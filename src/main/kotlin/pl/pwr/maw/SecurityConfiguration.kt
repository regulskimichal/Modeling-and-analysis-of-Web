package pl.pwr.maw

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .mvcMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html/**"
            )
    }

}
