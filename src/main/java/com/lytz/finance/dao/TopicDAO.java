/**
 * 
 */
package com.lytz.finance.dao;

import java.util.List;

import com.lytz.finance.common.query.TopicQuery;
import com.lytz.finance.vo.Topic;

/**
 * @author cloudlu
 *
 */
public interface TopicDAO extends BaseDAO<Topic, Integer> {

    //Topic getTopicByTitle(String title);
    
    List<Topic> findByQuery(TopicQuery query);
    
    int getTotalCount(TopicQuery query);
}
