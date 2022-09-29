package hu.ponte.hr.controller;

import hu.ponte.hr.domain.Image;
import lombok.Getter;

/**
 * @author zoltan
 */
@Getter
public class ImageMeta {
    private String id;
    private String name;
    private String mimeType;
    private long size;
    private String digitalSign;

    public ImageMeta(Image image) {
        this.id = image.getImageId().toString();
        this.name = image.getName();
        this.mimeType = image.getMimeType();
        this.size = image.getSize();
        this.digitalSign = image.getDigitalSign();
    }
}
