package com.max.springproject.controllers;

import com.max.springproject.models.Event;
import com.max.springproject.models.User;
import com.max.springproject.models.UserRole;
import com.max.springproject.models.UserStatus;
import com.max.springproject.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UserService service = Mockito.mock(UserService.class);
    private UserController controller =  new UserController(service);
    User user = new User(123, "Ivan", UserStatus.ACTIVE, UserRole.ADMIN, "123", Arrays.asList(new Event()));
    List<User> users = Arrays.asList(user);

    @Test
    void authTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithUserDetails("admin")
    void getUsersAuthTest() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails("admin")
    void getUserByIdAuth() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    void getUsers() {
        Mockito.when(service.getAll()).thenReturn(users);
        assertEquals(users.toString(), controller.getUsers());
    }

    @Test
    void getUser() {
        Mockito.when(service.getById(1l)).thenReturn(user);
        assertEquals(user.toString(), controller.getUserById(1l));
    }

    @Test
    void saveUser() {
        User user = new User(1, "Ivan", UserStatus.ACTIVE, UserRole.ADMIN, "1234", Arrays.asList(new Event()));
        Mockito.when(service.save(user)).thenReturn(user);
        assertEquals(user.toString(), controller.saveUser(user));
    }

    @Test
    void deleteUser() {
        controller.deleteUser(2l);
        Mockito.verify(service, Mockito.times(1)).deleteById(2l);
    }
}
