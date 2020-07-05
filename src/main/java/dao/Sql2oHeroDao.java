package dao;

import models.Hero;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oHeroDao implements HeroDao{
    private final Sql2o sql2o;

    public Sql2oHeroDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add (Hero hero){
        String sql = "INSERT INTO heroes (name, age, specialPower, weakness, squadId) VALUES (:name, :age, :specialPower, :weakness, :squadId)";
        try (Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true)
                            .bind(hero)
                            .executeUpdate()
                            .getKey();
            hero.setId(id);
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Hero> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM heroes")
                    .executeAndFetch(Hero.class);
        }
    }

    @Override
    public Hero findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM heroes WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Hero.class);
        }
    }

    @Override
    public void update(int id, String name, String specialPower, String weakness, int squadId) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAllHeroes() {

    }
}
