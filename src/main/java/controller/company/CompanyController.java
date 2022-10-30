package controller.company;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.CompanyDto;
import service.crud.CompanyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@WebServlet(urlPatterns = "/company")
public class CompanyController extends HttpServlet {
    private CompanyService companyService;

    @Override
    public void init() throws ServletException {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbusername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        companyService = new CompanyService(manager);
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
        req.getRequestDispatcher("/WEB-INF/jsp/company/findCompany.jsp").forward(req, resp);
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
        CompanyDto company;
        Integer companyId = Integer.parseInt(req.getParameter("companyid"));
        company = companyService.findById(companyId);
        if (company.getId() != null){
            req.setAttribute("companies", List.of(company));
        } else {
            req.setAttribute("msg", "Company not found!");
        }
    }

    private void findByName(HttpServletRequest req){
        Set<CompanyDto> companies = companyService.findByName(req.getParameter("companyname"));
        req.setAttribute("companies", companies);
    }

    private void findAll(HttpServletRequest req){
        Set<CompanyDto> companies = companyService.selectAll();
        req.setAttribute("companies", companies);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompanyDto company = new CompanyDto();
        company.setName(req.getParameter("companyname"));
        company.setDescription(req.getParameter("companydesc"));
        companyService.create(company);
        req.setAttribute("msg", "Company successfully created!");
        req.getRequestDispatcher("/WEB-INF/jsp/company/createCompany.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompanyDto company = new CompanyDto();
        company.setId(Integer.parseInt(req.getParameter("companyid")));
        company.setName(req.getParameter("companyname"));
        company.setDescription(req.getParameter("companydesc"));
        if(companyService.update(company) == null){
            req.setAttribute("msg", "Update failed! Company not found!");
        } else {
            req.setAttribute("msg", "Company successfully updated!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/company/updateCompany.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer companyId = Integer.parseInt(req.getParameter("companyid"));
        if(companyService.findById(companyId).getId() == null){
            req.setAttribute("msg", "Delete failed! Company not found!");
        } else {
            companyService.delete(companyId);
            req.setAttribute("msg", "Company successfully deleted!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/company/deleteCompany.jsp").forward(req, resp);
    }
}
