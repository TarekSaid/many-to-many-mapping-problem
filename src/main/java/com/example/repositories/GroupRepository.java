package com.example.repositories;

import com.example.entities.impl.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {
}
