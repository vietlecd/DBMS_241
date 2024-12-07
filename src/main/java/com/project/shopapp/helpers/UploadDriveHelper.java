package com.project.shopapp.helpers;

import com.project.shopapp.customexceptions.DataNotFoundException;
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

    public String upDrive(MultipartFile pdf) throws IOException {
        if (pdf.isEmpty()) {
            throw new DataNotFoundException("khong tim thay filePDF");
        }

        File pdfFile = new File(pdf.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            fos.write(pdf.getBytes());

            DriveResponse res = driveService.uploadImageToDrive(pdfFile);

            if (res != null) {
                return res.getUrl();
            }
        } catch (IOException | GeneralSecurityException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading PDF to drive", e);
        } finally {
            if (pdfFile.exists()) {
                pdfFile.delete();
            }
        }
        return null;
    }
}
