package org.example.testing_review;

import org.example.dto.RoleDTO;
import org.example.dto.UserDTO;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.KeycloakService;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectService projectService;
    @Mock
    private TaskService taskService;
    @Mock
    private KeycloakService keycloakService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    @Spy
    private UserMapper userMapper = new UserMapper(new ModelMapper());

    User user;
    UserDTO userDTO;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("user");
        user.setPassWord("Abc1");
        user.setEnabled(true);
        user.setRole(new Role("Manager"));

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setUserName("user");
        userDTO.setPassWord("Abc1");
        userDTO.setEnabled(true);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setDescription("Manager");

        userDTO.setRole(roleDTO);
    }

    private List<User> getUsers(){
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Emily");

        return List.of(user,user2);
    }

    private List<UserDTO> getUserDTOs(){
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setFirstName("Emily");

        return List.of(userDTO,userDTO2);
    }

    @Test
    void should_list_all_users() {
        // stub
        when (userRepository.findAllByIsDeletedOrderByFirstNameDesc(false)).thenReturn(getUsers());

        List<UserDTO> expectedUserDTOs = getUserDTOs();
        List<UserDTO> actualUserDTOs = userService.listAllUsers();

        // AssertJ
        assertThat(actualUserDTOs).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedUserDTOs);
    }

    //Task
    @Test
    void should_fine_user_by_username() {
        when(userRepository.findByUserNameAndIsDeleted("user", false)).thenReturn(user);
        UserDTO actualUserDTO = userService.findByUserName("user");
        UserDTO expectedUserDTO = userDTO;

        assertThat(actualUserDTO).usingRecursiveComparison().isEqualTo(expectedUserDTO);
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        when(userRepository.findByUserNameAndIsDeleted("abc", false)).thenReturn(null);


        // We call the method and capture the exception adn its message
        Throwable throwable = catchThrowable(() -> userService.findByUserName("abc"));

        // We use asssertInstanceOf method to verify exception type
        assertInstanceOf(NoSuchElementException.class, throwable);

        assertEquals("User Not Found!", throwable.getMessage());


    }
}
