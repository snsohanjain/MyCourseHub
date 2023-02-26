package com.sohan.springdata;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class StudentConfigration {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String  email = String.format("%s.%s@sohanjain.com",firstName,lastName);
            Student student = new Student(firstName,lastName,email,faker.number().numberBetween(17,55));

            student.addBook(
                    new Book("Clean Code",
                            LocalDateTime.now().minusDays(4)));

            student.addBook(
                    new Book("Think and Rich",
                            LocalDateTime.now()));

            student.addBook(
                    new Book("Spring Data JPA",
                            LocalDateTime.now().minusYears(6)));

            StudentIdCard studentIdCard = new StudentIdCard("123456789",student);

            student.setStudentIdCard(studentIdCard);

            student.addEnrolment(new Enrolment(new EnrolmentId(1L,1L),student,new Course("Computer Science","IT"),LocalDateTime.now()));
            student.addEnrolment(new Enrolment(new EnrolmentId(2L,2L),student,new Course("Civil Engineering","CV"),LocalDateTime.now().minusDays(18)));
            student.addEnrolment(new Enrolment(new EnrolmentId(3L,3L),student,new Course("Mechanical Engineering","ME"),LocalDateTime.now().minusYears(5)));

//            student.enrolToCourse(new Course("Computer Science","IT"));
//            student.enrolToCourse(new Course("Civil Engineering","Civil"));

            studentRepository.save(student);

            studentRepository.findById(1L).ifPresent(s -> {
                System.out.println("Fetch book lazy...");
                List<Book> books = student.getBooks();
                books.forEach(book -> {
                    System.out.println(s.getFirstName() + " Borrowed " + book.getBookName());
                });
            });
//
//            studentIdCardRepository.findById(1L).ifPresent(System.out::println);

//            studentRepository.deleteById(1L);


        };
    }

    private void sorting(StudentRepository studentRepository) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        studentRepository.findAll(sort).forEach(student -> System.out.println(student.getFirstName()));
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i <= 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String  email = String.format("%s.%s@sohanjain.com",firstName,lastName);
            Student student = new Student(firstName,lastName,email,faker.number().numberBetween(17,55));
            studentRepository.save(student);
        }
    }
}
