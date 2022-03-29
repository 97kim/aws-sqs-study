package io.purple.mysqs.controller;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.purple.mysqs.service.AmazonSQSSender;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MessageAPI {
    private final ObjectMapper objectMapper;
    private final AmazonSQSSender amazonSQSSender;

    public MessageAPI(ObjectMapper objectMapper, AmazonSQSSender amazonSQSSender) {
        this.objectMapper = objectMapper;
        this.amazonSQSSender = amazonSQSSender;
    }

    @PostMapping("/api/v1/send")
    public String send(@RequestBody Message message)  throws JsonProcessingException {
        amazonSQSSender.sendMessage(message);
        return "success";
    }

//    @SqsListener(value = "${cloud.aws.sqs.queue.name}")
//    private void receiveMessage(@Headers Map<String,String> header, @Payload String message) throws  JsonProcessingException {
//        Message readValue = objectMapper.readValue(message, Message.class);
//        System.out.println(readValue.getBody());
//    }
}
