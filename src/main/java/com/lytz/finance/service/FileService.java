/**
 * 
 */
package com.lytz.finance.service;

import java.io.IOException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author cloudlu
 *
 */
public interface FileService {

    String saveFile( CommonsMultipartFile file) throws IOException;
    
    boolean removeFile(String filePath);
    
    boolean isFileExists(String filePath);
    
}
