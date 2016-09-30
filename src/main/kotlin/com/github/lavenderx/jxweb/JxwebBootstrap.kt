package com.github.lavenderx.jxweb

import com.github.lavenderx.jxweb.utils.loggerFor

class JxwebBootstrap {
    companion object {
        private val SERVER_PORT = "jxweb.server.port"
        private val logger = loggerFor<JxwebBootstrap>()

        @JvmStatic fun main(args: Array<String>) {
            val port = if (System.getProperty(SERVER_PORT) == null) 8080 else System.getProperty(SERVER_PORT).toInt()
            val webServer = UndertowServer(port).start()

            Runtime.getRuntime().addShutdownHook(Thread {
                try {
                    webServer.destroy()
                } catch (e: Exception) {
                    logger.error("An exception occurred when destroying server", e)
                }
            })
        }
    }
}