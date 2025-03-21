package com.mypro.mypro.repository;

import com.mypro.mypro.model.tasks;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface tasksRepository extends JpaRepository<tasks, Integer> {
    Optional<tasks> findById(Integer id);

}
