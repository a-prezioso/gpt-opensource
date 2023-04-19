package dev.alexprezioso.gpt.repository;

import dev.alexprezioso.gpt.data.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByUserId(String userId);
}
