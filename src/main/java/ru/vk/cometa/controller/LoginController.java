package ru.vk.cometa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.User;
import ru.vk.cometa.repositories.UserRepository;
import ru.vk.cometa.service.EmailUtil;
import ru.vk.cometa.service.ValidationService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;
	@Autowired
	private ValidationService validationService;
	@Autowired
	private EmailUtil emailUtil;

	@RequestMapping("/auth")
    public Principal user(Principal user) {
        return user;
    }

	@RequestMapping("/register")
    public User register(@RequestBody Map<String, String> params) throws ManagedException {
		if(!params.containsKey("username") || !params.containsKey("password") || !params.containsKey("email")) {
			throw new ManagedException("User parameters are incorrect");
		}
		validationService.assertNotNull(params.get("username"), "User name");
		validationService.assertNotNull(params.get("password"), "Password");
		validationService.assertNotNull(params.get("email"), "E-mail");
        User user = new User();
        user.setLogin(params.get("username"));
        user.setName(params.get("username"));
        user.setPassword(params.get("password"));
        user.setEmail(params.get("email"));
		validationService.unique(user).addParameter("name", user.getName()).check();
		validationService.unique(user).addParameter("login", user.getLogin()).check();
		validationService.unique(user).addParameter("email", user.getEmail()).check();
		user = userRepository.save(user);
		emailUtil.send("Registration in co-Meta service", "Welcome! Your account = [" + user.getLogin() + "], password = [" + user.getPassword() + "]", user.getEmail());
		return user;
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        DataSource dataSource;

        @Autowired
        public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        	
			String sql1 = "";
			sql1 += "select login as username, password,'true' as enabled ";
			sql1 += "from user ";
			sql1 += "where login=?";

			String sql2 = "";
			sql2 += "select login as username, 'role' as authority ";
			sql2 += "from user ";
			sql2 += "where login=?";

			auth.jdbcAuthentication().dataSource(dataSource)
            	.usersByUsernameQuery(sql1)
            	.authoritiesByUsernameQuery(sql2);

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin().usernameParameter("login").passwordParameter("password").and()
                    .logout().and().authorizeRequests()
                    .antMatchers(
                            "/",
                            "/register",
                            "/index.html",
                            "/login.html").permitAll().anyRequest()
                    .authenticated().and().csrf()
                    .disable();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/js/**");
            web.ignoring().antMatchers("/webjars/**");
            web.ignoring().antMatchers("/fonts/**");
        }



        @SuppressWarnings("unused")
		private Filter csrfHeaderFilter() {
            return new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest request,
                                                HttpServletResponse response, FilterChain filterChain)
                        throws ServletException, IOException {
                    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                            .getName());
                    if (csrf != null) {
                        Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                        String token = csrf.getToken();
                        if (cookie == null || token != null
                                && !token.equals(cookie.getValue())) {
                            cookie = new Cookie("XSRF-TOKEN", token);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                    filterChain.doFilter(request, response);
                }
            };
        }

        @SuppressWarnings("unused")
		private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setHeaderName("X-XSRF-TOKEN");
            return repository;
        }
    }

}
