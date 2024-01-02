package org.example.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Gender;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private String confirmPassWord;
    private boolean enabled;
    private String phone;
    private RoleDTO role;
    private Gender gender;

}


