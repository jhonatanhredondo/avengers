package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.HeroJavaBeans;

import java.io.IOException;
import java.util.ArrayList;

import enums.Status;

@WebServlet({ "/Controller", "/actions" })
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ArrayList<HeroJavaBeans> heroes = new ArrayList<>();

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("details")) {
                this.getHeroDetails(request, response);
                return;
            } else if (action.equals("insert")) {
                response.sendRedirect("pages/insert.jsp");
                return;
            }
        }
        // Ação padrão: listar todos os heróis
        this.listAllTheHeroes(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("update")) {
                this.updateHero(request, response);
            } else if (action.equals("delete")) {
                this.deleteHero(request, response);
            } else if (action.equals("add")) {
                this.addNewHero(request, response);
            }
        }
    }

    private void addNewHero(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HeroJavaBeans hero = new HeroJavaBeans();

        String heroName = request.getParameter("formHeroName");
        String heroStatus = request.getParameter("formHeroStatus");
        String heroPowerName = request.getParameter("formPowerName");

        hero.setHeroName(heroName);
        hero.setHeroStatus(Status.valueOf(heroStatus));
        hero.setPowerName(heroPowerName);

        DAO dao = new DAO();
        dao.insertNewHero(hero);

        response.sendRedirect("Controller");
    }

    private void listAllTheHeroes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        this.heroes = dao.getAllTheHeroes();

        request.setAttribute("heroesList", this.heroes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void getHeroDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String heroIdString = request.getParameter("heroId");
        int heroId = Integer.parseInt(heroIdString);

        DAO dao = new DAO();
        HeroJavaBeans hero = dao.getHeroById(heroId);

        request.setAttribute("heroDetails", hero);

        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/details.jsp");
        dispatcher.forward(request, response);
    }

    private void updateHero(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HeroJavaBeans hero = new HeroJavaBeans();

        hero.setHeroId(Integer.parseInt(request.getParameter("heroId")));
        hero.setHeroName(request.getParameter("formHeroName"));
        hero.setHeroStatus(Status.valueOf(request.getParameter("formHeroStatus")));
        hero.setPowerId(Integer.parseInt(request.getParameter("powerId")));
        hero.setPowerName(request.getParameter("formPowerName"));

        DAO dao = new DAO();
        dao.updateHero(hero);

        response.sendRedirect("Controller");
    }

    private void deleteHero(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int heroId = Integer.parseInt(request.getParameter("heroId"));
        int powerId = Integer.parseInt(request.getParameter("powerId"));

        DAO dao = new DAO();
        dao.deleteHero(heroId, powerId);

        response.sendRedirect("Controller");
    }
}