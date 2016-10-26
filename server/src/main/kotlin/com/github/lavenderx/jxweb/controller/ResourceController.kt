package com.github.lavenderx.jxweb.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ResourceController {

    @Value("classpath:/ui/index.html")
    private val indexHtml: Resource? = null

    @RequestMapping("/")
    fun index(): Any {
        return ResponseEntity.ok().body<Resource>(indexHtml)
    }
}