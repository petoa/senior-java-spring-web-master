package hu.ponte.hr;

import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MultipartFileCreator {

    public static MultipartFile createMultipartFile(String filePath) {
        MultipartFile multipartFile = null;
        try {
            File file = new File(filePath);
            FileInputStream input = new FileInputStream(file);
            multipartFile = new MockMultipartFile("file", file.getName(),
                    "image/jpg", IOUtils.toByteArray(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartFile;
    }
}
