package com.yingfeng.modules.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import java.io.File;

/**
 * 文件压缩包处理
 */
public class ZipUtil {

    /**
     * 压缩包解压
     * 解密
     * @param fileDeCompressionUrl  解压输出路径
     *         zipFilePath    压缩包文件路径
     * @return [返回类型说明]
     * @since  FABLE_DSSG_V1.0.0
     * @author lz
     * @create 2017/3/13 0013 11:42
     **/
    public static void DeCompression(String fileDeCompressionUrl,String zipFilePath,String filePass) throws ZipException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        if (zipFile.isEncrypted()) {

            zipFile.setPassword(filePass);
        }
        zipFile.extractAll(fileDeCompressionUrl);

    }

    /**
     * 文件压缩
     * 加密
     * @param fileExportUrl  输出路径
     *         zipFilePath    文件路径
     * @return [返回类型说明]
     * @since  FABLE_DSSG_V1.0.0
     * @author lz
     * @create 2017/3/13 0013 11:42
     **/
    public static void Compressing(String fileExportUrl,String zipFilePath,String filePass) throws ZipException {

        File filepath=new File(fileExportUrl);
        ZipFile zipFile = new ZipFile(zipFilePath);
        ZipParameters params=new ZipParameters();
        params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        params.setEncryptFiles(true);
        params.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        params.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        params.setPassword(filePass);
        zipFile.addFolder(filepath,params);

    }
}
