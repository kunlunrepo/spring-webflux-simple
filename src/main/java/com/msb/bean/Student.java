package com.msb.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-06 09:14
 */
@Data
public class Student {

    @Id
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String address;
    private String remark;
    private boolean active;
    private LocalDateTime createAt;
    private String createBy;
    private LocalDateTime updateAt;
    private String updateBy;

}
