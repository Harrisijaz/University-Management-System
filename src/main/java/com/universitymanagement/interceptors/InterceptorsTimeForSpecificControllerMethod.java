package com.universitymanagement.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class InterceptorsTimeForSpecificControllerMethod implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (handler instanceof HandlerMethod) {
            long startTime = System.currentTimeMillis();
            request.setAttribute("startTime", startTime);
            System.out.println("deleteCourseById time started at ..");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (handler instanceof HandlerMethod && request.getAttribute("startTime") != null) {
            long startTime = (Long) request.getAttribute("startTime");
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            HandlerMethod method = (HandlerMethod) handler;
            System.out.println( method.getBeanType().getSimpleName() + "." + method.getMethod().getName()
                    + " executed in " + duration + " ms");
        }
    }

}
