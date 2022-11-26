package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class SchoolService {
	private Logger logger = LoggerFactory.getLogger(SchoolService.class);
	private final StudentRepository studentRepository;
	
	public SchoolService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	public Integer getTotalStudents() {
		logger.info("Method 'getTotalStudents()' was invoked");
		return studentRepository.getTotalStudents();
	}
	
	public Double getAverageAgeOfStudents() {
		logger.info("Method 'getAverageAgeOfStudents()' was invoked");
		Double averageAgeOfStudents = studentRepository.getAverageAgeOfStudents();
		return averageAgeOfStudents;
	}
	
	public List<Student> getLastStudents(int studentsNumber) {
		logger.info("Method 'getLastStudents()' was invoked");
		return studentRepository.getLastStudents(studentsNumber);
	}
	
}