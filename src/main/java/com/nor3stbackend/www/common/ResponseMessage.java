package com.nor3stbackend.www.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseMessage {
    private HttpStatus Status;
    private String Message;
    private Object Data;

    public ResponseMessage() {
    }

    public ResponseMessage(HttpStatus status, String message, Object data) {
        Status = status;
        Message = message;
        Data = data;
    }
}
