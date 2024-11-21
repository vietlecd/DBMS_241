package com.project.shopapp.configurations;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class GGDriveConfig {

    private static final String APPLICATION_NAME = "ShopApp Google Drive Integration";
    private static final String SERVICE_ACCOUNT_KEY = "path/to/your/service-account-key.json";

    public Drive getDriveService() throws IOException {
        Credential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
