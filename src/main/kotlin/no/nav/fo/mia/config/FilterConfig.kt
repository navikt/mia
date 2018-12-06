package no.nav.fo.mia.config

import no.finn.unleash.Unleash
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.*

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

val tjensteUrl = """\Ahttps://tjenester(-q[0-9])?.nav.no/.*""".toRegex()
val log = LoggerFactory.getLogger(RedirectFilter::class.java)

@Component
open class RedirectFilter(val unleash: Unleash) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if(tjensteUrl.matches(request.requestURL) && unleash.isEnabled("mia-redirect")) {
            log.info("redirecter")
            response.sendRedirect(response.encodeRedirectURL("https://mia.nav.no/"))
        } else {
            chain.doFilter(request, response)
        }
    }
}

@Component
open class SlashMiaFikser : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if(request.requestURI.startsWith("/mia")) {
            val newUri = request.requestURI.removePrefix("/mia")
            request.getRequestDispatcher(newUri).forward(request, response)
        } else {
            chain.doFilter(request, response)
        }
    }

}
