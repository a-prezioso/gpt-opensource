package dev.alexprezioso.gpt.data;

import lombok.Data;

@Data
public class MessageDTO {

    private String role;
    private String content;

    public MessageDTO(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public MessageDTO() {
    }
}
