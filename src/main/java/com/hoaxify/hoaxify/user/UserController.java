package com.hoaxify.hoaxify.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.hoaxify.shared.GenericResponse;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	//We are telling Spring that : We want the request of Body of the incoming message 
	//And we tell what type of Object type that want. (User)
	//Spring will take the JSON Body received in the incoming request and convert it into our User object.
	// Spring is using the jackson library here for this convertion.
	@PostMapping("/api/1.0/users")
	GenericResponse createUser(@RequestBody User user) {
		userService.save(user); //save DB
		return new GenericResponse("User Saved");
	}
}
