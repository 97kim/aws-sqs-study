package io.purple.mysqs.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AmazonSQSSenderImpl implements AmazonSQSSender {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.sqs.queue.name}")
    private String queueName;

    public AmazonSQSSenderImpl(AmazonSQS amazonSQS) {
        this.queueMessagingTemplate = new QueueMessagingTemplate((AmazonSQSAsync) amazonSQS);
    }

    @Override
    public void sendMessage(String message) {
        Message<String> newMessage = MessageBuilder.withPayload(message).build();
        queueMessagingTemplate.send(queueName, newMessage);
    }
}
