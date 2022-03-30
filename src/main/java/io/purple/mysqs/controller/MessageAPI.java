package io.purple.mysqs.controller;

import io.purple.mysqs.dto.MessageDto;
import io.purple.mysqs.service.AmazonSQSSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageAPI {

    private final AmazonSQSSender messageSender;

    @PostMapping("/api/v2/send")
    public String sendMessage(@RequestBody MessageDto message) {
        messageSender.sendMessage(message);
        return "success";
    }
}
