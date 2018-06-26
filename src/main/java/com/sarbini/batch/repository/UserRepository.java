package com.sarbini.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sarbini.batch.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
