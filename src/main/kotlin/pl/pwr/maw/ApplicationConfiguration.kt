package pl.pwr.maw

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableScheduling
class ApplicationConfiguration {

    @Bean
    fun webClient() = WebClient.create()

    @Bean
    fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        return http
            .csrf().disable()
            .build()
    }

}
