package com.app.ecom.service;

import com.app.ecom.Entity.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private Long nextId = 1L;
    List<Users> usersList = new ArrayList();

    public List<Users> fetchAllUsers() {
        return usersList;
    }

    public void addUser(Users users) {
        users.setId(nextId++);
        usersList.add(users);
    }

    public Users fetchUser(Long id) {
        for (Users user : usersList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
