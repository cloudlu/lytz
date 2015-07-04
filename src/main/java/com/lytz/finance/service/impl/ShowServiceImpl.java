/**
 * 
 */
package com.lytz.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lytz.finance.common.ShowQuery;
import com.lytz.finance.dao.ShowDAO;
import com.lytz.finance.service.ShowService;
import com.lytz.finance.vo.Show;

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
        return showDAO.findByQuery(query);
    }

    public int getTotalCount(ShowQuery query) {
        return showDAO.getTotalCount(query);
    }
}
