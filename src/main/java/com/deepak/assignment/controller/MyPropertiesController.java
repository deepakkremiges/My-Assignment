package com.deepak.assignment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.assignment.utility.JsonRequest;
import com.deepak.assignment.utility.ResponseHandler;
import com.deepak.assignment.utility.UtilGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyPropertiesController {

    @Value("${my.property1:NULL}")
    private String property1;

    @Value("${my.property2:NULL}")
    private String property2;

    @Value("${my.property3:NULL}")
    private String property3;

    @PostMapping("/myproperties")
    public ResponseEntity<Object> getProperties(@RequestBody JsonRequest request) {
        try {
            @SuppressWarnings("unchecked")
            List<String> propertyIdentifiers = (List<String>) request.getData().get("propertyIdentifiers");
            Map<String, Object> result = new HashMap<>();

            propertyIdentifiers.forEach(identifier -> {
                switch (identifier) {
                    case "property1":
                        result.put(identifier, property1);
                        break;
                    case "property2":
                        result.put(identifier, property2);
                        break;
                    case "property3":
                        result.put(identifier, property3);

                        break;
                    default:
                        result.put(identifier, "NULL");
                }
            });
            return ResponseHandler.generateResponse("success", "", "", result,
                    UtilGenerator.generateUniqueString(15));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("error", "INTERNAL_SERVER_ERROR", e.getMessage(), null, "");
        }
    }
}
