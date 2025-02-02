package com.mypro.mypro.repository;

import com.mypro.mypro.model.tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface tasksRepository extends JpaRepository<tasks, Long> {
}
