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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.Principal;

@RestController
public class LoginController {

	@RequestMapping("/auth")
    public Principal user(Principal user) {
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
                            "/admin.html",
                            "/alluser.html",
                            "/calculation_parameter.html",
                            "/calculation_type.html",
                            "/company.html",
                            "/contaminant.html",
                            "/control.html",
                            "/index.html",
                            "/location.html",
                            "/login.html",
                            "/parameter_structure_set.html",
                            "/period.html",
                            "/raw.html",
                            "/raw_type.html",
                            "/role.html",
                            "/settings.html",
                            "/source_type.html",
                            "/users.html",
                            "/work.html").permitAll().anyRequest()
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
