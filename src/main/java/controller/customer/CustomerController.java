package controller.customer;

import config.HibernateProvider;
import entities.dto.CustomerDto;
import service.crud.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() {
        HibernateProvider manager = new HibernateProvider();
        customerService = new CustomerService(manager);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        switch (operation) {
            case "Find by id":
                findById(req);
                break;
            case "Find by name":
                findByName(req);
                break;
            case "Show all":
                findAll(req);
                break;
        }
        req.getRequestDispatcher("/WEB-INF/jsp/customer/findCustomer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        switch (operation) {
            case "Create":
                create(req, resp);
                break;
            case "Update":
                update(req, resp);
                break;
            case "Delete":
                delete(req, resp);
                break;
        }
    }

    private void findById(HttpServletRequest req){
        CustomerDto customer;
        Integer customerId = Integer.parseInt(req.getParameter("customerid"));
        customer = customerService.findById(customerId);
        if (customer != null){
            req.setAttribute("customers", List.of(customer));
        } else {
            req.setAttribute("msg", "Customer not found!");
        }
    }

    private void findByName(HttpServletRequest req){
        Set<CustomerDto> customers = customerService.findByName(req.getParameter("customername"));
        if (customers.isEmpty()){
            req.setAttribute("msg", "Customer not found!");
        } else {
            req.setAttribute("customers", customers);
        }
    }

    private void findAll(HttpServletRequest req){
        Set<CustomerDto> customers = customerService.selectAll();
        req.setAttribute("customers", customers);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerDto customer = new CustomerDto();
        customer.setName(req.getParameter("customername"));
        customer.setDescription(req.getParameter("customerdesc"));
        customerService.create(customer);
        req.setAttribute("msg", "Customer successfully created!");
        req.getRequestDispatcher("/WEB-INF/jsp/customer/createCustomer.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerDto customer = new CustomerDto();
        customer.setId(Integer.parseInt(req.getParameter("customerid")));
        customer.setName(req.getParameter("customername"));
        customer.setDescription(req.getParameter("customerdesc"));
        if (customerService.findById(customer.getId()) != null){
            customerService.update(customer);
            req.setAttribute("msg", "Customer successfully updated!");
        } else {
            req.setAttribute("msg", "Update failed! Customer not found!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/customer/updateCustomer.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer customerId = Integer.parseInt(req.getParameter("customerid"));
        CustomerDto customer = customerService.findById(customerId);
        if (customer != null){
            customerService.delete(customer);
            req.setAttribute("msg", "Customer successfully deleted!");
        } else {
            req.setAttribute("msg", "Delete failed! Customer not found!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/customer/deleteCustomer.jsp").forward(req, resp);
    }
}
