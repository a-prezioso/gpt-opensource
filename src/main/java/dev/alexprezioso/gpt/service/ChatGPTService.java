package dev.alexprezioso.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexprezioso.gpt.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatGPTService {

    @Value("${chatgpt.api-key}")
    private String apiKey;

    @Value("${chatgpt.api-url}")
    private String apiUrl;

    @Autowired
    ChatManager chatManager;

    public ChatGPTResponse chat(ChatGPTRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatGPTResponse> response = restTemplate.exchange(
                apiUrl, HttpMethod.POST, entity, ChatGPTResponse.class);

        return response.getBody();
    }


    public List<MessageDTO> getHistory(String userName, String messageText, Integer messagesNum) {
        List<Message> conversation = chatManager.getChat(userName).getMessages();
        if(conversation == null) {
            conversation = new ArrayList<>();
        }
        Message userMessage = new Message("user", messageText);
        conversation.add(userMessage);
        chatManager.updateChat(userName, conversation);
        // Get the last 5 messages (or less if there are fewer messages in the conversation)
        int fromIndex = Math.max(0, conversation.size() - messagesNum);
        List<Message> lastMessages = conversation.subList(fromIndex, conversation.size());
        return lastMessages.stream()
                .map(msg -> new MessageDTO(msg.getRole(), msg.getContent()))
                .collect(Collectors.toList());
    }

    public List<MessageDTO> saveConversation(String userName, ChatGPTResponse response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(objectMapper.writeValueAsString(response.getChoices().get(0).get("message")), Message.class);
        List<Message> conversation = chatManager.getChat(userName).getMessages();
        conversation.add(message);
        chatManager.updateChat(userName, conversation);
        return conversation.stream()
                .map(msg -> new MessageDTO(msg.getRole(), msg.getContent()))
                .collect(Collectors.toList());
    }
}
