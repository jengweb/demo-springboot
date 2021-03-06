package com.example.rest.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("ทำการตรวจสอบ การดึงข้อมูล user ทั้งหมด")
    public void getAllUser() {
        // Call API
        UserListResponse response
                = restTemplate.getForObject("/user", UserListResponse.class);
        // Assert
        assertEquals(2, response.getUsers().size());
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("ทำการตรวจสอบ การดึงข้อมูล user ด้วย id=1")
    public void getUser() {
        // Intial data
        userRepository.save(new User("demo for test", 30));

        // Call API
        UserResponse response = restTemplate.getForObject("/user/1", UserResponse.class);

        // Assert
        assertEquals(1, response.getId());
        assertEquals("demo for test", response.getName());
        assertEquals(30, response.getAge());

        // Quiz => equals() + hashcode()
        UserResponse expected = new UserResponse(1, "demo for test", 30);
        assertEquals(expected, response);
    }

}