/**
 * 
 */
package com.lytz.finance.vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.phonetic.PhoneticFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.EnumBridge;
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
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,include="all")
@DynamicUpdate
@DynamicInsert
@Indexed
@Analyzer(impl=SmartChineseAnalyzer.class)
@AnalyzerDef(
        name="enTopicAnalyzer",
        charFilters={
            @CharFilterDef(factory=HTMLStripCharFilterFactory.class)
        },
        tokenizer=@TokenizerDef(factory=StandardTokenizerFactory.class),
        filters={
            @TokenFilterDef(factory=StandardFilterFactory.class),
            @TokenFilterDef(factory=StopFilterFactory.class),
            @TokenFilterDef(factory=PhoneticFilterFactory.class,
                params = {
                    @Parameter(name="encoder", value="DoubleMetaphone")
                }),
            @TokenFilterDef(factory=SnowballPorterFilterFactory.class,
                params = {
                    @Parameter(name="language", value="English")
                })
            }
    )
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
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String title;
    @ManyToOne //default eager, no cascade
    @JoinColumn(name="owner_id",updatable=false)
    @IndexedEmbedded(depth = 1)
    private User owner;
    @Basic(optional = false)
    @Column(nullable = false, length = 1000)
    @NotNull
    @NotBlank
    @Length(min = 4, max = 1000)
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String content;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    @NotBlank
    @Length(min = 1, max = 10)
    private String contactName;
    @Basic(optional = true)
    @OneToMany(cascade={CascadeType.REMOVE},fetch=FetchType.LAZY, mappedBy="topic")
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<Comment>();
    @Basic(optional = true)
    @Column(nullable = true, length = 50)
    @Email
    @Length(min = 5, max = 50)
    private String contactEmail;
    @Basic(optional = true)
    @Column(nullable = true, length = 20)
    private String contactPhoneNumber;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO, bridge=@FieldBridge(impl=EnumBridge.class))
    private TopicStatus status;
    

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
    
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public Set<Comment> getComments() {
        return comments;
    }
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
    public void addComment(Comment comment){
        getComments().add(comment);
    }
    public TopicStatus getStatus() {
        return status;
    }

    public void setStatus(TopicStatus status) {
        this.status = status;
    }
    
    
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
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
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this.getClass())
                .add("id", getId())
                .add("title", title)
                .add("contactPhoneNumber", contactPhoneNumber)
                .add("contactName", contactName)
                .add("contactEmail", contactEmail);
        if(null != getOwner()){
            helper.add("owner", owner.getUsername());
        }
        if(null != getComments()){
            helper.add("comments", comments);
        }
        helper.add("status", status);
        
        return helper.toString();
    }
}
