package com.nt.cntrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nt.dto.APIResponse;
import com.nt.dto.UserRequestDTO;
import com.nt.dto.UserResponseDTO;
import com.nt.entity.User;
import com.nt.event.RegistrationCompleteEvent;
import com.nt.mapper.UserMapper;
import com.nt.service.UserService;

@CrossOrigin("*")
@RequestMapping("/user")
@RestController
public class UserController {

	@Value("${frontend.url}")
	private String frontendURL;

	@Autowired
	private UserService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private UserMapper mapper;

	@PostMapping
	public ResponseEntity<APIResponse> addUser(@RequestBody UserRequestDTO dto, HttpServletRequest request) {

		if (service.checkIfSameuser(dto)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new APIResponse(null, "Already Registered"));

		} else {

			User user = service.addUser(dto);
			publisher.publishEvent(new RegistrationCompleteEvent(user, applicationURL(request)));

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new APIResponse(mapper.toResponseDTO(user), "Registration Success"));
		}

	}

	private String applicationURL(HttpServletRequest request) {

		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@GetMapping("/verifyRegistration")
	public Object verifyEmailVerification(@RequestParam String token) {

		String result = service.verifyRegiastrationwithtoken(token);

		if (result.equalsIgnoreCase("valid")) {
			return new RedirectView(frontendURL);
		} else if (result.equalsIgnoreCase("token_expired")) {
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("TOken Expired");
		} else {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("Invalid TOken");

		}

	}

	@GetMapping("/resendVerificationlink/{email}")
	public ResponseEntity<String> resendVerificationMail(@PathVariable String email, HttpServletRequest request) {

		String result = service.resendVerificationMail(email, applicationURL(request));
		if (result.equalsIgnoreCase("link_sent")) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

		}

	}

	@PostMapping("/login")
	public ResponseEntity<APIResponse> loginUser(@RequestBody UserRequestDTO dto) {

		String result = service.validateUser(dto);

		if (!result.equalsIgnoreCase("Authentication_Error") && !result.equalsIgnoreCase("invalid_Credentials")){
			if(!result.equalsIgnoreCase("Please_Verify_Emial_First")) {
				UserResponseDTO user = service.getUserByUsername(dto.getUsername()); 
				user.setToken(result);
				return ResponseEntity.ok(new APIResponse(user, "User Login Success"));
			} else {
				return ResponseEntity.ok(new APIResponse(null, "Please Verify your Email first..."));
	
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse(null, "User Login Failed"));

		}

	}

}
