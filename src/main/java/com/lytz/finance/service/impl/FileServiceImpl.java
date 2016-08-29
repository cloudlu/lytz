/**
 * 
 */
package com.lytz.finance.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lytz.finance.service.FileService;

/**
 * @author cloudlu
 *
 */
@Log4j2
@Service("fileService")
@RequiresRoles("ROLE_ADMIN")
public class FileServiceImpl implements FileService{

    @Value("${image.uploadUrl}")
    private String uri = null;
    
    private String uploadDir = null;
    
    @Value("${image.uploadDir}")
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
        File dirPath = new File(uploadDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    public String saveFile(CommonsMultipartFile file) throws IOException {
        
        InputStream stream = null;
        OutputStream bos = null;
        String newFilename = null;
        try {
            newFilename = new StringBuilder(Thread.currentThread().getName()).append(System.currentTimeMillis())
                    .append("_").append(InetAddress.getLocalHost().getHostAddress()).append("_img").append(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))).toString();
            stream = file.getInputStream();

            //write the file to the file specified
            bos = new FileOutputStream(uploadDir + newFilename);
            int bytesRead;
            byte[] buffer = new byte[8192];

            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } finally{
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(stream);
        }
        return uri + newFilename;
    }

    @Override
    public boolean removeFile(String filePath) {
        File file = new File(uploadDir + filePath.substring(filePath.lastIndexOf("/")));
        if(LOG.isDebugEnabled()){
            LOG.debug("try to remove unused img file: " + file.getAbsolutePath());
        }
        return file.delete();
    }

    @Override
    public boolean isFileExists(String filePath) {
        if(LOG.isDebugEnabled()){
            LOG.debug(filePath + " sub string of file path: " + filePath.substring(0, filePath.lastIndexOf("/")));
        }
        if(filePath.startsWith("http") && filePath.startsWith(uri)){
            return true;
        }
        if(uri.contains(filePath.substring(0, filePath.lastIndexOf("/")))){
            return true;
        }
        return false;
    }

}
