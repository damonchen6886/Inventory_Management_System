package com.inventory.ims.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.ims.dto.Item;
import com.inventory.ims.dto.ResultInfo;
import com.inventory.ims.service.ItemService;
import com.inventory.ims.service.impl.ItemServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/item/*")
public class ItemServlet extends BaseServlet {

    private ItemService service = new ItemServiceImpl();

    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Item i = mapper.readValue(request.getInputStream(), Item.class);
        boolean flag = this.service.insert(i);
        ResultInfo r = new ResultInfo();
        r.setFlag(flag);
        if (!flag) {
            r.setErrorMsg("Fail to insert item: " + i.getName());
        }
        writeValue(r, response);

    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> l = this.service.getAll();
        writeValue(l, response);
    }

    /**
     * get out only one record
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("ref");
        Item i = this.service.getOne(key);
        ResultInfo r = new ResultInfo();
        if (i == null) {
            writeValue(this.reportSuccess("Cannot find this item!"), response);
        } else {
            writeValue(this.reportSuccess(i), response);
        }

    }

    public void getByCat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("ref");
        List<Item> l = this.service.getByCat(key);
        if (l == null || l.isEmpty()) {
            writeValue(this.reportFailure("Cannot find item under that category!"), response);
        } else {
            writeValue(this.reportSuccess(l), response);
        }
    }
}
