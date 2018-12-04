package no.nav.fo.mia.config

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.*

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

val tjensteUrl = """\Ahttps://tjenester(-q[0-9])?.nav.no/.*""".toRegex()

@Component
open class RedirectFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if( tjensteUrl.matches(request.requestURL)) {
            response.sendRedirect("https://mia.nav.no/")
        } else {
            chain.doFilter(request, response)
        }
    }
}


