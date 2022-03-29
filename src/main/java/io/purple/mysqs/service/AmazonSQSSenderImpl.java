package io.purple.mysqs.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AmazonSQSSenderImpl implements AmazonSQSSender{
    private final ObjectMapper objectMapper;
    private final AmazonSQS amazonSQS;
    private final AmazonSQSProperties properties;

    @Override
    public SendMessageResult sendMessage(Message msg) throws JsonProcessingException {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(properties.getUrl(), objectMapper.writeValueAsString(msg))
                .withMessageGroupId("group-1")
                .withMessageDeduplicationId(UUID.randomUUID().toString());
        // SQS에서 콘텐츠 기반 중복 제거 옵션 활성화하면 위에 UUID 값 주지 않아도 된다.
//        SendMessageRequest sendMessageRequest = new SendMessageRequest(properties.getUrl(), objectMapper.writeValueAsString(msg))
//                .withMessageGroupId("group-1");
        return amazonSQS.sendMessage(sendMessageRequest);
    }

    public AmazonSQSSenderImpl(ObjectMapper objectMapper, AmazonSQS amazonSQS, AmazonSQSProperties properties) {
        this.objectMapper = objectMapper;
        this.amazonSQS = amazonSQS;
        this.properties = properties;
    }
}
