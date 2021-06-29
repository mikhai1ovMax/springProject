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


    @Test
    void authTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithUserDetails("admin")
    void getUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails("admin")
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
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