package com.diegofdez.springbootinaction.readingList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Class that modifies parameters before sending them to controller
 * Here we will modify the result of login operations
 */
@Component
public class ReaderHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static Logger LOG = LoggerFactory.getLogger(ReaderHandlerMethodArgumentResolver.class);
	
	/**
	 * Evaluate if a parameter is handled by this class
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Reader.class.isAssignableFrom(parameter.getParameterType());
	}

	
	/**
	 * Take a request parameter and modify it to be passed to the controller method
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {

		// What is a Principal? https://stackoverflow.com/questions/4989063/what-is-the-meaning-of-subject-vs-user-vs-principal-in-a-security-context
		Authentication auth = (Authentication) webRequest.getUserPrincipal();
	  
		LOG.info(auth.getPrincipal().toString());
	  
		Object result = null;
		if (auth != null && auth.getPrincipal() instanceof Reader) {
			result = auth.getPrincipal();
		}
		else if (auth != null && auth.getPrincipal() instanceof User) {
			Reader tmpReader = new Reader();
			User tmpUser = (User) auth.getPrincipal();
			tmpReader.setPassword(tmpUser.getPassword());
			tmpReader.setUsername(tmpUser.getUsername());
			
			result = tmpReader;
		} 
		
		return result;
  }

}
