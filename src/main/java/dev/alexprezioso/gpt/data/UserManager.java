package dev.alexprezioso.gpt.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserManager {
    private Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public User getUser(String userId) {
        if(users.get(userId) == null) {
            users.put(userId, new User(userId));
        }
        return users.get(userId);
    }

    public void setUser(String userId) {
        users.put(userId, new User(userId));
    }
}


