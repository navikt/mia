package no.nav.fo.mia

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.servlet.HandlerInterceptor

import java.lang.Exception
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val requestId = "requestId"

class LoggerInterceptor : HandlerInterceptor {
    private val log = LoggerFactory.getLogger(LoggerInterceptor::class.java)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val id = requestId()
        log.info(request.requestURL.toString())

        MDC.put(requestId, id)
        response.addHeader(requestId, id)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        MDC.clear()
    }

    private fun requestId(): String {
        return UUID.randomUUID().toString()
    }
}
