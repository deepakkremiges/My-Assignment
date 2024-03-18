package com.deepak.assignment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.assignment.utility.JsonRequest;
import com.deepak.assignment.utility.ResponseHandler;
import com.deepak.assignment.utility.UtilGenerator;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<Object> sayHello() {
        try {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("message", "Hello World");
            String reqId = UtilGenerator.generateUniqueString(20);
            return ResponseHandler.generateResponse("success", "", "", responseData, reqId);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null, "");
        }
    }

    @PostMapping("/hello")
    public ResponseEntity<Object> sayHello(@RequestBody JsonRequest request) {
        try {
            String name = (String) request.getData().get("name");
            String responseMessage = "Hello " + name;
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("Greeting: ", responseMessage);

            String reqId = UtilGenerator.generateUniqueString(12);
            return ResponseHandler.generateResponse("success", "", "", responseData, reqId);
        } catch (Exception e) {

            return ResponseHandler.generateResponse("error", "500", e.getMessage(), null, "");
        }
    }

    @PostMapping("/mysum")
    public ResponseEntity<Object> calculateSum(@RequestBody JsonRequest request) {
        try {
            // Extracting operation, num1, and num2 from the JsonRequest
            String operation = (String) request.getData().get("operation");
            double num1 = Double.parseDouble(request.getData().get("num1").toString());
            double num2 = Double.parseDouble(request.getData().get("num2").toString());
            String reqId = UtilGenerator.generateUniqueString(12);

            // Calculating the result based on the specified operation
            double result;
            switch (operation.toLowerCase()) {
                case "add":
                    result = num1 + num2;
                    break;
                case "subtract":
                    result = num1 - num2;
                    break;
                case "multiply":
                    result = num1 * num2;
                    break;
                case "divide":
                    if (num2 == 0) {
                        return ResponseHandler.generateResponse("error", "DIVIDE_BY_ZERO", "Cannot divide by zero",
                                null, "");
                    }
                    result = num1 / num2;
                    break;
                default:
                    return ResponseHandler.generateResponse("error", "INVALID_OPERATION", "Invalid operation specified",
                            null, "");
            }

            // Constructing the response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("result", result);

            // Generating the custom response using ResponseHandler
            return ResponseHandler.generateResponse("success", "", "", responseData, reqId);
        } catch (NumberFormatException e) {
            // Handling invalid number format
            return ResponseHandler.generateResponse("error", "INVALID_NUMBER_FORMAT", "Invalid number format", null,
                    "");
        } catch (Exception e) {

            return ResponseHandler.generateResponse("error", "INTERNAL_SERVER_ERROR", e.getMessage(), null, "");
        }
    }

    @PostMapping("/mycalc")
    public ResponseEntity<Object> performCalculation(@RequestBody JsonRequest request) {
        try {
            // Extracting operation and numbers from the JsonRequest
            String operation = (String) request.getData().get("operation");
            @SuppressWarnings("unchecked")
            List<Double> numbers = (List<Double>) request.getData().get("numbers");

            // Calculating the result based on the specified operation
            double result;
            switch (operation.toLowerCase()) {
                case "mean":
                    result = UtilGenerator.calculateMean(numbers);
                    break;
                case "min":
                    result = UtilGenerator.calculateMin(numbers);
                    break;
                case "max":
                    result = UtilGenerator.calculateMax(numbers);
                    break;
                default:
                    return ResponseHandler.generateResponse("error", "INVALID_OPERATION", "Invalid operation specified",
                            null, "");
            }
            // Constructing the response data
            Map<String, Object> responseData = Map.of(
                    "operation", operation,
                    "result", result);

            // Generating the custom response using ResponseHandler
            return ResponseHandler.generateResponse("success", "", "", responseData, "afkjekfsdkf454354");
        } catch (Exception e) {
            // Handling exceptions
            return ResponseHandler.generateResponse("error", "INTERNAL_SERVER_ERROR", e.getMessage(), null, "");
        }
    }

}
