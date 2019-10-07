package social.alone.server.config


import social.alone.server.auth.oauth2.CustomOAuth2UserService
import social.alone.server.auth.oauth2.HttpCookieOAuth2AuthorizationRequestRepository
import social.alone.server.auth.oauth2.OAuth2AuthenticationFailureHandler
import social.alone.server.auth.oauth2.OAuth2AuthenticationSuccessHandler
import social.alone.server.auth.oauth2.user.RestAuthenticationEntryPoint
import social.alone.server.auth.oauth2.user.TokenAuthenticationFilter
import social.alone.server.user.service.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val userService: CustomUserDetailService? = null

    @Autowired
    private val customOAuth2UserService: CustomOAuth2UserService? = null

    @Autowired
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler? = null

    @Autowired
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler? = null

    @Bean
    fun tokenAuthenticationFilter(): TokenAuthenticationFilter {
        return TokenAuthenticationFilter()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /*
    By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to update
    the authorization request. But, since our service is stateless, we can't update it in
    the session. We'll update the request in a Base64 encoded cookie instead.
  */
    fun cookieAuthorizationRequestRepository(): HttpCookieOAuth2AuthorizationRequestRepository {
        return HttpCookieOAuth2AuthorizationRequestRepository()
    }

    @Throws(Exception::class)
    public override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder())
    }


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    // TODO 정리 필요, ResourceServerConfig 와 책임 분리 필요
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/actuator/**"
                )
                .permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/**")
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/events/{id}/links")
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/push/tokens")
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/images")
                .permitAll()
                .antMatchers("/api/auth/**", "/oauth2/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService!!)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)

        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        // docs
        //    web.ignoring().mvcMatchers("/docs/index.html");
    }
}
