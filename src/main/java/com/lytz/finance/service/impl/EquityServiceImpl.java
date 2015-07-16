/**
 * 
 */
package com.lytz.finance.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lytz.finance.common.LYTZUtils;
import com.lytz.finance.common.EquityQuery;
import com.lytz.finance.dao.EquityDAO;
import com.lytz.finance.service.EquityService;
import com.lytz.finance.service.FileService;
import com.lytz.finance.vo.Equity;
import com.lytz.finance.vo.RoleNameEnum;
import com.lytz.finance.vo.Status;

/**
 * @author cloudlu
 *
 */
@Service("equityService")
public class EquityServiceImpl extends BaseServiceImpl<Equity, Integer> implements
        EquityService {
    
    private static final Logger LOG = LoggerFactory.getLogger(EquityServiceImpl.class);
    
    private FileService fileService;
    
    @Autowired
    @Qualifier("fileService")
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    } 
    
    private EquityDAO equityDAO;

    @Autowired
    @Qualifier("equityDAO")
    public void setEquityDAO(EquityDAO equityDAO) {
        this.dao = equityDAO;
        this.equityDAO = equityDAO;
    }
    
    public List<Equity> findByQuery(EquityQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(RoleNameEnum.ROLE_ADMIN.name())){
            query.setStatus(Status.COMPLETED);
        }
        return equityDAO.findByQuery(query);
    }

    public int getTotalCount(EquityQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        return equityDAO.getTotalCount(query);
    }
    
    @Override
    public Equity save(Equity equity){
       if(null != equity.getId()){
            //due to create/update time is not passed back from page
            Equity oldEquity = findById(equity.getId());
            if(null == oldEquity){
                return null;
            }
            List<String> oldFilelist = LYTZUtils.getFilePathFromContent(oldEquity.getContent());
            
            if(LOG.isDebugEnabled()){
                LOG.debug("the old content contains " + oldFilelist.size() + " images in image server");
            }
           /* oldEquity.setContent(equity.getContent());
            oldEquity.setTitle(equity.getTitle());
            oldEquity.setStatus(equity.getStatus());
            oldEquity.setVersion(equity.getVersion());*/
            equity = super.save(equity);
            List<String> newFilelist = LYTZUtils.getFilePathFromContent(equity.getContent());
            for(String filePath : oldFilelist){
                if(!newFilelist.contains(filePath) && fileService.isFileExists(filePath)){
                    if(LOG.isDebugEnabled()){
                        LOG.debug("unusedFile: " + filePath);
                    }
                    fileService.removeFile(filePath);
                }
            }
            return equity;
        } else {
            return super.save(equity);
        }
    }
    
    @Override
    public void remove(Equity equity) {
        super.remove(equity);
        removeAllRelatedImages(equity);
    }

    private void removeAllRelatedImages(Equity equity) {
        List<String> filelist = LYTZUtils.getFilePathFromContent(equity.getContent());
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
    public void remove(Integer id) {
        Equity equity = dao.findById(id);
        super.remove(equity);
        removeAllRelatedImages(equity);
    }
}
