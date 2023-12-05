package com.msb.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-05 13:53
 */
@Data
public class PasswordDTO {

    private String raw;

    private String secured;

    public PasswordDTO(@JsonProperty("raw") String raw,
                       @JsonProperty("secured") String secured) {
        this.raw = raw;
        this.secured = secured;

    }

}
