/**
 * 
 */
package com.lytz.finance.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.phonetic.PhoneticFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.EnumBridge;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
@Entity
@Table(name = "equity")
@NamedQueries({
    @NamedQuery(
            name = "findEquityByTitle",
            query = "select equity from Equity equity where equity.title = :name "
    )
})
@Indexed
@Analyzer(impl=SmartChineseAnalyzer.class)
@AnalyzerDef(
        name="enShowAnalyzer",
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
public class Equity extends TimestampHibernateEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7180548859709667484L;

    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    @NotNull
    @Length(min = 4, max = 50)
    @NotBlank
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String title;
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 10000)
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String content;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO, bridge=@FieldBridge(impl=EnumBridge.class))
    private Status status;
    /**
     * Default constructor - creates a new instance with no values set.
     */
    public Equity() {
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
        if (!(o instanceof Equity)) {
            return false;
        }

        final Equity show = (Equity) o;

        return show.getId() == this.getId();

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
                .add("id", getId())
                .add("title", title).add("content", this.getContent())
                .add("status", status)
                .toString();
    }}
