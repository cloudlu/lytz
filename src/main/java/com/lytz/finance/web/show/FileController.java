/**
 * 
 */
package com.lytz.finance.web.show;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author cloudlu
 *
 */
@Controller
public class FileController {
    
    //inject url/uploaddir later
    private String uri = null;
    
    private String uploadDir = null;

    private LocaleResolver localeResolver;
    
    /**
     * @param localResolver the localResolver to set
     */
    @Autowired
    @Qualifier(value="localeResolver")
    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }
    
    private MessageSource messageSource;
    
    /**
     * @param messageSource the messageSource to set
     */
    @Autowired
    @Qualifier(value="messageSource")
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }
    
    @RequestMapping(value = "/admin/file/upload", method = RequestMethod.POST)
    public @ResponseBody String onSubmit(@Valid FileUpload fileUpload, BindingResult errors, HttpServletRequest request)
            throws Exception {

        // validate a file was entered
        if (fileUpload.getFile() == null || fileUpload.getFile().length == 0) {
            return messageSource.getMessage("uploadForm.file", new Object[]{fileUpload.getName()}, localeResolver.resolveLocale((HttpServletRequest) request));
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");

        // the directory to upload to
        String uploadDir = request.getSession().getServletContext().getRealPath("/resources");

        // The following seems to happen when running jetty:run
        if (uploadDir == null) {
            uploadDir = new File("src/main/webapp/resources").getAbsolutePath();
        }
        uploadDir += "/";

        // Create the directory if it doesn't exist
        File dirPath = new File(uploadDir);

        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        InputStream stream = null;
        OutputStream bos = null;
        String newFilename = System.currentTimeMillis()+"_" + InetAddress.getLocalHost().getHostAddress() +"_img";
        try{
        //retrieve the file data
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
        return new StringBuilder(request.getContextPath()).append("/resources/").append(newFilename).toString();
    }
}
