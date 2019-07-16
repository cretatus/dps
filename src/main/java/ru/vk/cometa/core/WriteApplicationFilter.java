package ru.vk.cometa.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ru.vk.cometa.model.User;
import ru.vk.cometa.repositories.UserRepository;

public class WriteApplicationFilter implements Filter {
	UserRepository userRepository;
	
	public WriteApplicationFilter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getUserPrincipal() == null) {
			throw new ServletException("Access denied!");
		}
		User user = userRepository.findByLogin(req.getUserPrincipal().getName());
		if(user.getCurrentApplication() == null) {
			throw new ServletException("Application is not defined. Access denied!");
		}
		if(!"write".equals(user.getCurrentPermission()) && !"adimn".equals(user.getCurrentPermission()) && !"owner".equals(user.getCurrentPermission())) {
			throw new ServletException("Access denied! It can do admin, owner or writer");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
