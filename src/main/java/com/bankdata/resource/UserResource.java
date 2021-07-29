package com.bankdata.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankdata.auth.AuthenticationRequest;
import com.bankdata.auth.AuthenticationResponse;
import com.bankdata.service.UserService;
import com.bankdata.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class UserResource {
	
	private final Logger log = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws Exception{
		Authentication authentication = null;
		try {
			authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			log.error("Exception while login:::"+e.getMessage());
			return new ResponseEntity<String>("Bad Credentials",HttpStatus.BAD_REQUEST);
		}
		UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
		if(null!=userDetails) {
			String jwt  = jwtUtil.generateToken(userDetails);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}
		return new ResponseEntity<String>("Bad Credentials",HttpStatus.BAD_REQUEST);
	}

}
