package payments.security;

import net.minidev.json.JSONArray;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests -> authorizeRequests
//                        .antMatchers("/anonymous/**").permitAll()
//                        .anyRequest().authenticated())
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
//                                .oidcUserService(this.oidcUserService())
//                        )
//                );
//
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/anonymous/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);

            final Map<String, Object> claims = oidcUser.getClaims();
            final JSONArray groups = (JSONArray) claims.get("groups");

            final Set<GrantedAuthority> mappedAuthorities = groups.stream()
                    .map(role -> new SimpleGrantedAuthority(("ROLE_" + role)))
                    .collect(Collectors.toSet());

            return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }
}