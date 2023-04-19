package dev.alexprezioso.gpt.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatManager {
    private Map<String, Chat> chatMap;

    public ChatManager() {
        chatMap = new HashMap<>();
    }

    public Chat getChat(String userId) {
        if(chatMap.get(userId) == null) {
            chatMap.put(userId, new Chat(userId));
        }
        return chatMap.get(userId);
    }

    public void setChat(String userId, Chat chat) {
        chatMap.put(userId, chat);
    }

    public void updateChat(String userName, List<Message> messages) {
        Chat chat = getChat(userName);
        chat.setMessages(messages);
        chatMap.put(userName, chat);
    }
}


