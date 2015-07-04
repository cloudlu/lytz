/**
 * 
 */
package com.lytz.finance.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
@Entity
@Table(name = "show")
@NamedQueries({
    @NamedQuery(
            name = "findShowByTitle",
            query = "select show from Show show where show.title = :name "
    )
})
public class Show extends TimestampHibernateEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7180548859709667484L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() {
        return id;
    }

    @Basic(optional = false)
    @Column(nullable = false, length = 50, unique = true)
    @NotNull
    @Length(min = 1, max = 50)
    @NotBlank
    private String title;
    @Column(length = 10000)
    private String content;

    /**
     * Default constructor - creates a new instance with no values set.
     */
    public Show() {
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Show)) {
            return false;
        }

        final Show show = (Show) o;

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
                .add("title", title).add("content", this.getContent())
                .toString();
    }}
