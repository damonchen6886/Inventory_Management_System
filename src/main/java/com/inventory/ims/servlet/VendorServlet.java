package com.inventory.ims.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.ims.dto.Item;
import com.inventory.ims.dto.ResultInfo;
import com.inventory.ims.dto.SoldItem;
import com.inventory.ims.dto.Vendor;
import com.inventory.ims.service.VendorService;
import com.inventory.ims.service.impl.VendorServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


@WebServlet("/vendor/*")
public class VendorServlet extends BaseServlet {

    private VendorService service = new VendorServiceImpl();

    // can user Class.forName to Abstract
    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        Vendor vendor = new Vendor();
        try {
            BeanUtils.populate(vendor, map);
            boolean flag = this.service.insert(vendor);
            ResultInfo r = new ResultInfo();
            r.setFlag(flag);
            if (!flag) {
                r.setErrorMsg("Fail to insert vendor: " + vendor.getName());
            }
            writeValue(r, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Vendor> l = this.service.getAll();
        writeValue(l, response);
    }

    // get by name or id, all that kind of method can be abstract with reflection
    public void getOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("ref");
        Vendor i = this.service.getOne(key);
        if (i == null) {
            writeValue(this.reportFailure("No such vendor!"), response);
        } else {
            writeValue(this.reportSuccess(i), response);
        }
    }


    public void getByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String state = request.getParameter("state");
        List<Vendor> l = this.service.getByState(state);
        if (l == null || l.isEmpty()) {
            writeValue(this.reportFailure("No vendor in state: " + state), response);
        } else {
            writeValue(this.reportSuccess(l), response);
        }

    }


    // need vendor id or name
    public void getSoldItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("ref");
        List<Item> l = this.service.getSoldItem(key);
        if (l == null || l.isEmpty()) {
            writeValue(this.reportFailure("No available items!"), response);
        } else {
            writeValue(this.reportSuccess(l), response);
        }
    }

    public void addSoldItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        SoldItem s = mapper.readValue(request.getInputStream(), SoldItem.class);
        boolean f = this.service.addSoldItem(s);
        ResultInfo r = new ResultInfo();
        r.setFlag(f);
        if (!f) {
            r.setErrorMsg("Failure to add those records!");
        }
        writeValue(r, response);
    }

}
