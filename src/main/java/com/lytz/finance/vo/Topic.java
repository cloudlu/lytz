/**
 * 
 */
package com.lytz.finance.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
@Entity
@Table(name = "topic")
@NamedQueries({
    @NamedQuery(
            name = "findTopicByTitle",
            query = "select t from Topic t where t.title = :title "
    )
})
public class Topic extends TimestampHibernateEntity{
    /**
     * 
     */
    private static final long serialVersionUID = 5160846870694101761L;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    @NotNull
    @NotBlank
    @Length(min = 4, max = 50)
    private String title;
    @ManyToOne //default eager, no cascade
    @JoinColumn(name="user_id")
    private User user;
    @Basic(optional = false)
    @Column(nullable = false, length = 1000)
    @NotNull
    @NotBlank
    @Length(min = 4, max = 1000)
    private String content;
    @Basic(optional = true)
    @Column(nullable = true, length = 1000)
    @Length(min = 4, max = 1000)
    private String comment;
    @Basic(optional = true)
    @Column(nullable = true, length = 50, unique = true)
    @Email
    @Length(min = 1, max = 50)
    private String contactEmail;
    @Basic(optional = true)
    @Column(nullable = true, length = 20)
    private String contactPhoneNumber;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Status status;
    

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }
    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Topic)) {
            return false;
        }

        final Topic topic = (Topic) o;

        return topic.getId() == this.getId();

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (getId() != null ? getId().hashCode() : 0);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("title", title).add("user", user.getUsername())
                .add("status", status)
                .toString();
    }
}
