package com.intersection.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Entity
@Table(name = "is_user_db")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_no")
	@SerializedName("user_no")
	int userNo;
	@Column(name="phone_id")
	@SerializedName("phone_id")
	String phoneId;
	@Column(name="token_id")
	@SerializedName("token_id")
	String tokenId;
	@Column(name="push_yn")
	@SerializedName("push_yn")
	String pushYn;
	@Column(insertable = false)
	Timestamp created;
	@Column(insertable = false)
	@SerializedName("modified")
	Timestamp modified;
}
