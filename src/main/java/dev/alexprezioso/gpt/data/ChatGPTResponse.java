package dev.alexprezioso.gpt.data;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class ChatGPTResponse {
    private String id;
    private String object;
    private List<LinkedHashMap<String, Object>> choices;
}