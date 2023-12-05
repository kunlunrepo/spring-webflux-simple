package com.msb;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-05 14:14
 */
public class BCryptPasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));
        System.out.println(encoder.matches("123","$2a$10$jIyWNYA4UfE1usuGoocnBODSPMInJ.0FLpxc76f/.Ac9Arw/GjWvC"));
    }
}
