package com.project.shopapp.services;

import com.project.shopapp.responses.DriveResponse;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IDriveService {
    DriveResponse uploadPdfToDrive(File file) throws GeneralSecurityException, IOException;
    DriveResponse uploadImageToDrive(File file)  throws GeneralSecurityException, IOException;

    String deleteFileOrFolder(String fileId);
}
