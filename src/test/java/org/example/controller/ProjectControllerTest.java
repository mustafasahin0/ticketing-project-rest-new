package org.example.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dto.ProjectDTO;
import org.example.dto.RoleDTO;
import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    static UserDTO userDTO;
    static ProjectDTO projectDTO;

    static String token;

    @BeforeAll
    static void setUp() {

        token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwbV9taDF0cU9fcm02N0E2WEVhSEpTZnFpOHBXcWdudzVTVUE2d0lJWS1FIn0.eyJleHAiOjE3MDUwMjk1MjcsImlhdCI6MTcwNTAyOTIyNywianRpIjoiYTE1NzRmYmEtOTJmMS00NDM5LWEyMzgtYWU3MWNmZGRmNmUzIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2V4YW1wbGUtZGV2IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjhkNzMwNjEyLWI1YTYtNDAyOS05MTEyLThjZDg5NGVjNTYwZSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRpY2tldGluZy1hcHAiLCJzZXNzaW9uX3N0YXRlIjoiYjUxYzUyYTAtYWE5YS00NGY5LWJlMjEtOGUxYWU4NWNmYmIyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODEiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWV4YW1wbGUtZGV2Il19LCJyZXNvdXJjZV9hY2Nlc3MiOnsidGlja2V0aW5nLWFwcCI6eyJyb2xlcyI6WyJNYW5hZ2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiYjUxYzUyYTAtYWE5YS00NGY5LWJlMjEtOGUxYWU4NWNmYmIyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6Im1pa2UifQ.m-4uWrYJGwEP14hnoYZ3EbkRKFx8fwWWA9vpebNjx0GodiJZGJVn61Nu7m7JOSEwomwxscJ50WB8oFeiVOf_QLHV1lHyVOh4QaHJ4iAtUbnnnzPlJcS3Wcm7TRDpFx_czVZ81kt08nSEFIPUpLeaPJoZY2tZVlxymoGBO7vqNkisYVUhTzSNgOYNoPlYu4Waw4zQeE_HemtvZUHi1Kw7bvrkCQjLl-5f7-aawbQU1udKLb7N-clY8Kq3FYmzoxOmcbrOr7xhrYOphjXD0KSG6woWrUJulUd-tVaLJQ1zFbLSO2i2pqH7rG9aokLu7MF7an00NOj3eNSmTuhug_iGEQ";

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

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/project"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void givenToken_whenGetRequest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/project")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].projectCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].assignedManager.userName").isNotEmpty());

    }

    @Test
    public void givenToken_createProject() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/project")
                .header("Authorization", token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

    }

    @Test
    public void givenToken_updateProject() throws Exception {

        projectDTO.setProjectName("Api-mike");
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/project")
                        .header("Authorization", token)
                        .content(toJsonString(projectDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Project is successfully updated"));
    }

    @Test
    public void givenToken_deleteProject() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/project/" + projectDTO.getProjectCode())
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private static String toJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}