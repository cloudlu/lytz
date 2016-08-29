/**
 * 
 */
package com.lytz.finance.web.common;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author cloudlu
 *
 */
@Setter
@Getter
@ToString
public class FileUpload {

    private int length;
    @NotNull
    private byte[] file;
    @NotBlank
    private String name;
    @NotBlank
    private String type;

}
