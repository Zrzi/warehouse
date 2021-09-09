package com.database.warehouse.configuration;

import com.google.gson.Gson;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e)
            throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter printWriter = httpServletResponse.getWriter();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 400);
        map.put("message", "无权限");
        printWriter.write(new Gson().toJson(map));
        printWriter.flush();
        printWriter.close();
    }
}
