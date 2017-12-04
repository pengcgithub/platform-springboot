package com.yingfeng.cms.modules.fastdfs.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FastDfs测试类 <br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.modules.fastdfs.controller
 * @since 2017/12/4
 */
@RestController
public class FastDfsController {

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 上传文件<br>  
     * @author pengc
     * @param file 文件流对象
     * @return FastDFS 文件路径
     * @since 2017/12/4
     */
    @PostMapping("/FastDfs/uploadFile")
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath;
        synchronized (storageClient) {
            storePath = storageClient.uploadFile(
                    file.getInputStream(),
                    file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()),
                    null);
        }
        return storePath.getFullPath();
    }

    /**
     * 下载文件<br>
     * @author pengc
     * @param response
     * @return FastDFS 文件路径
     * @since 2017/12/4
     */
    @PostMapping("/FastDfs/downloadFile")
    public void downloadFile(HttpServletResponse response) {
//        storageClient.downloadFile();
    }

}
