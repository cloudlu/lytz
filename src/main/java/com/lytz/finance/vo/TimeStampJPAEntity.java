/**
 * 
 */
package com.lytz.finance.vo;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author cloudlu
 *
 */
public class TimeStampJPAEntity {

    @Basic(optional = false)
    @Column(nullable = false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerTime;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;
    
    
    @PrePersist
    protected void onCreate() {
        lastUpdateTime = registerTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateTime = new Date();
    }
}
