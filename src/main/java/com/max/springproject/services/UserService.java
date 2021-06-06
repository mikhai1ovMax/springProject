package com.max.springproject.services;

import com.max.springproject.models.User;
import com.max.springproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements GenericService<User> {
    UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getById(long id) {
        return repository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User save(User object) {
        return repository.save(object);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
