package com.inventory.ims.servlet;

import com.inventory.ims.dto.RetailStore;
import com.inventory.ims.dto.ResultInfo;
import com.inventory.ims.service.RetailStoreService;
import com.inventory.ims.service.impl.RetailStoreServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/store/*")
public class StoreServlet extends BaseServlet {

    private RetailStoreService service = new RetailStoreServiceImpl();

    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        RetailStore store = new RetailStore();
        try {
            BeanUtils.populate(store, map);
            boolean flag = this.service.insert(store);
            ResultInfo r = new ResultInfo();
            r.setFlag(flag);
            if (!flag) {
                r.setErrorMsg("Fail to insert this RetailStore!");
            }
            writeValue(r, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RetailStore> l = this.service.getAll();
        writeValue(l, response);
    }

    public void getOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("ref");
        RetailStore store = this.service.getOne(id);
        if (store == null) {
            writeValue(this.reportFailure("No such store"), response);
        } else {
            writeValue(this.reportSuccess(store), response);
        }

    }

    public void getByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String state = request.getParameter("state");
        List<RetailStore> l = this.service.getByState(state);
        if (l == null || l.isEmpty()) {
            writeValue(this.reportFailure("No vendor in state: " + state), response);
        } else {
            writeValue(this.reportSuccess(l), response);
        }

    }
}
