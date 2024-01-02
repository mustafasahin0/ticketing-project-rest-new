package org.example.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Status;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {

    private Long id;
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject;
    private String taskDetail;
    private Status taskStatus;
    private LocalDate assignedDate;

}
