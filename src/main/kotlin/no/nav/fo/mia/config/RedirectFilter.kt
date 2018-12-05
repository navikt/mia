package no.nav.fo.mia.config

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.*

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

val tjensteUrl = """\Ahttps://tjenester(-q[0-9])?.nav.no/.*""".toRegex()
val log = LoggerFactory.getLogger(RedirectFilter::class.java)

@Component
open class RedirectFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        log.info("request.requestURI: {}", request.requestURI)
        log.info("request.requestURL: {}", request.requestURL.toString())
        log.info("request.remoteAddr {}", request.remoteAddr)

        if( tjensteUrl.matches(request.requestURL)) {
            log.info("redirecter")
            response.sendRedirect(response.encodeRedirectURL("https://mia.nav.no/"))
        } else {
            chain.doFilter(request, response)
        }
    }
}


