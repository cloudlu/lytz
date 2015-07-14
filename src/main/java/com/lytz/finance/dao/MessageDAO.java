/**
 * 
 */
package com.lytz.finance.dao;

import java.util.List;

import com.lytz.finance.common.MessageQuery;
import com.lytz.finance.vo.Message;

/**
 * @author cloud
 *
 */
public interface MessageDAO extends BaseDAO<Message, Integer> {

    List<Message> findByQuery(MessageQuery query);
    
    int getTotalCount(MessageQuery query);
}
