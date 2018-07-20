package no.nav.fo.mia.controllers

import no.nav.fo.mia.Feilmelding
import no.nav.fo.mia.requestId
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val path = "/error"

@Controller
class CustomErrorController : ErrorController {

    override fun getErrorPath() = path
    private val LOGGER = LoggerFactory.getLogger(CustomErrorController::class.java)

    @RequestMapping(path, produces = [MediaType.TEXT_HTML_VALUE])
    fun htmlError(request: HttpServletRequest, model: Model, response: HttpServletResponse): ModelAndView {
        log(request, response)
        return ModelAndView("redirect:/")
    }

    @GetMapping(path)
    @ResponseBody
    fun error(request: HttpServletRequest, response: HttpServletResponse): Feilmelding {
        log(request, response)
        return feilmelding(response.status)
    }

    private fun log(request: HttpServletRequest, response: HttpServletResponse) {
        LOGGER.info("request feilemet mot: ${request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)} med feilkode: ${response.status}")
    }

    private fun feilmelding(feilkode: Int) = when (feilkode) {
        404 -> Feilmelding( id= "feilmelding.404", tittel = "Siden ikke funnet", message = "Siden ikke funnet", callId = getRequestId())
        else -> Feilmelding( id= "feilmelding.$feilkode", tittel = "Noe uventet feilet", message = "Noe uventet feilet", callId = getRequestId())
    }

    private fun getRequestId() =
        MDC.get(requestId)
}
