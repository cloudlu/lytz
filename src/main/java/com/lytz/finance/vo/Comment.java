/**
 * 
 */
package com.lytz.finance.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
@Entity
@Table(name = "comment")
public class Comment extends TimestampHibernateEntity {

    
    /**
     * 
     */
    private static final long serialVersionUID = 6835066279858762404L;

    private Topic topic;
    
    @Column(nullable = false, length = 100)
    @Length(min = 4, max = 100)
    private String content;
    
    private User owner;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this.getClass())
                .add("id", getId())
                .add("content", content);
        if(null != topic){
            helper.add("topic", topic.getTitle());
        }
        if(null != owner){
            helper.add("owner", owner.getUsername());
        }
        return helper.toString();
    }
}
