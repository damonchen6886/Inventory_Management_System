package com.inventory.ims.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.inventory.ims.dto.*;
import com.inventory.ims.service.SaleService;
import com.inventory.ims.service.impl.SaleServiceImpl;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@WebServlet("/sale/*")
public class SaleServlet extends BaseServlet {

    private SaleService service = new SaleServiceImpl();

    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> m = request.getParameterNames();
        String s = m.nextElement();
        JSONObject obj = new JSONObject(s);
        ObjectMapper mapper = new ObjectMapper();
        Sale sale = mapper.readValue(obj.get("sale").toString(), Sale.class);
        TypeFactory factory = mapper.getTypeFactory();
        ItemInTransaction[] items = mapper.readValue(obj.get("items").toString(), factory.constructArrayType(Item.class));
        boolean f =this.service.insert(sale, items);
        ResultInfo r = new ResultInfo();
        r.setFlag(f);
        if (!f) {
            r.setErrorMsg("Failure to insert!");
        }
        writeValue(r, response);
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Sale> l = this.service.getAll();
        writeValue(l, response);
    }

    public void getOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("ref");
        Sale s = this.service.getOne(id);
        if (s == null) {
            writeValue(this.reportFailure("No satisfied sale!"), response);
        } else {
            writeValue(this.reportSuccess(s), response);
        }
    }

    public void getSales(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        String[] d = map.get("date");
        String[] c = map.get("customer");
        String[] s = map.get("store");
        String date = d == null ? null : d[0];
        String vendor = c == null ? null : c[0];
        String store = s == null ? null : s[0];
        List<Sale> sales = this.service.getSales(date, vendor, store);
        if (sales == null || sales.isEmpty()) {
            writeValue(this.reportFailure("No satisfied sale!"), response);
        } else {
            writeValue(this.reportSuccess(sales), response);
        }
    }

    public void getItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("ref");
        List<ItemInTransaction> l = this.service.getItems(key);
        if (l == null || l.isEmpty()) {
            writeValue(this.reportFailure("Cannot find item with provided sale id!"), response);
        } else {
            writeValue(this.reportSuccess(l), response);
        }
    }

    public void returnSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String saleId = request.getParameter("sale");
        String item = request.getParameter("item");
        String quant = request.getParameter("quantity");
        boolean f = this.service.returnSale(saleId, item, quant);
        ResultInfo r = new ResultInfo();
        r.setFlag(f);
        if (!f) {
            r.setErrorMsg("Failure to return!");
        }
        writeValue(r, response);

    }

}
