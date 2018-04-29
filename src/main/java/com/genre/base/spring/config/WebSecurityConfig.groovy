package com.genre.base.spring.config
//package springbootapi.spring.config
//
//import com.sun.net.httpserver.Filter
//import org.apache.catalina.filters.*
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.boot.context.embedded.FilterRegistrationBean
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.PropertySource
//import org.springframework.core.env.Environment;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.core.authority.AuthorityUtils
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//
//
//@Configuration
//@PropertySource("classpath:users.properties")
////@EnableWebSecurity
//class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private Environment env
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////                .authorizeRequests().anyRequest().authenticated()
////                .and()
////                .x509()
////                .subjectPrincipalRegex($/CN=(.*?)(?:,|$)/$)
////                .userDetailsService(userDetailsService())
//
//
//
//
//
//    }
//
////    @Autowired
////    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////        //... whose username is defined in the application's properties.
//////                .inMemoryAuthentication()
//////                .withUser(ALLOWED_USER).password("").roles("SSL_USER");
//////
////
//////                .inMemoryAuthentication()
//////                .withUser(env.getProperty("sample.username")).password(env.getProperty("sample.password")).roles("USER");
////
////    }
//
//
////    @Bean
////    UserDetailsService userDetailsService() {
////        return new UserDetailsService() {
////            @Override
////            UserDetails loadUserByUsername(String username) {
////                if (username.equals("cid")) {
////                    return new User(username, "",
////                            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
////                }
////            }
////        }
////    }
//
//
//}