package com.deepak.assignment.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.deepak.assignment.dto.EmployeeDto;
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

    public static void createExcelFile(List<EmployeeDto> employees, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Employee ID");
        headerRow.createCell(1).setCellValue("First Name");

        // Create data rows
        int rowNum = 1;
        for (EmployeeDto employee : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getEmpId());
            row.createCell(1).setCellValue(employee.getFname());
        }

        // Write the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }

        // Close the workbook
        workbook.close();
    }
}
