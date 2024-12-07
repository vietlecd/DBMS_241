package com.project.shopapp.responses;

import lombok.Data;

@Data
public class DriveResponse {
    private int status;
    private String message;
    private String url;
}
