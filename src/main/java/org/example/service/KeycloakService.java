package org.example.service;


import org.example.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {

    Response userCreate(UserDTO userDTO);
    void delete(String userName);
}