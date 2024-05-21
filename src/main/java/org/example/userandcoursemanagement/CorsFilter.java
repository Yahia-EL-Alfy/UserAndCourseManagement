package org.example.userandcoursemanagement;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Allow cross-origin requests from any origin
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        // Allow the following methods
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // Allow the following headers
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        // Allow credentials
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(httpRequest, httpResponse);
    }

    // Other methods from the Filter interface
}