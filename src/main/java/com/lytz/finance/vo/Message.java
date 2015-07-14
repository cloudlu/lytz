/**
 * 
 */
package com.lytz.finance.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
@Entity
@Table(name = "message")
public class Message extends TimestampHibernateEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -2636710232385049814L;

    private User receiver;
    
    @Column(nullable = false, length = 100)
    @Length(min = 4, max = 100)
    private String content;
    
    private User sender;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    @NotNull
    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.UNREAD;
    
    @Override
    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this.getClass())
                .add("id", getId())
                .add("content", content);
        if(null != sender){
            helper.add("sender", sender.getUsername());
        }
        if(null != receiver){
            helper.add("receiver", receiver.getUsername());
        }
        helper.add("status", status);
        return helper.toString();
    }
}
