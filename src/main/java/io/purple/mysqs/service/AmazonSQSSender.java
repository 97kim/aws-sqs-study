package io.purple.mysqs.service;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AmazonSQSSender {
    SendMessageResult sendMessage(Message message) throws JsonProcessingException;
}
