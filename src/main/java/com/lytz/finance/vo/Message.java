/**
 * 
 */
package com.lytz.finance.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
@Entity
@Table(name = "message")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,include="all")
@DynamicUpdate
@DynamicInsert
public class Message extends TimestampHibernateEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -2636710232385049814L;

    @ManyToOne //default eager, no cascade
    @JoinColumn(name="receiver_id", updatable=false)
    private User receiver;
    
    @Column(nullable = false, length = 100, updatable=false)
    @Length(min = 4, max = 100)
    private String content;
    
    @ManyToOne //default eager, no cascade
    @JoinColumn(name="sender_id",updatable=false)
    private User sender;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    @NotNull
    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.UNREAD;
    
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

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
