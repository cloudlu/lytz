/**
 * 
 */
package com.lytz.finance.service;

import java.util.List;

import com.lytz.finance.common.MessageQuery;
import com.lytz.finance.vo.Message;

/**
 * @author cloudlu
 *
 */
public interface MessageService extends BaseService<Message, Integer> {

    List<Message> findByQuery(MessageQuery query);
    
    int getTotalCount(MessageQuery query);
}
