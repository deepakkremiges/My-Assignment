package com.deepak.assignment.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String status, String statusCode, String statusMsg,
            Object responseData, String reqId) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("status_code", statusCode);
        map.put("status_msg", statusMsg);
        map.put("data", responseData);
        map.put("_reqid", reqId);
        map.put("_server_ts", LocalDateTime.now().toString());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
