package com.nt.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nt.dto.UserRequestDTO;
import com.nt.dto.UserResponseDTO;
import com.nt.entity.EmailVerificationToken;
import com.nt.entity.User;
import com.nt.event.RegistrationCompleteEvent;
import com.nt.mapper.UserMapper;
import com.nt.repository.EmailVerificationTokenRepository;
import com.nt.repository.UserRepository;
import com.nt.service.JWTService;
import com.nt.service.UserService;

import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private EmailVerificationTokenRepository emailVerificationTokenRepository;

	@Autowired
	private UserMapper mapper;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private JavaMailSender javamailSender;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JWTService jwtService;

	@Override
	@Transactional
	public User addUser(UserRequestDTO dto) {
		User user = mapper.toEntity(dto);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		try {
			// Use saveOrUpdateUser to manage the entity
			User addedUser = repository.save(user);
			if (addedUser != null) {
				return addedUser;
			}
			return null;
		} catch (Exception e) {

			e.printStackTrace();

			return null;
		}
	}

	@Override
	public boolean checkIfSameuser(UserRequestDTO dto) {
		try {
			return repository.existsByEmailAndUsername(dto.getEmail(), dto.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void sendVerificationMail(String email, String uRL) throws MessagingException {

		MimeMessage message = javamailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(email);
		helper.setSubject("Please confirm your email address");
		helper.setText("<html><body><p>Click the link below to confirm your registration:</p>" + "<p><a href='" + uRL
				+ "'>Confirm my account</a></p></body></html>", true);
		javamailSender.send(message);

	}

	@Override
	@Transactional(readOnly = true)
	public String validateUser(UserRequestDTO dto) {
		User user = repository.findByUsername(dto.getUsername());

//		        // Authenticate the user with the provided credentials
//		        Authentication authentication = authManager.authenticate(
//		            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//		        );
//		        if (authentication.isAuthenticated()) {
		// Generate the JWT token if authentication is successful
		if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			return jwtService.generateToken(user.getUsername());
		} else {
			return "Failed: Authentication unsuccessful";
		}

	}

	@Override
	@Transactional
	public void saveEmailverificationToken(User user, String token) {
		user = saveOrUpdateUser(user); // Ensure user is managed before creating token
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken(user, token);
		emailVerificationTokenRepository.save(emailVerificationToken);
	}

	@Transactional
	public User saveOrUpdateUser(User user) {
		return entityManager.merge(user); // Use merge to attach or update the entity
	}

	@Override
	public String verifyRegiastrationwithtoken(String token) {

		EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByToken(token);

		if (emailVerificationToken == null) {
			return "Invalid";
		}

		User user = emailVerificationToken.getUser();
		Calendar cal = Calendar.getInstance();

		if (emailVerificationToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 0) {
			emailVerificationTokenRepository.delete(emailVerificationToken);
			return "token_expired";
		}
		user.setVerified(true);
		repository.save(user);

		return "valid";

	}

	@Override
	public String resendVerificationMail(String email, String applicationURL) {

		User user = repository.findByEmail(email);

		if (user != null ) {
			if(user.isVerified()) {
				publisher.publishEvent(new RegistrationCompleteEvent(user, applicationURL));
				return "link_sent";
			} else {
				return "Please_Verify_Emial_First";
			}
			
		}
		return "invalid_mail";
	}

	@Override
	public UserResponseDTO getUserByUsername(String username) {

		User user = repository.findByUsername(username);
		UserResponseDTO dto = mapper.toResponseDTO(user);
		
		
		
		return dto;
	}

}
