package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.domain.Image;
import hu.ponte.hr.exception.FileUploadFailedException;
import hu.ponte.hr.exception.ImageNotFoundException;
import hu.ponte.hr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImageStore {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageStore(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void store(MultipartFile file) {
        try {
            imageRepository.save(new Image(file));
        } catch (IOException e) {
            throw new FileUploadFailedException("File upload (" + file.getName() + ") failed");
        }
    }

    public List<ImageMeta> getImageMetaList() {
        return imageRepository.findAll()
                .stream()
                .map(ImageMeta::new)
                .collect(Collectors.toList());
    }

    public byte[] getImageData(String id) {
        return findImageById(id).getImageData();
    }

    private Image findImageById(String id) {
        Long imageId = Long.parseLong(id);
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Image with #" + id + " id is not found."));
    }
}

