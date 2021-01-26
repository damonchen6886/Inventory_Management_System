package com.inventory.ims.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.ims.dto.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("connected");
        String uri= req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        }  catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
//        Item i = new Item();
//        Category c = new Category();
//        c.setId(10);
//        c.setName("fruit");
//        c.setDescription("an apple");
//        i.setCategory(c);
//        i.setId(1);
//        i.setName("apple");
//        i.setPrice(0.99);
//        String v = writeValueAsString(i);
//        System.out.println(v);
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.readValue(v, Item.class));
    }

    /**
     * write back object as json into response
     * @param obj
     * @param response
     * @throws IOException
     */
    protected void writeValue(Object obj, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), obj);
    }

    protected String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    /** Read the json date from the request body and build a Bean from it */
    protected static <T> T readJson(Class<T> type, HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(request.getInputStream(), type);
    }

    /**
     * Set the ResultInfo
     */
    protected ResultInfo reportSuccess(Object obj ) {
        ResultInfo r = new ResultInfo();
        r.setFlag(true);
        r.setData(obj);
        return r;
    }

    protected ResultInfo reportFailure(String msg) {
        ResultInfo r = new ResultInfo();
        r.setFlag(false);
        r.setErrorMsg(msg);
        return r;
    }


}
