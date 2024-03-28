package com.remiges.assign.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.remiges.assign.model.Department;
import com.remiges.assign.model.Employee;
import com.remiges.assign.utils.AppConstants;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplate;

    @Value("${spring.kafka.topic-json.name}")
    private String topicJsonName;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    public void sendMessage(Employee data) {
        LOGGER.info(String.format("Message sent -> %s", data.toString()));

        Message<Employee> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, AppConstants.JSON_TOPIC_NAME)
                .build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(Department data) {
        LOGGER.info(String.format("Message sent -> %s", data.toString()));

        Message<Department> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, AppConstants.TOPIC_NAME)
                .build();

        kafkaTemplate.send(message);
    }

}
