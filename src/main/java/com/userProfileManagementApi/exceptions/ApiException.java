package com.userProfileManagementApi.exceptions;


import java.util.Date;



public class ApiException {
    public  String error;
    public  Integer status;
    public  String path;
    public String timestamp;

    public ApiException() {       
    }

    public ApiException(String error, Integer status, String timestamp, String path) {

        this.error = error;
        this.status = status;
        this.timestamp = timestamp;
        this.path=path;
    }

    
    public ApiException(String error) {
        this.error = error;
        this.status = 400;
        this.timestamp = (new Date()).toString();
        this.path=null;
    }

    public ApiException(String error,Integer status) {
        this.error = error;
        this.status = status;
        this.timestamp = (new Date()).toString();
        this.path=null;
    }
}
