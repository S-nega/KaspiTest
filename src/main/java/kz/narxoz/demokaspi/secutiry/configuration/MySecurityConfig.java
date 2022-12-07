//package kz.narxoz.demokaspi.secutiry.configuration;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
//
//import javax.sql.DataSource;
//
//@EnableWebSecurity
//public class MySecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.jdbcAuthentication().dataSource(dataSource);
//    }
//    @Override
//    public void configure(WebSecurity web) throws Exception{
//        web.ignoring()
//                .antMatchers(
////                        "/static/**", "/logIn", "/controllers/**");
//                        "/register/**")
//                .antMatchers("/myLog/**")
//                .antMatchers("/registration/**");
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/templates/**").hasAnyRole("user","admin")
////                .antMatchers("/resources/**").permitAll().anyRequest().permitAll()
////                .antMatchers("/hr_info").hasRole("HR")
////                .antMatchers("/account/**").hasRole("user")
//                .and()
//                .formLogin().permitAll()
//                .loginPage("/logIn")
//                ;
////                .loginProcessingUrl("/perform-login");
//
//    }
//}
