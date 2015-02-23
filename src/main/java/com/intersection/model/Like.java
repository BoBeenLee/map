package com.intersection.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "is_trans_like_db")
@Data
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_no")
	int likeNo;
	@Column(name = "user_no")
	int userNo;
	@Column(name = "trans_no")
	int transNo;
	Timestamp created;
}
