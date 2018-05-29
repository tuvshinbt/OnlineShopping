package edu.mum.cs490.project.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Erdenebayar on 5/4/2018
 */
@Component
public class FileManagementService {

    //Save the uploaded file to this folder

    @Value("${resource.dir}")
    private String FILE_UPLOAD_PATH;

    @Value("#{'${image.extensions}'.split(';')}")
    private List<String> extensions;

    public String createFile(MultipartFile file, String objectType, Integer objectId) {
        try {
            String directoryPath = "";
            String savingPath = "";
            if (FILE_UPLOAD_PATH != null) {
                directoryPath = FILE_UPLOAD_PATH + objectType + File.separator + objectId;
                savingPath = objectType + File.separator + objectId;
                Path rootPath = Paths.get(directoryPath);
                if (!Files.exists(rootPath)) {
                    Files.createDirectories(rootPath);
                }
            }
            String filePath = File.separator + (objectId + "." + getExtension(file.getOriginalFilename()));
            String fileFullName = directoryPath + filePath;
            savingPath += filePath;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileFullName);
            Files.write(path, bytes);
            System.out.println("Successfully created file - " + fileFullName + " URL - " + savingPath);
            return savingPath;
        } catch (IOException ex) {
            ex.getMessage();
        }
        return null;
    }

    private String getExtension(String fileName) {
        return fileName.split("\\.")[1];
    }

    public boolean checkImageExtension(String fileName) {
        return extensions.contains(getExtension(fileName.toLowerCase()));
    }
}
