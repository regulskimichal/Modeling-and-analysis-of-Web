package pl.pwr.maw

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableScheduling
class ApplicationContext : SchedulingConfigurer {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info().title("Page Measurer API").version("1"))
    }

    @Bean
    fun webClient(webClientBuilder: WebClient.Builder) = webClientBuilder.build()

    @Bean
    fun corsConfigurer() = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**").allowedOrigins("http://localhost:4200")
        }
    }

    @Bean
    fun taskScheduler() = ThreadPoolTaskScheduler().apply {
        poolSize = 100
        setThreadNamePrefix("Scheduler-")
        setWaitForTasksToCompleteOnShutdown(true)
        isRemoveOnCancelPolicy = true
    }

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler())
    }

}
