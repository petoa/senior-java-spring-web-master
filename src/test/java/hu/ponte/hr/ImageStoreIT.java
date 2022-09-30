package hu.ponte.hr;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.services.ImageStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
class ImageStoreIT {

    private final String FILE_PATH_PREFIX = "src/test/resources/images/";
    private final MultipartFile catFile = MultipartFileCreator.createMultipartFile(FILE_PATH_PREFIX + "cat.jpg");
    private final MultipartFile enhancedBuzzFile =
            MultipartFileCreator.createMultipartFile(FILE_PATH_PREFIX + "enhanced-buzz.jpg");
    private final MultipartFile rndFile = MultipartFileCreator.createMultipartFile(FILE_PATH_PREFIX + "rnd.jpg");

    @Autowired
    private ImageStore imageStore;

    @BeforeEach
    void init() {
        imageStore.store(catFile);
        imageStore.store(enhancedBuzzFile);
        imageStore.store(rndFile);
    }

    @Test
    void store() {
        assertEquals(3, imageStore.getImageMetaList().size());
        imageStore.store(catFile);
        assertEquals(4, imageStore.getImageMetaList().size());
    }

    @Test
    void getImageMetaList() {
        List<ImageMeta> imageMetaList = imageStore.getImageMetaList();
        assertEquals("cat.jpg", imageMetaList.get(0).getName());
        assertEquals(32_172, imageMetaList.get(0).getSize());
        assertEquals("image/jpg", imageMetaList.get(0).getMimeType());

        assertEquals("enhanced-buzz.jpg", imageMetaList.get(1).getName());
        assertEquals(220_523, imageMetaList.get(1).getSize());
        assertEquals("image/jpg", imageMetaList.get(1).getMimeType());

        assertEquals("rnd.jpg", imageMetaList.get(2).getName());
        assertEquals(198_870, imageMetaList.get(2).getSize());
        assertEquals("image/jpg", imageMetaList.get(2).getMimeType());
    }

    @Test
    void getImageData() throws IOException {
        assertEquals(catFile.getBytes(), imageStore.getImageData("1"));
        assertEquals(enhancedBuzzFile.getBytes(), imageStore.getImageData("2"));
        assertEquals(rndFile.getBytes(), imageStore.getImageData("3"));
    }
}
