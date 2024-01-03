package org.example.controller;

import org.example.dto.ProjectDTO;
import org.example.entity.ResponseWrapper;
import org.example.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getProjects() {
        List<ProjectDTO> projectDTOList = projectService.listAllProjects();

        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved", projectDTOList, HttpStatus.OK));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") String code) {
        ProjectDTO projectDTO = projectService.getByProjectCode(code);

        return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved", projectDTO, HttpStatus.OK));
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO) {
        projectService.save(projectDTO);

        return ResponseEntity.ok(new ResponseWrapper("Project is created successfully", HttpStatus.CREATED));
    }

    @PutMapping()
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO) {
        projectService.update(projectDTO);

        return ResponseEntity.ok(new ResponseWrapper("Project is updated successfully", HttpStatus.OK));
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.delete(projectCode);

        return ResponseEntity.ok(new ResponseWrapper("Project deleted successfully", HttpStatus.OK));
    }

    @GetMapping("/{completeProject}")
    public ResponseEntity<ResponseWrapper> getProjectByManager() {
        List<ProjectDTO> projectDTOList = projectService.listAllProjectDetails();

        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved", projectDTOList, HttpStatus.OK));
    }


    @PutMapping("/{completeProject}")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectCode);

        return ResponseEntity.ok(new ResponseWrapper("Project completed successfully", HttpStatus.OK));
    }


}
