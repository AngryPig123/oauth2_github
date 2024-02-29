package org.spring.oauth2.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Value("${oauth2.github.id}")
    private String id;

    @Value("${oauth2.github.secret}")
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  OAuth2 사용 설정
        http
                .oauth2Login(client -> client
                        .clientRegistrationRepository(clientRegistrationRepository())
                );
        //  모든 end-point 인증 설정
        http
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated();
    }


    private ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = clientRegistration();
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    private ClientRegistration clientRegistration() {
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                .clientId(id)
                .clientSecret(secret)
                .build();
    }

}
