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
    
    public String upload (MultipartFile multipartFile) throws IOException {
    	if (multipartFile != null) {
    		byte [] bytes = multipartFile.getBytes();
    		Path completePath = Paths.get(FOLDER, multipartFile.getOriginalFilename());
    		Files.write(completePath, bytes);
    		
    		// Return the image URI
        	log.debug("File uploaded successfully");
    		return URL + multipartFile.getOriginalFilename();

    	}
    	log.debug("The file could not be uploaded");
    	return URL + IMG_DEFAULT;
    }
    
    public void delete(String nameFile) {
        try {
            String fileName = nameFile.replace(URL, "");
            
            Path filePath = Paths.get(FOLDER, fileName);
            
            File file = filePath.toFile();
            if (file.exists() && file.delete()) {
                log.debug("File deleted successfully: {}", fileName);
            } else {
                log.warn("File not found or could not be deleted: {}", fileName);
            }
        } catch (Exception e) {
            log.error("The file could not be deleted: {}", e.getMessage());
        }
    }

}
