package com.yingfeng.commons.web.config.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import org.springframework.context.annotation.*;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * FastDFS配置项<br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.config.fastdfs
 * @since 2017/10/26
 */
@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ComponetImport {

    @Scope("prototype")
    @Bean
    public DefaultFastFileStorageClient defaultFastFileStorageClient(){
        DefaultFastFileStorageClient defaultFastFileStorageClient = new DefaultFastFileStorageClient();
        return defaultFastFileStorageClient;
    }

}
