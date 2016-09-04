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

import com.lytz.finance.common.query.TopicQuery;
import com.lytz.finance.dao.CommentDAO;
import com.lytz.finance.service.MessageService;
import com.lytz.finance.dao.TopicDAO;
import com.lytz.finance.service.TopicService;
import com.lytz.finance.service.UserService;
import com.lytz.finance.service.exception.IllegalWordException;
import com.lytz.finance.utils.wordFilter.MatchType;
import com.lytz.finance.utils.wordFilter.SensitiveWordFilter;
import com.lytz.finance.vo.Comment;
import com.lytz.finance.vo.Message;
import com.lytz.finance.vo.RoleNameEnum;
import com.lytz.finance.vo.Topic;
import com.lytz.finance.vo.TopicStatus;

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
    
    private CommentDAO commentDAO;

    @Autowired
    @Qualifier("commentDAO")
    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }
    
    private MessageService messageService;

    @Autowired
    @Qualifier("messageService")
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
    
    private UserService userService;

    @Autowired
    @Qualifier("userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    private SensitiveWordFilter wordFilter;

    @Autowired
    @Qualifier("sensitiveWordFilter")    
    public void setWordFilter(SensitiveWordFilter wordFilter) {
        this.wordFilter = wordFilter;
    }
    
    
    public List<Topic> findByQuery(TopicQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(RoleNameEnum.ROLE_ADMIN.name())){
            query.setUsername((String)currentUser.getPrincipal());
            query.setExcludeStatus(TopicStatus.CANCELLED);
        }
        return topicDAO.findByQuery(query);
    }

    public int getTotalCount(TopicQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(RoleNameEnum.ROLE_ADMIN.name())){
            query.setUsername((String)currentUser.getPrincipal());
            query.setExcludeStatus(TopicStatus.CANCELLED);
        }
        return topicDAO.getTotalCount(query);
    }
    
    @Override
    public Topic create(Topic topic) {
        if(null == topic || null != topic.getId()){
            throw new IllegalArgumentException("topic should not be null or topic id should be null");
        }
        if(wordFilter.containsSensitiveWord(topic.getTitle(), MatchType.MIN)){
            throw new IllegalWordException("title contains invalid word");
        }
        if(wordFilter.containsSensitiveWord(topic.getContent(), MatchType.MIN)){
            topic.setContent(wordFilter.replaceSensitiveWord(topic.getContent(), MatchType.MAX));
        }
        Subject currentUser = SecurityUtils.getSubject();
        topic.setOwner(userService.getUserByName((String)currentUser.getPrincipal()));
        return super.save(topic);
    }
    
    @Override
    public Topic update(Topic topic){
        if(null == topic || null == topic.getId()){
            throw new IllegalArgumentException("topic should not be null and topic id should not be null");
        }
        Subject currentUser = SecurityUtils.getSubject();
        Topic oldTopic = findById(topic.getId());
        if(null == oldTopic){
            throw new IllegalArgumentException("topic id invalid");
        }
        if(currentUser.hasRole(RoleNameEnum.ROLE_ADMIN.name()) ||
                ((String)currentUser.getPrincipal()).equals(oldTopic.getOwner().getUsername())){
            topic.setOwner(oldTopic.getOwner());
            return super.save(topic);
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
    }
    @Override
    public Topic save(Topic topic){
        if(null == topic){
            throw new IllegalArgumentException("topic should not be null");
        }
        if(null != topic.getId()){
            return update(topic);
        } else {
            return create(topic);
        }
    }
    
    @Override
    public void remove(Topic topic) {
        if(null == topic){
            return;
        }
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.hasRole(RoleNameEnum.ROLE_ADMIN.name())){
            if(((String)currentUser.getPrincipal()).equals(topic.getOwner().getUsername())){
                topic.setStatus(TopicStatus.CANCELLED);
                save(topic);
            } else {
                return;
            }
        } else {
            super.remove(topic);
        }
    }
    
    @Override
    public void remove(Integer id) {
        Topic topic = super.findById(id);
        if(null == topic){
            return;
        }
        remove(topic);
    }

    @Override
    public Comment addComment(Integer topicId, Comment comment) {
        Topic topic = findById(topicId);
        Subject currentUser = SecurityUtils.getSubject();
        comment.setOwner(userService.getUserByName((String)currentUser.getPrincipal()));
        comment.setTopic(topic);
        commentDAO.save(comment);
        if(!comment.getOwner().equals(topic.getOwner())){
            Message message = new Message();
            message.setSender(comment.getOwner());
            message.setReceiver(topic.getOwner());
            message.setContent(topic.getTitle() + "有更新");
            messageService.save(message);
        }
        topic.addComment(comment);
        save(topic);
        return comment;
    }
}
