/**
 * 
 */
package com.lytz.finance.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author cloudlu
 *
 */
@MappedSuperclass
public class TimestampJPAEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4729138673895122673L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedTime;
    
    @Version
    private Integer version;
    
    @PrePersist
    protected void onCreate() {
        lastUpdatedTime = createdTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedTime = new Date();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public Date getCreatedTme() {
        return createdTime;
    }
    public void setCreatedTme(Date createdTme) {
        this.createdTime = createdTme;
    }
    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }
    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
