package com.msb.controller;

import com.msb.bean.Student;
import com.msb.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-06 09:13
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {

//    private final StudentRepository studentRepository;
//
//    public StudentController (StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
//
//    @GetMapping
//    public Flux<Student> index() {
//        return studentRepository.findAll();
//    }


}
