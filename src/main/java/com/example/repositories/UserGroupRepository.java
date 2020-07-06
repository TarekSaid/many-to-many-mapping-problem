package com.example.repositories;

import com.example.entities.joins.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    @Modifying
    @Query("delete from UserGroup ug where ug.id = ?1")
    void deleteById(Long id);
}
