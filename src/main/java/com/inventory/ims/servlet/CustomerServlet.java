package com.inventory.ims.servlet;

import com.inventory.ims.dto.Customer;
import com.inventory.ims.dto.ResultInfo;
import com.inventory.ims.service.CustomerService;
import com.inventory.ims.service.impl.CustomerServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/customer/*")
public class CustomerServlet extends BaseServlet {

    private CustomerService service = new CustomerServiceImpl();

    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        Customer customer = new Customer();
        try {
            BeanUtils.populate(customer, map);
            if (this.service.insert(customer)) {
                // there is no such category
                // report success to the user
            } else {
                // there is such category
                // alert the user
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void getOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("ref");
        Customer cus = this.service.getOne(key);
        ResultInfo r = new ResultInfo();
        if (cus == null) {
            r.setFlag(false);
            r.setErrorMsg("No such customer!");
        } else {
            r.setFlag(true);
            r.setData(cus);
        }
        writeValue(r, response);
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> l = this.service.getAll();
        writeValue(l, response);
    }
}
