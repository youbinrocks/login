/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : Account.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dell
 *
 */

@Entity
@Getter
@Setter
@Table(name="account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)	
	private String email;
	
	@Column(nullable=false)	
	private String username;
	
	@Column(nullable=false)	
	private String password;
	
	@OneToMany(cascade = CascadeType.MERGE)	
	private Set<Role> roles;
	
	@CreationTimestamp
	private Date regDate;
	
}
