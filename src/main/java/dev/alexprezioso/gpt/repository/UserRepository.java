package dev.alexprezioso.gpt.repository;

import dev.alexprezioso.gpt.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
