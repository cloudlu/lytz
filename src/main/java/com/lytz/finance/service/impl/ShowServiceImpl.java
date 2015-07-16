/**
 * 
 */
package com.lytz.finance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lytz.finance.common.LYTZUtils;
import com.lytz.finance.common.ShowQuery;
import com.lytz.finance.dao.ShowDAO;
import com.lytz.finance.service.FileService;
import com.lytz.finance.service.ShowService;
import com.lytz.finance.vo.RoleNameEnum;
import com.lytz.finance.vo.Show;
import com.lytz.finance.vo.Status;

/**
 * @author cloudlu
 *
 */
@Service("showService")
public class ShowServiceImpl extends BaseServiceImpl<Show, Integer> implements
        ShowService {

    private static final Logger LOG = LoggerFactory.getLogger(ShowServiceImpl.class);
    
    private FileService fileService;
    
    @Autowired
    @Qualifier("fileService")
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    private ShowDAO showDAO;

    @Autowired
    @Qualifier("showDAO")
    public void setShowDAO(ShowDAO showDAO) {
        this.dao = showDAO;
        this.showDAO = showDAO;
    }
    
    public List<Show> findByQuery(ShowQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(RoleNameEnum.ROLE_ADMIN.name())){
            query.setStatus(Status.COMPLETED);
        }
        return showDAO.findByQuery(query);
    }

    public int getTotalCount(ShowQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        return showDAO.getTotalCount(query);
    }
    
    @Override
    @RequiresRoles("ROLE_ADMIN")
    public Show save(Show show){
       if(null != show.getId()){
            //due to create/update time is not passed back from page
            Show oldShow = findById(show.getId());
            if(null == oldShow){
                return null;
            }
            List<String> oldFilelist = LYTZUtils.getFilePathFromContent(oldShow.getContent());
            if(LOG.isDebugEnabled()){
                LOG.debug("the old content contains " + oldFilelist.size() + " images in image server");
            }
           /* oldShow.setContent(show.getContent());
            oldShow.setTitle(show.getTitle());
            oldShow.setStatus(show.getStatus());
            oldShow.setVersion(show.getVersion());*/
            show = super.save(show);
            List<String> newFilelist = LYTZUtils.getFilePathFromContent(show.getContent());
            for(String filePath : oldFilelist){
                if(!newFilelist.contains(filePath) && fileService.isFileExists(filePath)){
                    if(LOG.isDebugEnabled()){
                        LOG.debug("unusedFile: " + filePath);
                    }
                    fileService.removeFile(filePath);
                }
            }
            
            return show;
        } else {
            return super.save(show);
        }
    }
    
    @Override
    @RequiresRoles("ROLE_ADMIN")
    public void remove(Show show) {
        super.remove(show);
        removeAllRelatedImages(show);
    }

    private void removeAllRelatedImages(Show show) {
        List<String> filelist = LYTZUtils.getFilePathFromContent(show.getContent());
        for(String filePath : filelist){
            if(fileService.isFileExists(filePath)){
                if(LOG.isDebugEnabled()){
                    LOG.debug("unusedFile: " + filePath);
                }
                fileService.removeFile(filePath);
            }
        }
    }
    
    @Override
    @RequiresRoles("ROLE_ADMIN")
    public void remove(Integer id) {
        Show show = dao.findById(id);
        super.remove(show);
        removeAllRelatedImages(show);
    }
}
