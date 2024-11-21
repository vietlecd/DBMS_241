package com.project.shopapp.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.project.shopapp.responses.DriveResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class DriveService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    //private static final String SERVICE_ACOUNT_KEY_PATH = getPathToGoodleCredentials();

    private static final String SERVICE_ACOUNT_KEY_PATH = "src/main/resources/cred.json";



    private static String getPathToGoodleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cred.json");
        return filePath.toString();
    }

    public DriveResponse uploadImageToDrive(File file) throws GeneralSecurityException, IOException {
        DriveResponse res = new DriveResponse();

        try{
            String folderId = System.getenv("drive_url");
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
//            FileContent mediaContent = new FileContent("image/png", file);
            FileContent mediaContent = new FileContent("application/pdf", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            file.delete();
            res.setStatus(200);
            res.setMessage("Image Successfully Uploaded To Drive");
            res.setUrl(imageUrl);
        }catch (Exception e){
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return  res;

    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {

//        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
//                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        GoogleCredential credential = GoogleCredential.fromStream(
                        getClass().getClassLoader().getResourceAsStream("cred.json"))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();

    }
}
