package com.sohan.springdata;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    // JPQL Query for Any DATABASE
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age = ?2")
    Optional<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    // NATIVE Query for PSQL DATABASE Specific
    @Query(value = "SELECT * FROM student WHERE first_Name = ?1 AND age >= ?2",nativeQuery = true)
    Optional<Student> findStudentsByFirstNameEqualsAndAgeEqualsNative(String firstName, Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE  u.id = ?1")
    int deleteStudentById(Long id);



}
