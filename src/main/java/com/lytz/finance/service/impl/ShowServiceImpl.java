/**
 * 
 */
package com.lytz.finance.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lytz.finance.common.ShowQuery;
import com.lytz.finance.dao.ShowDAO;
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
    
    private ShowDAO showDAO;

    @Autowired
    @Qualifier("showDAO")
    public void setShowDAO(ShowDAO showDAO) {
        this.dao = showDAO;
        this.showDAO = showDAO;
    }
    
    public List<Show> findByQuery(ShowQuery query) {
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(RoleNameEnum.ROLE_ADMIN.name())){
            query.setStatus(Status.COMPLETED);
        }
        return showDAO.findByQuery(query);
    }

    public int getTotalCount(ShowQuery query) {
        return showDAO.getTotalCount(query);
    }
    
    @Override
    public Show save(Show show){
        if(null != show.getId()){
            Show oldShow = findById(show.getId());
            oldShow.setContent(show.getContent());
            oldShow.setTitle(show.getTitle());
            oldShow.setStatus(show.getStatus());
            return super.save(oldShow);
        } else {
            return super.save(show);
        }
    }
}
