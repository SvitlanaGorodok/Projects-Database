package controller.project;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.ProjectDto;
import service.crud.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@WebServlet(urlPatterns = "/project")
public class ProjectController extends HttpServlet {
    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbusername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        projectService = new ProjectService(manager);
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
        req.getRequestDispatcher("/WEB-INF/jsp/project/findProject.jsp").forward(req, resp);
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
        ProjectDto project;
        Integer projectId = Integer.parseInt(req.getParameter("projectid"));
        project = projectService.findById(projectId);
        if (project.getId() != null){
            req.setAttribute("projects", List.of(project));
        } else {
            req.setAttribute("msg", "Project not found!");
        }
    }

    private void findByName(HttpServletRequest req){
        Set<ProjectDto> projects = projectService.findByName(req.getParameter("projectname"));
        req.setAttribute("projects", projects);
    }

    private void findAll(HttpServletRequest req){
        Set<ProjectDto> projects = projectService.selectAll();
        req.setAttribute("projects", projects);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectDto project = new ProjectDto();
        project.setName(req.getParameter("projectname"));
        project.setDescription(req.getParameter("projectdesc"));
        project.setStartDate(Date.valueOf(req.getParameter("projectstartdt")));
        projectService.create(project);
        req.setAttribute("msg", "Project successfully created!");
        req.getRequestDispatcher("/WEB-INF/jsp/project/createProject.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectDto project = new ProjectDto();
        project.setId(Integer.parseInt(req.getParameter("projectid")));
        project.setName(req.getParameter("projectname"));
        project.setDescription(req.getParameter("projectdesc"));
        project.setStartDate(Date.valueOf(req.getParameter("projectstartdt")));
        if(projectService.update(project) == null){
            req.setAttribute("msg", "Update failed! Project not found!");
        } else {
            req.setAttribute("msg", "Project successfully updated!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/project/updateProject.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer projectId = Integer.parseInt(req.getParameter("projectid"));
        if(projectService.findById(projectId).getId() == null){
            req.setAttribute("msg", "Delete failed! Project not found!");
        } else {
            projectService.delete(projectId);
            req.setAttribute("msg", "Project successfully deleted!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/project/deleteProject.jsp").forward(req, resp);
    }
}
