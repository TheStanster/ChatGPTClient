package com.chatgptclient.chatgptclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChatGPTGUI extends Application {

    private static final String API_URL = "http://localhost:8080/complete";

    private final RestTemplate restTemplate = new RestTemplate();

    private Label responseLabel;
    private Label historyLabel;
    private TextArea inputTextArea;
    private Button submitButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ChatGPT");

        responseLabel = new Label();
        responseLabel.setWrapText(true);
        historyLabel = new Label();
        historyLabel.setWrapText(true);
        inputTextArea = new TextArea();
        submitButton = new Button("Submit");

        VBox vbox = new VBox(historyLabel, responseLabel, inputTextArea, submitButton);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        List<ChatMessage> MessageHistory = new ArrayList<>();

        submitButton.setOnAction(event -> {
            try {
                MessageHistory.add(new ChatMessage("user", inputTextArea.getText().trim()));
                String history = "";
                for (ChatMessage message : MessageHistory) {
                    history += message.getRole() + ": " + message.getContent() +"\n";
                }
                historyLabel.setText(history);
                inputTextArea.clear();
                ChatRequest request = new ChatRequest(
                        "gpt-3.5-turbo",
                            MessageHistory
                );
                ObjectMapper objectMapper = new ObjectMapper();
                String payload = objectMapper.writeValueAsString(request);
                String response = restTemplate.exchange(
                        RequestEntity.post(new URI(API_URL))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .body(payload),
                        String.class
                ).getBody();
                responseLabel.setText(response);
                MessageHistory.add(new ChatMessage("assistant", response));
            } catch (URISyntaxException | JsonProcessingException e) {
                e.printStackTrace();
            }
        });


        primaryStage.setScene(new Scene(vbox, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

