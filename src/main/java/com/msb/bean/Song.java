package com.msb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-05 15:56
 */
@Data
@AllArgsConstructor
public class Song {

    private Integer id;
    private String name;
    private String artist;
    private String album;

}
