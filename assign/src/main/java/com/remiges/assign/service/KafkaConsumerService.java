package com.remiges.assign.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.remiges.assign.model.Department;
import com.remiges.assign.model.Employee;
import com.remiges.assign.utils.AppConstants;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = AppConstants.JSON_TOPIC_NAME, groupId = AppConstants.GROUP_ID)
    public void consume(Employee data) {
        LOGGER.info(String.format("Message received -> %s", data.toString()));
    }

    @KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = AppConstants.GROUP_ID)
    public void consume2(Department data) {
        LOGGER.info(String.format("Message received -> %s", data.toString()));
    }
}
