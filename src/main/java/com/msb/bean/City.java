package com.msb.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-06 09:34
 */
@Data
public class City implements Serializable {

    private static final long serialVersionUID = -1L;

    // 城市编号
    @Id
    private Long id;

    // 省份编号
    private Long provinceId;

    // 城市名称
    private String cityName;

    // 描述
    private String description;

}
