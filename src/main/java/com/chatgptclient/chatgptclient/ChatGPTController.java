package com.chatgptclient.chatgptclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatGPTController {
    private final ChatGPTClient client;

    public ChatGPTController(ChatGPTClient client) {
        this.client = client;
    }

    @PostMapping("/complete")
    public String complete(@RequestBody String prompt) throws JsonProcessingException {
        return client.complete(prompt);
    }
}
