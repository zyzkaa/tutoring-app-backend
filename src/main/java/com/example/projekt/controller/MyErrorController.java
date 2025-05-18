//package com.example.projekt.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class MyErrorController implements ErrorController {
//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
//        if (statusCode == null) {
//            statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        }
//        return "redirect:https://http.cat/" + statusCode;
//    }
//}
