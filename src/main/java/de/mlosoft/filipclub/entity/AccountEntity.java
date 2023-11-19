package de.mlosoft.filipclub.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "account_account_id_seq")
	@Column(name = "account_id")
	private long accountId;
	private String hashedPassword;
	private String email;

	private String role;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Date createdAt;
}
