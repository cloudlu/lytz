package com.lytz.finance.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.MoreObjects;


@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(
            name = "findUserByName",
            query = "select r from User r where r.username = :username "
    )
})
public class User extends TimestampHibernateEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2989448605305814612L;
	@Basic(optional = true)
	@Column(nullable = true, length = 30)
	//@NotNull
	//@NotBlank
    @Length(min = 4, max = 30)
	private String realname;
	@Basic(optional = false)
	@Column(nullable = false, length = 30, unique = true, updatable=false)
	@NotNull
	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9]{4,30}", message = "{errors.invalid}")
    @Length(min = 4, max = 30)
	private String username;
	@Basic(optional = false)
	@Column(nullable = false, length = 150)
	@NotNull
	@NotBlank
    @Length(min = 6, max = 150)
	private String password;
	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
    @NotBlank
	private String passwordHint;
	@Basic(optional = true)
	@Column(nullable = true, length = 50, unique = true)
	@Email
	@Length(min = 1, max = 30)
	private String email;
	@Basic(optional = true)
	@Column(nullable = true, length = 20)
	private String phoneNumber;
    
	@Basic(optional = false)
	@Column(nullable = false, name="accountEnabled")
	private boolean enabled;
	@Basic(optional = false)
	@Column(nullable = false)
	private boolean accountExpired;
	@Basic(optional = false)
	@Column(nullable = false)
	private boolean accountLocked;
	@Basic(optional = false)
	@Column(nullable = false)
	private boolean credentialsExpired;
	@Basic(optional = true)
	@Column(nullable = true)
	private Date expiredTime;
	//@Basic(optional = false)
	//@Column(nullable = false)
	//private String credentialsSalt;
	
    @Transient
	private String confirmPassword;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST/* , CascadeType.MERGE */ })
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id",  referencedColumnName="id") },
            inverseJoinColumns = @JoinColumn(name = "role_id",  referencedColumnName="id")
    )
	private Set<Role> roles = new HashSet<Role>();

	//when user is delete(should not call delete), remain topics
	@OneToMany(cascade=CascadeType.MERGE,fetch=FetchType.LAZY, mappedBy="owner")
	private Set<Topic> topics = new HashSet<Topic>();
	

	public User() {

	}
	
	public User(String realname, String username,
			String password, String passwordHint, String email,
			String phoneNumber, boolean enabled,
			boolean accountExpired, boolean accountLocked,
			boolean credentialsExpired, Date expiredTime) {
		super();
		this.realname = realname;
		this.username = username;
		this.password = password;
		this.passwordHint = passwordHint;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.enabled = enabled;
		this.accountExpired = accountExpired;
		this.accountLocked = accountLocked;
		this.credentialsExpired = credentialsExpired;
		this.expiredTime = expiredTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		
		/*if(roles.isEmpty()){
			if(null == branch)
				roles.add(new Role("ROLE_ANONYMOUS"));
			if("ALL".equalsIgnoreCase(branch)){
				roles.add(new Role("ROLE_ADMIN"));
			} else
				roles.add(new Role("ROLE_USER"));			
		}*/
		
		return roles;
	}

	/**
	 * Adds a role for the user
	 * 
	 * @param role
	 *            the fully instantiated role
	 */
	public void addRole(Role role) {
		getRoles().add(role);
	}
	
	/**
	 * Adds a role for the user
	 * 
	 * @param role
	 *            the fully instantiated role
	 */
	public boolean removeRole(Role role) {
		return getRoles().remove(role);
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}	
    
    /**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}

		final User user = (User) o;

		return !(username != null ? !username.equals(user.getUsername()) : user
				.getUsername() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (username != null ? username.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
	    MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this.getClass()).add("username",this.username).add("enabled",this.enabled)
				.add("accountExpired",this.accountExpired)
				.add("credentialsExpired",this.credentialsExpired)
				.add("accountLocked",this.accountLocked);

		if (null != roles) {
			for (Role role : roles) {
				helper.add("role", role.getName());
			}
		} else {
		    helper.addValue("No Granted Roles");
		}
		return helper.toString();
	}

	
    public Set<Topic> getTopics() {
        return topics;
    }

    //actually not required
    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
    
    public void addTopic(Topic topic){
        getTopics().add(topic);
    }
}
