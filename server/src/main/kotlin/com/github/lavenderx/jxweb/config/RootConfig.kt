package com.github.lavenderx.jxweb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.annotation.Order

@Order(0)
@Configuration
@ComponentScan("com.github.lavenderx.jxweb.service")
@PropertySource("classpath:common-config.properties")
open class RootConfig {
    companion object {
        @Bean fun placeholderConfigurer(): PropertySourcesPlaceholderConfigurer {
            return PropertySourcesPlaceholderConfigurer()
        }
    }
}