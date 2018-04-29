package com.genre.base.scraper.restapi.controllers

import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/*

   This class intercepts all incoming HTTP requests.

*  The purpose of this class will be to strip out x509 certificates
*  and validate them against the servers keystore.
*
*
*/
class SecurityInterceptor implements HandlerInterceptor{




    @Override
    boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //your custom logic here.

//        try{
//
//            X509Certificate[] certs = (X509Certificate[]) HttpServletRequest["javax.servlet.request.X509Certificate"]
//
//
//        } catch(Exception ex){
//            System.println("----------------------------------------------------------")
//            System.println("No x509 certificate found: "+ex)
//        }

        System.println("----------------------------------------------------------------------")
        System.println("----------------- Incoming httpServletRequest Header -----------------")
        System.println("----------------- Incoming httpServletRequest Header -----------------")
        System.println("----------------- Incoming httpServletRequest Header -----------------")
        System.println("----------------------------------------------------------------------")



        Enumeration<String> headerNames = httpServletRequest.getHeaderNames()
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement()
            System.println("Header Name: " + headerName)
            String headerValue = httpServletRequest.getHeader(headerName)
            System.println("Header Value: " + headerValue)
            System.println("----------------------------------------------------------")
        }

        return true

    }

    @Override
    void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {



    }

    @Override
    void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
