/**
 * 
 */
package com.lytz.finance.vo;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
    
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdTme;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastUpdatedTime;
    
    @Version
    private Integer version;
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
    public Date getCreatedTme() {
        return createdTme;
    }
    public void setCreatedTme(Date createdTme) {
        this.createdTme = createdTme;
    }
    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }
    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
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

        return topic.id == this.id;

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("title", title).add("user", user.getUsername())
                .add("status", status)
                .toString();
    }
}
