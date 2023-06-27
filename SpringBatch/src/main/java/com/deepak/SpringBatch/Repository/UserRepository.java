package com.deepak.SpringBatch.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepak.SpringBatch.model.User;

public interface UserRepository extends JpaRepository<User, Integer>   {

}
