package com.capgemini.greet.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.greet.dto.GreetMessage;
import com.capgemini.greet.dto.GreetRequest;
import com.capgemini.greet.exception.GreetCheckedException;
import com.capgemini.greet.service.GreetService;
import com.capgemini.user.exception.UserException;
import com.capgemini.user.exception.core.EnableExceptionHandler;
import com.capgemini.user.exception.core.ResponseEntityUserExceptionHandler;

@Controller @RequestMapping("/greet") @EnableExceptionHandler
public class GreetWebServiceController {
	
	@Autowired
	private GreetService greetService;
	
	@Autowired
	private ResponseEntityUserExceptionHandler responseEntityUserExceptionHandler;

	@ResponseBody @RequestMapping(value="/greet-simple", method = RequestMethod.GET, produces={"application/json","application/xml"})
	public ResponseEntity<GreetMessage> greet(){
		GreetMessage greetMessage = greetService.greet();
		return new ResponseEntity<GreetMessage>(greetMessage,HttpStatus.OK);
	}
	
	@ResponseBody @RequestMapping(value="/greet-with-message/{greetMessage}", method = RequestMethod.GET, produces={"application/json","application/xml"})
	public ResponseEntity<GreetMessage> greet(@PathVariable("greetMessage") String greetMessage){
		GreetMessage greetMessageReturn = null;
		try{
			greetMessageReturn = greetService.greet(greetMessage);
		}catch(GreetCheckedException ex){
			throw new RuntimeException(ex);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return new ResponseEntity<GreetMessage>(greetMessageReturn,HttpStatus.OK);
	}
	
	@ResponseBody @RequestMapping(value="/greet-with-messagebody", method = RequestMethod.POST, 
			consumes={"application/json", "application/xml"}, produces={"application/json","application/xml"})
	public ResponseEntity<GreetMessage> greet(@RequestBody GreetRequest greetRequest){
		GreetMessage greetMessage = greetService.greet(greetRequest);
		return new ResponseEntity<GreetMessage>(greetMessage,HttpStatus.OK);
	}
	

	@ExceptionHandler(value={Exception.class})
	public void handleUserException(final Exception exception, HttpServletRequest request){
		System.out.println("Exception :"+exception +" HttpServletRequest :"+request);
	}
}
