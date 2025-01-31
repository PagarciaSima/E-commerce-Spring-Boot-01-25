package com.pgs.ecommerce.Ecommerce.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadFileService {

    private final String FOLDER = Paths.get("src", "main", "resources", "static", "images").toString();
    private final String IMG_DEFAULT = "default.jpg";
    private final String URL = "http://localhost:8085/images/";

    public String upload(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            log.info("Uploading file: {}", multipartFile.getOriginalFilename());

            byte[] bytes = multipartFile.getBytes();
            Path completePath = Paths.get(FOLDER, multipartFile.getOriginalFilename());
            Files.write(completePath, bytes);

            log.info("File uploaded successfully: {}", completePath);
            return URL + multipartFile.getOriginalFilename();
        }

        log.warn("No file uploaded, returning default image");
        return URL + IMG_DEFAULT;
    }

    public void delete(String nameFile) {
        try {
            String fileName = nameFile.replace(URL, "");
            Path filePath = Paths.get(FOLDER, fileName);
            File file = filePath.toFile();

            if (file.exists()) {
                if (file.delete()) {
                    log.info("File deleted successfully: {}", fileName);
                } else {
                    log.warn("File exists but could not be deleted: {}", fileName);
                }
            } else {
                log.warn("File not found: {}", fileName);
            }
        } catch (Exception e) {
            log.error("Error deleting file: {}", e.getMessage(), e);
        }
    }
    
    public File getFile(String fileName) {
        return new File(fileName);
    }
}
