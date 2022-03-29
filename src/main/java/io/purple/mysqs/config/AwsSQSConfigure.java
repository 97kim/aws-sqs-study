package io.purple.mysqs.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AwsSQSConfigure {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    @Primary
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }

    @Bean
    public AmazonSQS amazonSQSClient() {
        AmazonSQSClientBuilder builder = AmazonSQSClientBuilder.standard().withCredentials(awsCredentialsProvider());
        builder.withRegion(region);
        return builder.build();
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQSAsync) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        // SQS API를 가지고 통신을 하는 container에 의해 사용되는 AmazonSQSAsync를 설정
        factory.setAmazonSqs(amazonSQSAsync);
        // 한 번 polling 하는 동안 조회되어야 하는 메시지의 최대 개수, 높을수록 polling request 요청을 줄여준다.
        factory.setMaxNumberOfMessages(10);
        // queue에 메시지가 없을 때, queue로 들어오는 새로운 메시지에 대해 polling 요청 시 대기하는 timeout 시간
        factory.setTaskExecutor(messageThreadPoolTaskExecutor());
        return factory;
    }

    @Bean
    public ThreadPoolTaskExecutor messageThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("sqs-task-");
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
