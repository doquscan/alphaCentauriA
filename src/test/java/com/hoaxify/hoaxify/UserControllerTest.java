package com.hoaxify.hoaxify;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoaxify.hoaxify.shared.GenericResponse;
import com.hoaxify.hoaxify.user.User;
import com.hoaxify.hoaxify.user.UserRepository;

@RunWith(SpringRunner.class) //JUnit 4 Unit Test
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //For Integration Test
@ActiveProfiles("test") //Define which environment this test applies
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)// It will force JUnit Tests to run naming order. To prevent of tests to not fail
//in case of right order test run issues.

//In Test Driven development each test must run in a controlled environment. So it must start in a null state and return
//everything in an original condition after the test is completed so the next test will not be affected by stale data.
public class UserControllerTest {

	private static final String API_1_0_USERS = "/api/1.0/users";
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Autowired
	UserRepository userRepository;
	
	//we do not need @FixMethodOrder after adding this for the order of test running.
	@Before  //This test will run before each method to make sure the DB is empty before each test runs 
	public void cleanup() {
		userRepository.deleteAll();
	}
	
	//Unit Test Method Name Schema : methodName_condition_expectedBehavior
	@Test
	public void postUser_whenUserIsValid_receiveOk() {
		User user = createValidUser();
		
		ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void postUser_whenUserIsValid_userSavedToDatabase() {
		User user = createValidUser();
		testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
		assertThat(userRepository.count()).isEqualTo(1);
		
	}
	
	@Test
	public void postUser_whenUserIsValid_receiveSuccessMessage() {
		User user = createValidUser();
	
		ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);
		
		assertThat(response.getBody().getMessage()).isNotNull();
	}
	
	private User createValidUser() {
		User user = new User();
		user.setUsername("test-user");
		user.setDisplayname("test-display");
		user.setPassword("P4assword");
		return user;
	}
	
}
