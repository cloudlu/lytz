/**
 * 
 */
package com.lytz.finance.service;

import java.util.List;

import com.lytz.finance.common.query.TopicQuery;
import com.lytz.finance.vo.Comment;
import com.lytz.finance.vo.Topic;

/**
 * @author cloudlu
 *
 */
public interface TopicService extends BaseService<Topic, Integer> {

    //Topic getTopicByTitle(String title);
    
    List<Topic> findByQuery(TopicQuery query);
    
    int getTotalCount(TopicQuery query);

    Comment addComment(Integer topicId, Comment comment);
}
