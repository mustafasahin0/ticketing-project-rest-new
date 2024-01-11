package org.example.controller;

import org.example.dto.ProjectDTO;
import org.example.dto.RoleDTO;
import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static UserDTO userDTO;

    static ProjectDTO projectDTO;

    @BeforeAll
    static void setUp() {
        userDTO = UserDTO.builder()
                .id(2L)
                .firstName("ozzy")
                .lastName("ozzy")
                .userName("ozzy")
                .passWord("Abc1")
                .confirmPassWord("Abc1")
                .role(new RoleDTO(2L, "Manager"))
                .gender(Gender.MALE)
                .build();

        projectDTO = ProjectDTO.builder()
                .projectCode("Api1")
                .projectName("Api-ozzy")
                .assignedManager(userDTO)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectDetail("Api Test")
                .projectStatus(Status.OPEN)
                .build();
    }

    @Test
    public void givenNoToken_whenGetRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }
}