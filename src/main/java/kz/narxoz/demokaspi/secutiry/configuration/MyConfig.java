//package kz.narxoz.demokaspi.secutiry.configuration;
//
//import com.mchange.v2.c3p0.ComboPooledDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import javax.sql.DataSource;
//import java.beans.PropertyVetoException;
//
//@Configuration
//@ComponentScan(basePackages = "kz.narxoz")
//@EnableWebMvc
//public class MyConfig {
//
//    @Bean
//    public ViewResolver viewResolver(){
//        InternalResourceViewResolver internalResourceViewResolver =
//                new InternalResourceViewResolver();
//        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
//        internalResourceViewResolver.setSuffix(".jsp");
//        return internalResourceViewResolver;
//    }
//    @Bean
//    public DataSource dataSource(){
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
//        //            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
//        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
//        dataSource.setUser("postgres");
//        dataSource.setPassword("MyS1ste^^");
//        return dataSource;
//    }
//
//}
