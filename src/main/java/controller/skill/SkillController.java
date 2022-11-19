package controller.skill;

import config.HibernateProvider;
import entities.dto.SkillDto;
import service.crud.SkillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "/skill")
public class SkillController extends HttpServlet {
    private SkillService skillService;

    @Override
    public void init() {
        HibernateProvider manager = new HibernateProvider();
        skillService = new SkillService(manager);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        switch (operation) {
            case "Find by id":
                findById(req);
                break;
            case "Find by area":
                findByName(req);
                break;
            case "Show all":
                findAll(req);
                break;
        }
        req.getRequestDispatcher("/WEB-INF/jsp/skill/findSkill.jsp").forward(req, resp);
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
        SkillDto skill;
        Integer skillId = Integer.parseInt(req.getParameter("skillid"));
        skill = skillService.findById(skillId);
        if (skill != null){
            req.setAttribute("skills", List.of(skill));
        } else {
            req.setAttribute("msg", "Skill not found!");
        }
    }

    private void findByName(HttpServletRequest req){
        Set<SkillDto> skills = skillService.findByName(req.getParameter("skillarea"));
        if (skills.isEmpty()){
            req.setAttribute("msg", "Skill not found!");
        } else {
            req.setAttribute("skills", skills);
        }
    }

    private void findAll(HttpServletRequest req){
        Set<SkillDto> skills = skillService.selectAll();
        req.setAttribute("skills", skills);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SkillDto skill = new SkillDto();
        skill.setArea(req.getParameter("skillarea"));
        skill.setLevel(req.getParameter("skilllevel"));
        skillService.create(skill);
        req.setAttribute("msg", "Skill successfully created!");
        req.getRequestDispatcher("/WEB-INF/jsp/skill/createSkill.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SkillDto skill = new SkillDto();
        skill.setId(Integer.parseInt(req.getParameter("skillid")));
        skill.setArea(req.getParameter("skillarea"));
        skill.setLevel(req.getParameter("skilllevel"));
        if (skillService.findById(skill.getId()) != null){
            skillService.update(skill);
            req.setAttribute("msg", "Skill successfully updated!");
        } else {
            req.setAttribute("msg", "Update failed! Skill not found!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/skill/updateSkill.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer skillId = Integer.parseInt(req.getParameter("skillid"));
        SkillDto skill = skillService.findById(skillId);
        if (skill != null){
            skillService.delete(skill);
            req.setAttribute("msg", "Skill successfully deleted!");
        } else {
            req.setAttribute("msg", "Delete failed! Skill not found!");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/skill/deleteSkill.jsp").forward(req, resp);
    }
}
