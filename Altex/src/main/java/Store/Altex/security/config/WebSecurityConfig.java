package Store.Altex.security.config;
import Store.Altex.services.UserService;
import Store.Altex.models.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static resources from an external directory
        registry.addResourceHandler("/public/**")
                .addResourceLocations("file:///D:/1ProiecteAN3/1AltexPS/public/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v*/registration/**",
                        "/api/v*/login/**",
                        "/api/v*/products/**",
                        "/api/v*/categories/**",
                        "/api/v*/wishlist/**",
                        "/api/v*/order/**",
                        "/api/v*/cart/**",
                        "/api/v*/reviews/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}