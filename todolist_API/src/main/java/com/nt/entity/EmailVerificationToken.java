package com.nt.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_verification_tokens")
@Data
@NoArgsConstructor

public class EmailVerificationToken {

	
	private static final int EXPIRATION_TIME = 10;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String token;
	private Date expirationTime;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "USER_VERIFY_TOKEN"))
	private User user;
	
	
	 public EmailVerificationToken(User user, String token) {
	        super();
	        this.token = token;
	        this.user = user;
	        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
	    }


	    public EmailVerificationToken(String token) {
	        super();
	        this.token = token;
	        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
	    }

	 // Add 10 minutes in current time and return the time in expiry time
	    private Date calculateExpirationDate(int expirationTime) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(new Date().getTime());
	        calendar.add(Calendar.MINUTE, expirationTime);
	        return new Date(calendar.getTime().getTime());
	    }


}
