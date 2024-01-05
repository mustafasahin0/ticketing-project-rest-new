package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.annotation.DefaultExceptionMessage;
import org.example.dto.UserDTO;
import org.example.entity.ResponseWrapper;
import org.example.exception.TicketingProjectException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "UserController", description = "User API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Get Users")
    public ResponseEntity<ResponseWrapper> getUsers() {
        List<UserDTO> userDTOList = userService.listAllUsers();

        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved", userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{userName}")
    @RolesAllowed("Admin")
    @Operation(summary = "Get User By Username")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("userName") String userName) {

        UserDTO userDTO = userService.findByUserName(userName);

        return ResponseEntity.ok(new ResponseWrapper("User is successfully retrieved", userDTO, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Create User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("The user account has been successfully created.", HttpStatus.CREATED));
    }

    @PutMapping()
    @RolesAllowed("Admin")
    @Operation(summary = "Update User")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);

        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated", userDTO, HttpStatus.OK));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Admin")
    @Operation(summary = "Delete User")
    @DefaultExceptionMessage(defaultMessage = "Failed to delete user.")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("userName") String userName) throws TicketingProjectException {
        userService.delete(userName);

        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted", HttpStatus.OK));

        // 204 - HttpStatus.NO_CONTENT
    }
}