package com.userProfileManagementApi.exceptions;

import java.util.Date;

public class ApiRequestException extends RuntimeException {
    private final String error;
    private final Integer status;
    private final String timestamp;

    public ApiRequestException(String error, Integer status, String timestamp) {
        this.error = error;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ApiRequestException(String error, Integer status) {
        this.error = error;
        this.status = status;
        this.timestamp = (new Date()).toString();
    }

    public ApiRequestException(String error) {
        this.error = error;
        this.status = 400;
        this.timestamp = (new Date()).toString();
    }

    public String getError() {
        return error;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
