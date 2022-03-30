package io.purple.mysqs.service;

import io.purple.mysqs.dto.MessageDto;

public interface AmazonSQSSender {
    void sendMessage(MessageDto message);
}
