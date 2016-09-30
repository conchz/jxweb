package com.github.lavenderx.jxweb

import com.github.lavenderx.jxweb.config.RootConfig
import com.github.lavenderx.jxweb.config.WebConfig
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.util.IntrospectorCleanupListener
import javax.servlet.ServletContainerInitializer
import javax.servlet.ServletContext
import javax.servlet.ServletException

/**
 * https://gist.github.com/vmarcinko/f514a6f6c26dbdf876b6
 */
class WebAppServletContainerInitializer : ServletContainerInitializer {

    @Throws(ServletException::class)
    override fun onStartup(c: MutableSet<Class<*>>, ctx: ServletContext) {
        // Create the root appContext
        val rootContext = AnnotationConfigWebApplicationContext()
        rootContext.register(RootConfig::class.java)

        // Manage the lifecycle of the root appContext
        ctx.addListener(ContextLoaderListener(rootContext))
        ctx.addListener(IntrospectorCleanupListener())
        ctx.setInitParameter("defaultHtmlEscape", "true")

        // Load config for the Dispatcher servlet
        val dispatcherContext = AnnotationConfigWebApplicationContext()
        dispatcherContext.register(WebConfig::class.java)

        // The main Spring MVC servlet
        val appServlet = ctx.addServlet("dispatcher-servlet", DispatcherServlet(dispatcherContext))
        appServlet.setLoadOnStartup(1)
        appServlet.addMapping("/api/*")
    }
}