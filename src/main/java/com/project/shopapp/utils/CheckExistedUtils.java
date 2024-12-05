package com.project.shopapp.utils;

import com.project.shopapp.customexceptions.DataNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CheckExistedUtils {
    public void checkFileExists(MultipartFile file, String fileName) {
        if (file == null || file.isEmpty()) {
            throw new DataNotFoundException(fileName + " not found");
        }
    }
    public void checkObjectExisted(Object object, String objectName) {
        if (object == null) {
            throw new DataNotFoundException(objectName + " not found");
        }
        if (object instanceof String && ((String) object).trim().isEmpty()) {
            throw new DataNotFoundException(objectName + " is empty");
        }
    }

    public void checkStatusExisted(Boolean status, String statusName) {
        if (!status) {
            throw new DataNotFoundException(statusName + " not found");
        }
    }

}
