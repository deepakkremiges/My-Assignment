package com.deepak.assignment.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.deepak.assignment.dto.UpdateEmployeeRequest;

public class UtilGenerator {

    public static String generateUniqueString(int length) {
        // Generate a unique string using UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // If the generated string length is greater than required length, return a
        // substring
        if (uuid.length() >= length) {
            return uuid.substring(0, length);
        } else {
            // If the generated string length is less than required length, pad with zeros
            StringBuilder builder = new StringBuilder(uuid);
            while (builder.length() < length) {
                builder.append("0");
            }
            return builder.toString().substring(0, length);
        }
    }

    public static double calculateMean(List<Double> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("List of numbers cannot be empty");
        }
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum / numbers.size();
    }

    public static double calculateMin(List<Double> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("List of numbers cannot be empty");
        }
        double min = numbers.get(0);
        for (double num : numbers) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    public static double calculateMax(List<Double> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("List of numbers cannot be empty");
        }
        double max = numbers.get(0);
        for (double num : numbers) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    // setting value in the EmployeeController for /update
    public static UpdateEmployeeRequest mapJsonRequestToUpdateEmployeeRequest(JsonRequest request) {
        try {
            UpdateEmployeeRequest updateRequest = new UpdateEmployeeRequest();

            updateRequest.setId(Long.parseLong(request.getData().get("id").toString()));

            updateRequest.setEmpId((String) request.getData().get("empId"));
            updateRequest.setFname((String) request.getData().get("fname"));
            updateRequest.setFullname((String) request.getData().get("fullname"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = dateFormat.parse(request.getData().get("dob").toString());
            Date doj = dateFormat.parse(request.getData().get("doj").toString());

            updateRequest.setDob(dob);
            updateRequest.setDoj(doj);

            updateRequest.setSalary(Integer.parseInt(request.getData().get("salary").toString()));
            updateRequest.setReportsTo(Long.parseLong(request.getData().get("reportsTo").toString()));

            return updateRequest;

        } catch (Exception e) {
            return null;
        }
    }
}
