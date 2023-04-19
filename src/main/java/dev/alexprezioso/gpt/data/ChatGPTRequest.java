package dev.alexprezioso.gpt.data;

import lombok.Data;

import java.util.List;

@Data
public class ChatGPTRequest {
    private List<MessageDTO> messages;

    private String model;

    private Float temperature = 0.4f;
}


