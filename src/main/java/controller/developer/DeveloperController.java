package controller.developer;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.DeveloperDto;
import service.crud.DeveloperService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@WebServlet(urlPatterns = "/developer")
public class DeveloperController extends HttpServlet {
    private DeveloperService developerService;

    @Override
    public void init() throws ServletException {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbusername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        developerService = new DeveloperService(manager);
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
        req.getRequestDispatcher("/WEB-INF/jsp/developer/findDeveloper.jsp").forward(req, resp);
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
        DeveloperDto developer;
        Integer developerId = Integer.parseInt(req.getParameter("devid"));
        developer = developerService.findById(developerId);
        if (developer.getId() != null){
            req.setAttribute("developers", List.of(developer));
        } else {
            req.setAttribute("msg", "Developer not found!");
        }
    }

    private void findByName(HttpServletRequest req){
        Set<DeveloperDto> developers = developerService.findByName(req.getParameter("devname"));
        req.setAttribute("developers", developers);
    }

    private void findAll(HttpServletRequest req){
        Set<DeveloperDto> developers = developerService.selectAll();
        req.setAttribute("developers", developers);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeveloperDto developer = new DeveloperDto();
        developer.setFirstName(req.getParameter("devfirstname"));
        developer.setLastName(req.getParameter("devlastname"));
        developer.setAge(Integer.parseInt(req.getParameter("devage")));
        developer.setSalary(Integer.parseInt(req.getParameter("devsalary")));
        developerService.create(developer);
        req.setAttribute("msg", "Developer successfully created!");
        req.getRequestDispatcher("/WEB-INF/jsp/developer/createDeveloper.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeveloperDto developer = new DeveloperDto();
        developer.setId(Integer.parseInt(req.getParameter("devid")));
        developer.setFirstName(req.getParameter("devfirstname"));
        developer.setLastName(req.getParameter("devlastname"));
        developer.setAge(Integer.parseInt(req.getParameter("devage")));
        developer.setSalary(Integer.parseInt(req.getParameter("devsalary")));
        if(developerService.update(developer) == null){
            req.setAttribute("msg", "Update failed! Developer not found!");
        } else {
            req.setAttribute("msg", "Developer successfully updated!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/developer/updateDeveloper.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer developerId = Integer.parseInt(req.getParameter("devid"));
        if(developerService.findById(developerId).getId() == null){
            req.setAttribute("msg", "Delete failed! Developer not found!");
        } else {
            developerService.delete(developerId);
            req.setAttribute("msg", "Developer successfully deleted!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/developer/deleteDeveloper.jsp").forward(req, resp);
    }
}
