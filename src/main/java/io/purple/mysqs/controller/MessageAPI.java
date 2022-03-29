package io.purple.mysqs.controller;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.purple.mysqs.service.AmazonSQSSender;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MessageAPI {

    private final AmazonSQSSender messageSender;

    @PostMapping("/api/v2/send")
    public String sendMessage(@RequestBody String message) {
        messageSender.sendMessage(message);
        return "success";
    }
}
