import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oSquadDao;
import dao.Sql2oHeroDao;
import models.Squad;
import models.Hero;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        //String connectionString = "jdbc:postgresql://localhost:5432/hero_squad";
        String connectionString = "jdbc:postgresql://ec2-52-204-232-46.compute-1.amazonaws.com:5432/d9hl8541b0v65d?sslmode=require";
        //Sql2o sql2o = new Sql2o(connectionString, "vincent", "Taptet#2001");
        Sql2o sql2o = new Sql2o(connectionString, "ytkddhbgeseitu", "24247282bd68c436cd7af9dc6543ca4a3878e6dd466a30eeb3cf963b53936300");
        Sql2oHeroDao heroDao = new Sql2oHeroDao(sql2o);
        Sql2oSquadDao squadDao = new Sql2oSquadDao(sql2o);


        //get: show all heroes in all squads and show all squads
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> allSquads = squadDao.getAll();
            model.put("squads", allSquads);
            List<Hero> heroes = heroDao.getAll();
            model.put("heroes", heroes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new squad
        get("/squads/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> squads = squadDao.getAll(); //refresh list of links for navbar
            model.put("squads", squads);
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new squad
        post("/squads", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String causeToFight = req.queryParams("cause");
            Squad newSquad = new Squad( name, causeToFight);
            squadDao.add(newSquad);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all squads and all heroes
        get("/squads/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            squadDao.clearAllSquads();
            heroDao.clearAllHeroes();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all heroes
        get("/heroes/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            heroDao.clearAllHeroes();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific squad (and the heroes it contains)
        get("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSquadToFind = Integer.parseInt(req.params("id"));
            Squad foundSquad = squadDao.findById(idOfSquadToFind);
            model.put("squad", foundSquad);
            List<Hero> allHeroesBySquad = squadDao.getAllHeroesBySquad(idOfSquadToFind);
            model.put("heroes", allHeroesBySquad);
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "squad-details.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a squad
        get("/squads/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editSquad", true);
            Squad squad = squadDao.findById(Integer.parseInt(req.params("id")));
            model.put("squad", squad);
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a squad
        post("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSquadToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("name");
            String causeToFight = req.queryParams("cause");
            squadDao.update(idOfSquadToEdit, newName, causeToFight);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual hero
        get("/squads/:squad_id/heroes/:hero_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToDelete = Integer.parseInt(req.params("hero_id"));
            heroDao.deleteById(idOfHeroToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new hero form
        get("/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> squads = squadDao.getAll();
            model.put("squads", squads);
            List<Squad> squadsWithSpace = new ArrayList<Squad>();
            for (Squad squad:squads ) {
                int squadId = squad.getId();
                if (squadDao.getAllHeroesBySquad(squadId).size() < squad.getMaximumSize()){
                    squadsWithSpace.add(squad);
                }
            }
            model.put("squadsWithSpace", squadsWithSpace);
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new hero form
        post("/heroes", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> allSquads = squadDao.getAll();
            model.put("squads", allSquads);
            String name = req.queryParams("name");
            int age = Integer.parseInt(req.queryParams("age"));
            String specialPower = req.queryParams("specialPower");
            String weakness = req.queryParams("weakness");
            int squadId = Integer.parseInt(req.queryParams("squadId"));
            Hero newHero = new Hero(name, age, specialPower, weakness, squadId);
            heroDao.add(newHero);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual hero that is nested in a squad
        get("/squads/:squad_id/heroes/:hero_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToFind = Integer.parseInt(req.params("hero_id"));
            Hero foundHero = heroDao.findById(idOfHeroToFind);
            int idOfSquadToFind = Integer.parseInt(req.params("squad_id"));
            Squad foundSquad = squadDao.findById(idOfSquadToFind);
            model.put("squad", foundSquad);
            model.put("hero", foundHero);
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "hero-detail.hbs"); //individual hero page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a hero
        get("/heroes/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> allSquads = squadDao.getAll();
            model.put("squads", allSquads);
            List<Squad> squadsWithSpace = new ArrayList<Squad>();
            for (Squad squad:allSquads) {
                int squadId = squad.getId();
                if (squadDao.getAllHeroesBySquad(squadId).size() < squad.getMaximumSize()){
                    squadsWithSpace.add(squad);
                }
            }
            model.put("squadsWithSpace", squadsWithSpace);
            Hero hero = heroDao.findById(Integer.parseInt(req.params("id")));
            model.put("hero", hero);
            Squad foundSquad = squadDao.findById(hero.getId());
            model.put("squad", foundSquad);
            model.put("editHero", true);
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a hero
        post("/heroes/:id", (req, res) -> { //URL to update hero on POST route
            Map<String, Object> model = new HashMap<>();
            int heroToEditId = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("name");
            int age = Integer.parseInt(req.queryParams("age"));
            String specialPower = req.queryParams("specialPower");
            String weakness = req.queryParams("weakness");
            int newSquadId = Integer.parseInt(req.queryParams("squadId"));
            heroDao.update(heroToEditId,age, newName, specialPower, weakness, newSquadId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}