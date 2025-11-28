package com.qswar.hc.pojos.responses.exception;

public class ErrorResponse {
    private String status; // e.g., "error" or HTTP status code
    private String message;
    private long timestamp;
    private String path; // The request path that caused the error

    public ErrorResponse() {
    }

    public ErrorResponse(String status, String message, long timestamp, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}