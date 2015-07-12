/**
 * 
 */
package com.lytz.finance.web.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.lytz.finance.service.FileService;

/**
 * @author cloudlu
 *
 */
@Controller
public class FileController {
    
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
    
    //inject url/uploaddir later
    private FileService fileService;
    
    private LocaleResolver localeResolver;

    /**
     * @param fileService the fileService to set
     */
    @Autowired
    @Qualifier(value="fileService")
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

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
        if(LOG.isDebugEnabled()){
            LOG.debug(fileUpload.toString());
        }
        // validate a file was entered
        if (fileUpload.getFile() == null || fileUpload.getFile().length == 0) {
            return messageSource.getMessage("uploadForm.file", new Object[]{fileUpload.getName()}, localeResolver.resolveLocale((HttpServletRequest) request));
        }
        if(fileUpload.getType().equals("image/png") && fileUpload.getType().equals("image/bmp") && fileUpload.getType().equals("image/jpg") && fileUpload.getType().equals("image/gif") && fileUpload.getType().equals("image/jpeg")){
            throw new IllegalArgumentException("文件类型不对");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");

        String savedUrl = fileService.saveFile(file);
        
        
        return savedUrl.toString();
    }
}
