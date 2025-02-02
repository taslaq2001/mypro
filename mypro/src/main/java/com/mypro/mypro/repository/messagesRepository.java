package com.mypro.mypro.repository;

import com.mypro.mypro.model.messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface messagesRepository extends JpaRepository<messages, Long> {
}
