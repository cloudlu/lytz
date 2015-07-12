/**
 * 
 */
package com.lytz.finance.web.common;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
public class FileUpload {

    private int length;
    @NotNull
    private byte[] file;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the file
     */
    public byte[] getFile() {
        return file;
    }
    /**
     * @param file the file to set
     */
    public void setFile(byte[] file) {
        this.file = file;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("name", name)
                .add("length", length)
                .add("type", type)
                .toString();
    }
}
