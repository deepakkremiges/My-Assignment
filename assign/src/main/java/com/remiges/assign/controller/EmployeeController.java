package com.remiges.assign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.remiges.assign.model.Department;
import com.remiges.assign.model.Employee;
import com.remiges.assign.service.KafkaProducerService;

@RestController
public class EmployeeController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestBody Employee employee) {
        kafkaProducerService.sendMessage(employee);
    }

    @PostMapping("/publish2")
    public void sendMessageToKafkaTopic(@RequestBody Department department) {
        kafkaProducerService.sendMessage(department);
    }
}
