package com.learningkt.bankapi.controller.handler

import org.springframework.web.bind.annotation.ControllerAdvice
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ErrorHandler {
    fun illegalArgumentException(request: HttpServletRequest  )
}