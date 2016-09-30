package com.github.lavenderx.jxweb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Order(1)
@Configuration
@ComponentScan("com.github.lavenderx.jxweb.controller")
@EnableWebMvc
open class WebConfig : WebMvcConfigurerAdapter() {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    open fun characterEncodingFilter(): CharacterEncodingFilter = CharacterEncodingFilter("UTF-8", true)

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/ui/")
    }
}