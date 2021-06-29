package com.max.springproject.services;

import com.max.springproject.models.Event;
import com.max.springproject.models.User;
import com.max.springproject.models.UserRole;
import com.max.springproject.models.UserStatus;
import com.max.springproject.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserServiceTest {

    UserRepository repository = Mockito.mock(UserRepository.class);

    UserService service = new UserService(repository);

    @Test
    void getById() {
        User user = new User(1, "Ivan", UserStatus.ACTIVE, UserRole.ADMIN, "1234", Arrays.asList(new Event()));
        Mockito.when(repository.getById(1l)).thenReturn(user);
        assertEquals(user, service.getById(1l));
    }

    @Test
    void getAll() {
        List<User> users = Arrays.asList(new User(1, "Ivan", UserStatus.ACTIVE, UserRole.ADMIN, "1234", Arrays.asList(new Event())), new User());
        Mockito.when(repository.findAll()).thenReturn(users);
        assertEquals(users, service.getAll());
    }

    @Test
    void save() {
        User user = new User(1, "Ivan", UserStatus.ACTIVE, UserRole.ADMIN, "1234", Arrays.asList(new Event()));
        Mockito.when(repository.save(user)).thenReturn(user);
        assertEquals(user, service.save(user));
    }

    @Test
    void deleteById() {
        repository.deleteById(2l);
        Mockito.verify(repository, Mockito.times(1)).deleteById(2l);
    }
}