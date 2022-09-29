package hu.ponte.hr.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String name;

    private String mimeType;

    private long size;

    private String digitalSign;

    @Lob
    private byte[] imageData;

    public Image(MultipartFile file) throws IOException {
        this.name = file.getOriginalFilename();
        this.mimeType = file.getContentType();
        this.size = file.getSize();
        this.imageData = file.getBytes();
    }
}
