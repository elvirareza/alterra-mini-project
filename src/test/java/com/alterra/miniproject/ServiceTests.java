package com.alterra.miniproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.alterra.miniproject.domain.model.HelloWorld;
import com.alterra.miniproject.domain.response.ApiResponse;
import com.alterra.miniproject.repository.HelloWorldRepository;
import com.alterra.miniproject.service.HelloWorldService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HelloWorldService.class)
public class ServiceTests {
    @MockBean
    private HelloWorldRepository helloWorldRepository;

    @Autowired
    private HelloWorldService helloWorldService;

    @Test
    void getAllSuccess_Test() {
        when(helloWorldRepository.findAll())
            .thenReturn(List.of(HelloWorld.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .build()));

        ResponseEntity<Object> responseEntity = helloWorldService.getAll();
        ApiResponse<Object> response = (ApiResponse) helloWorldService.getAll().getBody();
        List<HelloWorld> hello = (List<HelloWorld>) Objects.requireNonNull(response.getData());
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(1, hello.size());
    }
}
