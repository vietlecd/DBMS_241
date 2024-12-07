package com.project.shopapp.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.project.shopapp.responses.DriveResponse;
import com.project.shopapp.services.IDriveService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class DriveService implements IDriveService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${google-drive.folder-id}")
    private String folderId;

    // Upload PDF file
    @Override
    public DriveResponse uploadPdfToDrive(File file) throws GeneralSecurityException, IOException {
        return uploadFileToDrive(file, "application/pdf");
    }

    // Upload Image file
    @Override
    public DriveResponse uploadImageToDrive(File file) throws GeneralSecurityException, IOException {
        String mimeType = Files.probeContentType(file.toPath());

        if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
            throw new IllegalArgumentException("Unsupported image type: " + mimeType);
        }

        return uploadFileToDrive(file, mimeType);
    }

    // Generic file upload logic
    private DriveResponse uploadFileToDrive(File file, String mimeType) throws GeneralSecurityException, IOException {
        DriveResponse res = new DriveResponse();

        try {
            Drive drive = createDriveService();

            // Set file metadata
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent(mimeType, file);

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id, webViewLink, webContentLink")
                    .execute();

            String downLink = uploadedFile.getWebContentLink();

            res.setStatus(200);
            res.setMessage("File Successfully Uploaded To Drive");
            res.setUrl(downLink);

            // Delete local file after successful upload
            if (res.getStatus() == 200) {
                file.delete();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {
        GoogleCredential credential = GoogleCredential.fromStream(
                        getClass().getClassLoader().getResourceAsStream("cred.json"))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();
    }

    @Override
    public String deleteFileOrFolder(String fileUrl) {
        String fileId = extractFileIdFromUrl(fileUrl);

        if (fileId == null) {
            throw new IllegalArgumentException("Invalid Google Drive file URL: " + fileUrl);
        }

        try {
            Drive drive = createDriveService();
            drive.files().delete(fileId).execute();
            System.out.println("File deleted: " + fileUrl);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error deleting file: " + e.getMessage(), e);
        }
        return fileId;
    }

    private String extractFileIdFromUrl(String fileUrl) {
        String fileId = null;
        if (fileUrl.contains("/d/")) {
            fileId = fileUrl.split("/d/")[1].split("/")[0];
        } else if (fileUrl.contains("id=")) {
            fileId = fileUrl.split("id=")[1].split("&")[0];
        }
        return fileId;
    }
}
