package com.deepak.assignment.controller.service;

import org.springframework.stereotype.Service;

import com.deepak.assignment.jwt.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserService() {
        store.add(new User(UUID.randomUUID().toString(), "Shivendra kumar", "shivendra.kumar@remiges.tech"));
        store.add(new User(UUID.randomUUID().toString(), "Deepak kumar", "deepak.kumar@remiges.tech"));
        store.add(new User(UUID.randomUUID().toString(), "Rajesh Chaudhary", "rajesh.chodhary@remiges.tech"));

    }

    private List<User> store = new ArrayList<>();

    public List<User> getUsers() {
        return store;
    }
}
