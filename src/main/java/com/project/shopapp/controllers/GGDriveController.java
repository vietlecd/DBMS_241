package com.project.shopapp.controllers;

import com.project.shopapp.responses.DriveResponse;
import com.project.shopapp.services.impl.DriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("${api.prefix}")
public class GGDriveController {
    @Autowired
    private DriveService driveService;

    @PostMapping("/uploadToGoogleDrive")
    public Object handleFileUpload(@RequestParam("pdf") MultipartFile file) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return "FIle is empty";
        }
        File pdfFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            fos.write(file.getBytes());
        }
        DriveResponse res = driveService.uploadImageToDrive(pdfFile);
        System.out.println(res);
        return res;
    }
}
