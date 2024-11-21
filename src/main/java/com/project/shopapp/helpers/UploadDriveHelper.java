package com.project.shopapp.helpers;

import com.project.shopapp.customexceptions.DataNotFoundException;
//import com.project.shopapp.responses.DriveResponse;
//import com.project.shopapp.services.impl.DriveService;
import com.project.shopapp.responses.DriveResponse;
import com.project.shopapp.services.impl.DriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class UploadDriveHelper {
    @Autowired
    private DriveService driveService;

    public DriveResponse upDrive(MultipartFile file) {
        if (file.isEmpty()) {
            throw new DataNotFoundException("khong tim thay filePDF");
        }

        File pdfFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            fos.write(file.getBytes());

        DriveResponse res = driveService.uploadImageToDrive(pdfFile);

//        if (res != null) {
//            pdfFile.delete();
//        }

        return res;

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }}
}
