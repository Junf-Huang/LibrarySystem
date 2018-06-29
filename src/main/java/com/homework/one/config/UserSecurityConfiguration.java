package com.homework.one.config;

import com.homework.one.service.CustomAuthenticationProvider;
import com.homework.one.responsitory.UserRepository;
import com.homework.one.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity  //开启Spring Security的功能
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    //加密
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //决定认证方式
        //数据库与登录信息比对认证
        auth.authenticationProvider(customAuthenticationProvider);
        //数据库用户名认证
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(getPasswordEncoder());
        //下者优先于上者
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authenticationProvider(customAuthenticationProvider)
                    .exceptionHandling()
                .and()
                .authorizeRequests()
                    .antMatchers("/**", "/public/**","/css/**", "/js/**").permitAll()
                    //.antMatchers("/sign-in","/sign-up","/addUser").permitAll()
                    //Spring Security 3.x to 4.x. Changing hasRole() to hasAuthority()
                    //资源加载控制
                    .antMatchers( "/books", "/search","/borrows", "/users").hasAnyAuthority("USER","ADMIN")
                    //页面访问权限控制
                    .antMatchers("/users").access("hasAnyAuthority('ADMIN')")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .usernameParameter("username").passwordParameter("password")
                    .loginPage("/sign-in")
                    .defaultSuccessUrl("/home")
                    .failureUrl("/sign-in?error")
                    .permitAll()
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/sign-in?logout")
                    .permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/403");
    }

/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()    //定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/css/**", "/js/**", "/sign-in", "/sign-up").permitAll()    //不需要任何认证就可以访问
                .antMatchers("/books","/page").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/sign-in")  //定义当需要用户登录时候，转到的登录页面
                    .defaultSuccessUrl("/index")
                    .failureUrl("/sign-in?error").permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/sign-in?logout").permitAll();
    }
*/

    /*
    .anyRequest().authenticated(),表示其他的请求都必须要有权限认证
    管理员才可以访问admin文件夹下的内容
        .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
     有多个角色来访问
        .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN","ROLE_USER")
     */
/*

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为USER
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
*/


    private PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return true;
            }
        };
    }

}
