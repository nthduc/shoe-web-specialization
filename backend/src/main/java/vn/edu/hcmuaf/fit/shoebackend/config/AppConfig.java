package vn.edu.hcmuaf.fit.shoebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean("bCryptPasswordEncoderConfigure")
    public BCryptPasswordEncoder bCryptPasswordEncoderConfigure(){
        return new BCryptPasswordEncoder();
    }
}
