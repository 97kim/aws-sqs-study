package io.purple.mysqs.service;

public interface AmazonSQSSender {
    void sendMessage(String message);
}
