/**
 * 
 */
package com.lytz.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lytz.finance.common.TopicQuery;
import com.lytz.finance.dao.TopicDAO;
import com.lytz.finance.service.TopicService;
import com.lytz.finance.vo.Topic;

/**
 * @author cloudlu
 *
 */
@Service("topicService")
public class TopicServiceImpl extends BaseServiceImpl<Topic, Integer> implements TopicService{

    private TopicDAO topicDAO;

    @Autowired
    @Qualifier("topicDAO")
    public void setTopicDAO(TopicDAO topicDAO) {
        this.dao = topicDAO;
        this.topicDAO = topicDAO;
    }
    
    public List<Topic> findByQuery(TopicQuery query) {
        return topicDAO.findByQuery(query);
    }

    public int getTotalCount(TopicQuery query) {
        return topicDAO.getTotalCount(query);
    }

}
