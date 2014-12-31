package com.capgemini.user.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.user.exception.core.EnableExceptionHandler;
import com.capgemini.user.logging.core.AppContextSingleton;
import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.LogEventTypes;
import com.capgemini.user.logging.event.SimpleLogEvent;
import com.capgemini.user.service.UserService;
import com.capgemini.user.service.dto.User;
import com.capgemini.user.service.util.UserMapper;

@Controller 
@RequestMapping("/user") 
@EnableExceptionHandler
public class UserWebServiceController {

	private static final LogPublisher logPublisher = AppContextSingleton.getInstance().getLogPublisher();

	@Autowired private UserService userService;

	@ResponseBody @RequestMapping(value="/create/{userId}", method = RequestMethod.POST, 
			consumes={"application/json", "application/xml"}, produces={"application/json","application/xml"})
	public ResponseEntity<User> createUser(@PathVariable("userId") String userId, @RequestBody User user, Model model){
		
		logPublisher.publishLog(new SimpleLogEvent(String.format("HTTP POST Inside createUser with userId=%s username=%s firstname=%s lastname=%s dob=%s",
					userId, user.getUsername(), user.getFirstname(), user.getLastname(), user.getDob()), LogEventTypes.DEBUG.toString()));
		
		User createdUser =  UserMapper.toUser(userService.createUser(UserMapper.fromUser(user)));
		return new ResponseEntity<User>(createdUser,HttpStatus.CREATED);
	}

	@ResponseBody @RequestMapping(value="/find/{userId}", method = RequestMethod.GET, produces={"application/json","application/xml"})
	public ResponseEntity<User> findUser(@PathVariable("userId") String userId, Model model){
		
		logPublisher.publishLog(new SimpleLogEvent(String.format("HTTP GET Inside findUser with userId %s",userId),LogEventTypes.DEBUG.toString()));
		
		User foundUser = UserMapper.toUser(userService.findUserWithId(Long.valueOf(userId)));
		if(foundUser!=null){
			logPublisher.publishLog(new SimpleLogEvent(String.format("Found User with userId=%s username=%s firstname=%s lastname=%s dob=%s",
						foundUser.getId(), foundUser.getUsername(), foundUser.getFirstname(), foundUser.getLastname(), foundUser.getDob()),LogEventTypes.DEBUG.toString()));
			return new ResponseEntity<User>(foundUser,HttpStatus.OK);
		}else{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody @RequestMapping(value="/update/{userId}", method = RequestMethod.PUT, 
			consumes={"application/json", "application/xml"}, produces={"application/json","application/xml"})
	public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @RequestBody User user, Model model){
		
		logPublisher.publishLog(new SimpleLogEvent(String.format("HTTP PUT Inside updateUser with userId %s",userId),LogEventTypes.DEBUG.toString()));
		
		User foundUser = UserMapper.toUser(userService.findUserWithId(Long.valueOf(userId)));
		if(foundUser==null){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		User updatedUser =  UserMapper.toUser(userService.updateUser(UserMapper.fromUser(user)));
		
		logPublisher.publishLog(new SimpleLogEvent(String.format("Updated User with userId=%s username=%s firstname=%s lastname=%s dob=%s",
					updatedUser.getId(), updatedUser.getUsername(), updatedUser.getFirstname(), updatedUser.getLastname(), updatedUser.getDob()),LogEventTypes.DEBUG.toString()));
		
		return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
	}

	@ResponseBody @RequestMapping(value="/delete/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("userId") String userId, Model model){
		
		logPublisher.publishLog(new SimpleLogEvent(String.format("HTTP DELETE Inside deleteUser with userId %s",userId),LogEventTypes.DEBUG.toString()));
		
		User foundUser = UserMapper.toUser(userService.findUserWithId(Long.valueOf(userId)));
		if(foundUser==null){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		userService.deleteUser(Long.valueOf(userId));
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	/*@ExceptionHandler(value={UserException.class, Exception.class})
	public void handleUserException(final Exception exception, HttpServletRequest request){
		System.out.println("Exception :"+exception +" HttpServletRequest :"+request);
	}*/
}
