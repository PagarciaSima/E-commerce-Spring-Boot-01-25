package com.pgs.ecommerce.Ecommerce.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for handling file uploads and deletions.
 */
@Slf4j
public class UploadFileService {

    private final String FOLDER = Paths.get("src", "main", "resources", "static", "images").toString();
    private final String IMG_DEFAULT = "default.jpg";
    private final String URL = "http://localhost:8085/images/";

    /**
     * Uploads a file to the server and stores it in the images folder.
     * If the file is not provided, a default image URL is returned.
     *
     * @param multipartFile The file to be uploaded.
     * @return The URL of the uploaded file or the default image if no file was provided.
     * @throws IOException If an I/O error occurs during the file upload.
     */
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

    /**
     * Deletes a file from the server based on its name.
     * 
     * @param nameFile The name of the file to be deleted.
     */
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

    /**
     * Retrieves a file based on its name.
     *
     * @param fileName The name of the file to retrieve.
     * @return The file object.
     */
    public File getFile(String fileName) {
        return new File(fileName);
    }
}
