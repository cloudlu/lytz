/**
 * 
 */
package com.lytz.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lytz.finance.common.MessageQuery;
import com.lytz.finance.dao.MessageDAO;
import com.lytz.finance.service.MessageService;
import com.lytz.finance.vo.Message;

/**
 * @author cloudlu
 *
 */
public class MessageServiceImpl extends BaseServiceImpl<Message, Integer>
        implements MessageService {

    private MessageDAO messageDAO;

    @Autowired
    @Qualifier("messageDAO")
    public void setMessageDAO(MessageDAO messageDAO) {
        this.dao = messageDAO;
        this.messageDAO = messageDAO;
    }

    public List<Message> findByQuery(MessageQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        return messageDAO.findByQuery(query);
    }

    public int getTotalCount(MessageQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        return messageDAO.getTotalCount(query);
    }
}
