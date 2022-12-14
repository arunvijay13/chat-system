package com.sms.project.chatsystem.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.project.chatsystem.constant.APPConstants;
import com.sms.project.chatsystem.constant.ReturnCode;
import com.sms.project.chatsystem.entity.ErrorResponse;
import com.sms.project.chatsystem.exception.InvalidJwtTokenException;
import com.sms.project.chatsystem.validation.JWTValidation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class AuthorizationFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {

            String[] JWTtoken = request.getHeader(APPConstants.JWT_TOKEN).split(" ");
            System.out.println(JWTtoken[1]);
            if (JWTtoken.length != 2 && !(JWTtoken[0].equals("Bearer"))) {
                throw new InvalidJwtTokenException("INVALID TOKEN");
            }

            JWTValidation.validateToken(JWTtoken[1]);

            chain.doFilter(request, response);

        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
                    ReturnCode.FAILURE.toString(), HttpStatus.BAD_REQUEST.toString(), new Date().toString());

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
