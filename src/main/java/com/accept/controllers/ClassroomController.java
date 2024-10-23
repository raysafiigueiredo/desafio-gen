package com.accept.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accept.dto.ClassroomDTO;
import com.accept.services.ClassroomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("classes")
@Tag(name = "Classes Controller", description = "REST API for managing classes")
public class ClassroomController {

	@Autowired
	private final ClassroomService classroomService;

	public ClassroomController(ClassroomService classroomService) {
		this.classroomService = classroomService;
	}

	@GetMapping
	@Operation(summary = "Get all Classroom", description = "Retrieve a list of all classesroom")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClassroomDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<ClassroomDTO>> getAll() {
		List<ClassroomDTO> classes = classroomService.getAllClasses();
		return ResponseEntity.ok(classes);
	}

	@GetMapping("{id}")
	@Operation(summary = "Get Classroom by ID", description = "Retrieve a specific classroom by its ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClassroomDTO.class))),
			@ApiResponse(responseCode = "404", description = "Classroom not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<ClassroomDTO> getById(@PathVariable UUID id) {
		ClassroomDTO classroomDTO = classroomService.getClassroomById(id);
		return ResponseEntity.ok(classroomDTO);
	}

	@PostMapping
	@Operation(summary = "Create a Classroom", description = "Creates a new class with the provided details")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Class created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClassroomDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<ClassroomDTO> create(@Valid @RequestBody ClassroomDTO classroomDTO) {
		ClassroomDTO createdClassroom = classroomService.createClassroom(classroomDTO);
		return new ResponseEntity<>(createdClassroom, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@Operation(summary = "Update a Classroom", description = "Update the details of an existing classroom")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Classroom updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClassroomDTO.class))),
			@ApiResponse(responseCode = "404", description = "Classroom not found"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<ClassroomDTO> update(@PathVariable UUID id, @Valid @RequestBody ClassroomDTO classroomDTO) {
		ClassroomDTO updatedClassroom = classroomService.updateClassroom(id, classroomDTO);
		return ResponseEntity.ok(updatedClassroom);
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Delete a Classroom", description = "Deletes a specific class by its ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Classroom deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Classroom not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		String message = "Classroom with ID " + id + " deleted successfully.";
		classroomService.deleteClassroom(id);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

}