/**
 * 
 */
package com.lytz.finance.web.show;

/**
 * @author cloudlu
 *
 */
public class FileUpload {

    private int length;
    private byte[] file;
    private String name;
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
}
