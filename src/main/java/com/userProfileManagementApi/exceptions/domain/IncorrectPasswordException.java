package com.userProfileManagementApi.exceptions.domain;

public class IncorrectPasswordException extends Exception{
    public IncorrectPasswordException(String message) {
        super(message);
    }
}