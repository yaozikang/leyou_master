package com.leyou.upload.test;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author 要子康
 * @description FastDFSTest
 * @since 2020/7/2 15:46
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastDFSTest {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUpload() throws FileNotFoundException{

        File file = new File("C:\\Users\\dell\\Desktop\\1524297419252.jpg");

        StorePath storePath = this.fastFileStorageClient.uploadFile(
                new FileInputStream(file), file.length(), "jpg", null
        );

        System.out.println(storePath.getFullPath());

        System.out.println(storePath.getPath());
    }

    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException{
        File file = new File("C:\\Users\\dell\\Desktop\\152429741925.png");

        StorePath storePath = this.fastFileStorageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), "png", null
        );

        System.out.println(storePath.getFullPath());

        System.out.println(storePath.getPath());

        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());

        System.out.println(path);
    }

}
