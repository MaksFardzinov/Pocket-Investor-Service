package com.example.FirstApp.exeptions;

import java.util.Date;

public class AppError {
    private int status;
    private String message;
    private Date time;

    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
        this.time = new Date();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
