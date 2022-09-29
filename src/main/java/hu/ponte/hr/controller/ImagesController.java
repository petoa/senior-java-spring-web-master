package hu.ponte.hr.controller;


import hu.ponte.hr.services.ImageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    @Autowired
    private ImageStore imageStore;

    @GetMapping("meta")
    @ResponseStatus(HttpStatus.OK)
    public List<ImageMeta> listImages() {
        return imageStore.getImageMetaList();
    }

    @GetMapping("preview/{id}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImage(@PathVariable("id") String id) {
        return imageStore.getImageData(id);
    }

}
