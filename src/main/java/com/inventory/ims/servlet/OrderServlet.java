package com.inventory.ims.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.inventory.ims.dto.Item;
import com.inventory.ims.dto.ItemInTransaction;
import com.inventory.ims.dto.Order;
import com.inventory.ims.dto.ResultInfo;
import com.inventory.ims.service.OrderService;
import com.inventory.ims.service.impl.OrderServiceImpl;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@WebServlet("/order/*")
public class OrderServlet extends BaseServlet {

    private OrderService service = new OrderServiceImpl();

    // Coming json can be {order: o, items: [{}, {}, {}]}
    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> m = request.getParameterNames();
        String s = m.nextElement();
        JSONObject obj = new JSONObject(s);
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(obj.get("order").toString(), Order.class);
        TypeFactory factory = mapper.getTypeFactory();
        ItemInTransaction[] items = mapper.readValue(obj.get("items").toString(), factory.constructArrayType(Item.class));
        boolean f =this.service.insert(order, items);
        ResultInfo r = new ResultInfo();
        r.setFlag(f);
        if (!f) {
            r.setErrorMsg("Failure to insert!");
        }
        writeValue(r, response);

    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> l = this.service.getAll();
        writeValue(l, response);
    }

    public void getOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("ref");
        Order o = this.service.getOne(id);
        if (o == null) {
            writeValue(this.reportFailure("No satisfied order!"), response);
        } else {
            writeValue(this.reportSuccess(o), response);
        }
    }

    public void getOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        String[] d = map.get("date");
        String[] v = map.get("vendor");
        String[] s = map.get("store");
        String date = d == null ? null : d[0];
        String vendor = v == null ? null : v[0];
        String store = s == null ? null : s[0];
        List<Order> orders = this.service.getOrders(date, vendor, store);
        if (orders == null || orders.isEmpty()) {
            writeValue(this.reportFailure("No satisfied order!"), response);
        } else {
            writeValue(this.reportSuccess(orders), response);
        }
    }

    public void getItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("ref");
        List<ItemInTransaction> l = this.service.getItems(key);
        if (l == null || l.isEmpty()) {
            writeValue(this.reportFailure("Cannot find item with provided order id!"), response);
        } else {
            writeValue(this.reportSuccess(l), response);
        }
    }

    public void updateDeliver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("order");
        boolean f = this.service.updateDeliverDate(key);
        ResultInfo r = new ResultInfo();
        r.setFlag(f);
        if (!f) {
            r.setErrorMsg("Failure to update!");
        }
        writeValue(r, response);

    }
}
