package com.prinpedia.backend.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Login failure");
        jsonObject.put("status", -1);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

}
