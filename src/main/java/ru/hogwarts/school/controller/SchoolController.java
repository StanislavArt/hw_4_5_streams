package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.SchoolService;

import java.util.Collection;

@RestController
@RequestMapping("school")
public class SchoolController {
	
	private final SchoolService schoolService;
	
	public SchoolController(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	
	@GetMapping("total")
	public ResponseEntity<String> getTotalStudents() {
		Integer total = schoolService.getTotalStudents();
		String result = String.format("В школе учатся %d студентов", total);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("average")
	public ResponseEntity<String> getAverageAgeOfStudents() {
		Double averageAge = schoolService.getAverageAgeOfStudents();
		String result = String.format("Средний возраст студентов равен %.2f", averageAge);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("last-students")
	public ResponseEntity<Collection<Student>> getLastStudents(@RequestParam("number") Integer studentsNumber) {
		Collection<Student> students = schoolService.getLastStudents(studentsNumber);
		return ResponseEntity.ok().body(students);
	}
	
}