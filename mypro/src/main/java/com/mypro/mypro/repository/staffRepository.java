package com.mypro.mypro.repository;

import com.mypro.mypro.model.staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface staffRepository extends JpaRepository<staff, Integer> {
    staff findByUsername(String username);

}
