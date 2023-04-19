package dev.alexprezioso.gpt.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {
    private String userId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Chat(String userId) {
        this.userId = userId;
        this.messages = new ArrayList<>();
    }

}
