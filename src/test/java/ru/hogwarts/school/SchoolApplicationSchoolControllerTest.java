package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.SchoolController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SchoolApplicationSchoolControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private SchoolController schoolController;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeAll
    public void init() {
        String url = "http://localhost:" + port + "/student/add?name=Федор&age=22";
        testRestTemplate.postForObject(url, null, Student.class);

        url = "http://localhost:" + port + "/student/add?name=Елена&age=24";
        testRestTemplate.postForObject(url, null, Student.class);

        url = "http://localhost:" + port + "/student/add?name=Игнат&age=38";
        testRestTemplate.postForObject(url, null, Student.class);

        url = "http://localhost:" + port + "/student/add?name=Артем&age=41";
        testRestTemplate.postForObject(url, null, Student.class);

        url = "http://localhost:" + port + "/student/add?name=Бертран&age=19";
        testRestTemplate.postForObject(url, null, Student.class);
    }

    @Test
    public void getTotalStudents() {
        String expectedTotal = "В школе учатся 5 студентов";

        String url = "http://localhost:" + port + "/school/total";
        String actualTotal = testRestTemplate.getForObject(url, String.class);

        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    @Test
    public void getAverageAgeOfStudents() {
        String expected = "Средний возраст студентов равен 28,80";

        String url = "http://localhost:" + port + "/school/average";
        String actual = testRestTemplate.getForObject(url, String.class);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getLastStudents() {
        int expectedQuantityOfStudents = 3;
        List<String> expectedNames = List.of("Игнат", "Артем", "Бертран");

        String url = "http://localhost:" + port + "/school/last-students?number=" + expectedQuantityOfStudents;
        ResponseEntity<Collection<Student>> response = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Student>>() {
        });
        Collection<Student> students = response.getBody();

        Assertions.assertEquals(expectedQuantityOfStudents, students.size());

        List<String> studentNames = students.stream()
                .map(s -> s.getName())
                .toList();

        org.assertj.core.api.Assertions.assertThat(studentNames).containsExactlyInAnyOrderElementsOf(expectedNames);
    }
}
