package com.intersection.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Entity
@Table(name = "is_trans_db")
@Data
public class Translation  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="trans_no")
	@SerializedName("trans_no")
	int transNo;
	String name;
	String type;
	@Transient
	@SerializedName("like_count")
	int likeCount;
	@Transient
	@SerializedName("cur_like_status")
	boolean likeStatus;
	@Transient
	int seq;
	Timestamp created;
	Timestamp modified;
}
