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

    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/hero_squad";
        Sql2o sql2o = new Sql2o(connectionString, "vincent", "Taptet#2001");
        //String connectionString = "jdbc:postgresql://ec2-35-153-12-59.compute-1.amazonaws.com:5432/dhvov5qncp41e";
        //Sql2o sql2o = new Sql2o(connectionString, "jkesdylhdalfvf", "0ad5503c5d6f7d91c2a59992963e9d590205e61dca094458a3c016f29d164d32");
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
            return new ModelAndView(model, "squad-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new squad
        post("/squads", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            int maximumSize = Integer.parseInt(req.queryParams("maximumSize"));
            String causeToFight = req.queryParams("cause");
            Squad newSquad = new Squad(maximumSize, name, causeToFight);
            squadDao.add(newSquad);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}