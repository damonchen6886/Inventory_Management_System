package com.inventory.ims.servlet;

import com.inventory.ims.dto.Category;
import com.inventory.ims.dto.ResultInfo;
import com.inventory.ims.service.CategoryService;
import com.inventory.ims.service.impl.CategoryServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    public void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        Category cat = new Category();
        try {
            BeanUtils.populate(cat, map);
            boolean flag = this.service.insert(cat);
            ResultInfo r = new ResultInfo();
            r.setFlag(flag);
            if (!flag) {
                r.setErrorMsg("Fail to insert category: " + cat.getName());
            }
            writeValue(r, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> l = this.service.getAll();
        writeValue(l, response);
    }

    public void getOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("ref");
        Category cat = this.service.getOne(key);
        if (cat == null) {
            writeValue(this.reportFailure("No such category!"), response);
        } else {
            writeValue(this.reportSuccess(cat), response);
        }

    }
}
