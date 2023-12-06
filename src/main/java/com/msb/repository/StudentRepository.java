package com.msb.repository;

import com.msb.bean.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-06 09:17
 */
public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {
}
