package com.deepak.assignment.utility;

import java.util.Map;

import lombok.Data;

@Data
public class JsonRequest {
    private String token;
    private Map<String, Object> data;
    private String _reqid;
    private String _client_ts;
    private String _client_type;
}