package com.github.lavenderx.jxweb.server

import com.github.lavenderx.jxweb.utils.loggerFor
import io.undertow.Undertow
import io.undertow.server.handlers.resource.ClassPathResourceManager
import io.undertow.servlet.Servlets
import io.undertow.servlet.api.DeploymentManager
import io.undertow.servlet.api.ServletContainerInitializerInfo
import io.undertow.servlet.handlers.DefaultServlet
import io.undertow.servlet.util.ImmediateInstanceFactory
import org.springframework.beans.factory.DisposableBean

import java.util.HashSet

class UndertowServer(val port: Int) : DisposableBean {

    private val logger = loggerFor<UndertowServer>()
    private val undertowServer: Undertow
    private val deploymentManager: DeploymentManager

    init {
        try {
            val instanceFactory = ImmediateInstanceFactory(WebServletContainerInitializer())
            val sciInfo = ServletContainerInitializerInfo(
                    WebServletContainerInitializer::class.java, instanceFactory, HashSet<Class<*>>())

            val defaultServlet = Servlets.servlet("default", DefaultServlet::class.java)
            val deploymentInfo = Servlets
                    .deployment()
                    .addServletContainerInitalizer(sciInfo)
                    .setClassLoader(this.javaClass.classLoader)
                    .setContextPath("/")
                    .setDefaultEncoding("UTF-8")
                    .setDeploymentName("jxweb")
                    .setResourceManager(ClassPathResourceManager(this.javaClass.classLoader))
                    .addServlet(defaultServlet)
            deploymentManager = Servlets.defaultContainer().addDeployment(deploymentInfo)
            deploymentManager.deploy()

            val httpHandler = deploymentManager.start()

            undertowServer = Undertow
                    .builder()
                    .addHttpListener(port, "0.0.0.0")
                    .setHandler(httpHandler)
                    .build()

        } catch (e: Exception) {
            throw e
        }
    }

    fun start(): UndertowServer {
        undertowServer.start()
        logger.info("Undertow server started on port {}", port)
        return this
    }

    @Throws(Exception::class)
    override fun destroy() {
        logger.info("Stopping undertow server")
        undertowServer.stop()
        deploymentManager.stop()
        deploymentManager.undeploy()
        logger.info("Undertow server stopped on port {}", port)
    }
}