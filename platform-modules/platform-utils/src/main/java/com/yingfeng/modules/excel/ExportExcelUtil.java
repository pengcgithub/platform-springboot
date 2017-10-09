/*
 * Copyright (C), 2013-2014, 江苏飞搏软件技术有限公司
 * FileName: ExportExcel.java
 * Date:     2014年7月28日 下午3:16:45  
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.yingfeng.modules.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导出为Excel表格.
 * @param <T>
 */
public class ExportExcelUtil<T> {
    /**
     * 
     * 功能描述: 将放置在集合中的数据以EXCEL的形式输出到指定IO设备上.
     * 
     * @param title 表格标题名
     * @param headers 表格属性列名数组
     * @param fields 表格列属性对应的数据对象属性数组
     * @param datalist 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式
     */
    public void exportExcel(String title, String[] headers, String[] fields, Collection<T> datalist, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置工作表列宽
        sheet.setDefaultColumnWidth(40);
        // 设置工作表行高
        // sheet.setDefaultRowHeight((short) 10);
        // 获得表头单元格样式
        HSSFCellStyle headerStyle = this.setColumnTopStyle(workbook);
        // 获得表体单元格样式
        HSSFCellStyle bodyStyle = this.setTableStyle(workbook, 0);
        HSSFCellStyle bodyStyle2 = this.setTableStyle(workbook, 1);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0); // 在索引0的位置创建行(最顶端的行)
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            // 设置表头单元格的数据类型
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            // 设置表头单元格样式
            cell.setCellStyle(headerStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            // 设置表头单元格的值
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = datalist.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            if (index > 65535)
                break;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields2 = t.getClass().getDeclaredFields();// 获得Class对象所表示的类或接口所声明的所有字段
            if (isMatch(fields, fields2)) {
                for (int i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    if (index % 2 == 0) {
                        cell.setCellStyle(bodyStyle);
                    } else {
                        cell.setCellStyle(bodyStyle2);
                    }
                    String fieldName = fields[i];
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class<? extends Object> tClass = t.getClass();
                    try {
                        Method getMethod = tClass.getMethod(getMethodName);
                        Object value = getMethod.invoke(t);
                        String textValue = dataTypeCast(pattern, value);
                        if (textValue != null) {
                            // 利用正则表达式判断textValue是否全部由数字组成
                            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                            Matcher matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Double.parseDouble(textValue));
                            } else {
                                HSSFRichTextString richString = new HSSFRichTextString(textValue);
                                cell.setCellValue(richString);
                            }
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 判断值的类型后进行强制类型转换.
     * 
     * @param pattern 日期数据的格式
     * @param value 要转换的数据
     * @return 转换后的数据
     */
    private String dataTypeCast(String pattern, Object value) {
        String textValue = null;
        if (value == null) {
            return "";
        }
        if (value instanceof Character) {
            Character bValue = (Character) value;
            textValue = "失败";
            if (bValue == '0') {
                textValue = "成功";
            }
        } else if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            textValue = sdf.format(date);
        } else {
            // 其它数据类型都当作字符串简单处理
            textValue = value.toString();
        }

        return textValue;
    }

    /**
     * 功能描述: 判断所给属性组与所给数据集合中对象的属性是否匹配.
     * 
     * @param fields 所给属性数组
     * @param fields2 所给数据集合中对象的属性组
     * @return boolean
     */
    private boolean isMatch(String[] fields, Field[] fields2) {
        boolean flag = true;
        int n;
        for (int i = 0; i < fields.length; i++) {
            n = 0;
            for (int j = 0; j < fields2.length; j++) {
                if (fields[i].equals(fields2[j].getName())) {
                    n++;
                }
            }
            if (n == 0) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 功能描述: 设置表体单元格样式.
     * 
     * @param workbook 工作薄对象
     * @param i 隔行颜色不同
     * @return 单元格样式
     */
    private HSSFCellStyle setTableStyle(HSSFWorkbook workbook, int i) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        // font.setFontHeightInPoints((short)10);
        // 字体加粗
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        // 设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        if (i == 0) {
            // 设置背景色
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        }
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /**
     * 功能描述: 设置表头单元格样式.
     * 
     * @param workbook 工作薄对象
     * @return 单元格样式
     */
    private HSSFCellStyle setColumnTopStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        // 设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        // 设置背景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);

        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }
}