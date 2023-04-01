package com.chatgptclient.chatgptclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = ASSIGNABLE_TYPE, value = ChatGPTController.class))
public class ChatGPTApplication {
    @Value("${chatgpt.apiKey}")
    private String apiKey;


    public static void main(String[] args) {
        SpringApplication.run(ChatGPTApplication.class, args);
    }

    @Bean
    public ChatGPTClient chatGPTClient() {
        return new ChatGPTClient(apiKey);
    }


    @Bean
    public ChatGPTController chatGPTController(ChatGPTClient client) {
        return new ChatGPTController(client);
    }
}
