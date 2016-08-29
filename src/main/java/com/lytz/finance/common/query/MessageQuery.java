/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.lytz.finance.vo.MessageStatus;

/**
 * @author cloud
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor 
@AllArgsConstructor
public class MessageQuery extends Query {

    private MessageStatus status;
    
    public MessageQuery(MessageQuery query){
        super(query);
        this.status = query.status;
    }

}
