package no.nav.fo.mia.controllers

import no.nav.fo.mia.Feilmelding
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val path = "/error"

@Controller
class CustomErrorController : ErrorController {

    override fun getErrorPath() = path

    @GetMapping(path, produces = [MediaType.TEXT_HTML_VALUE])
    fun htmlError(model: Model, response: HttpServletResponse) = ModelAndView("redirect:/")

    @GetMapping(path)
    @ResponseBody
    fun error(request: HttpServletRequest, response: HttpServletResponse): Feilmelding {
        return feilmelding(response.status)
    }

    private fun feilmelding(feilkode: Int) = when (feilkode) {
            404 -> Feilmelding( id= "feilmelding.404", tittel = "Siden ikke funnet", message = "Siden ikke funnet", callId = null)
            else -> Feilmelding( id= "feilmelding.$feilkode", tittel = "Noe uventet feilet", message = "Noe uventet feilet", callId = null)
    }
}
