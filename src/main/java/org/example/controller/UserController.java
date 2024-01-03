package org.example.controller;

import org.example.dto.UserDTO;
import org.example.entity.ResponseWrapper;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUsers() {
        List<UserDTO> userDTOList = userService.listAllUsers();

        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved", userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("userName") String userName) {

        UserDTO userDTO = userService.findByUserName(userName);

        return ResponseEntity.ok(new ResponseWrapper("User is successfully retrieved", userDTO, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("The user account has been successfully created.", HttpStatus.CREATED));
    }

    @PutMapping()
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);

        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated", userDTO, HttpStatus.OK));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("userName") String userName) {
        userService.deleteByUserName(userName);

        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted", HttpStatus.OK));

        // 204 - HttpStatus.NO_CONTENT
    }
}