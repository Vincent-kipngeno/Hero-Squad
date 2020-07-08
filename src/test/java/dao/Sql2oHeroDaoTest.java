package dao;

import models.Hero;
import org.junit.*;
import org.sql2o.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;



import static org.junit.Assert.*;

public class Sql2oHeroDaoTest {
    private static  Sql2oHeroDao heroDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        //String connectionString = "jdbc:postgresql://localhost:5432/hero_squad_test"; // connect to postgres test database
        String connectionString = "jdbc:postgresql://ec2-34-225-162-157.compute-1.amazonaws.com:5432/d910g3e6kb2asg?sslmode=require";
        //Sql2o sql2o = new Sql2o(connectionString, "vincent", "Taptet#2001");
        Sql2o sql2o = new Sql2o(connectionString, "hzdmiqhhnvyijh", "809f3b2d8e96f170d5c8194a952507d2be5eea955531beca9a9d6f7af5e1d8f3");
        heroDao = new Sql2oHeroDao(sql2o);
        conn = (Connection) sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        heroDao.clearAllHeroes();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    public void addingHeroesSetsId() {
        Hero hero = setNewHero();
        int originalHeroId = hero.getId();
        heroDao.add(hero);
        assertNotEquals(originalHeroId, hero.getId());
    }

    @Test
    public void add_individualHeroesCanBeFoundById() {
        Hero hero = setNewHero();
        heroDao.add(hero);
        Hero foundHero = heroDao.findById(hero.getId());
        assertEquals(hero, foundHero);
    }

    @Test
    public void findById_individualHeroesCanBeFoundById() {
        Hero hero = setNewHero();
        heroDao.add(hero);
        Hero foundHero = heroDao.findById(hero.getId());
        assertEquals(hero, foundHero);
    }

    @Test
    public void getAll_allHeroesAreReturnedCorrectly() {
        Hero hero = setNewHero();
        heroDao.add(hero);
        assertEquals(1, heroDao.getAll().size());
    }

    @Test
    public void getAll_nothingIsReturnedFromAnEmptyDatabase() {
        assertEquals(0, heroDao.getAll().size());
    }

    @Test
    public void update_heroIsUpdatedCorrectly() {
        Hero hero = setNewHero();
        heroDao.add(hero);
        int currentId = hero.getId();
        heroDao.update(currentId, 30, "hello", "singing","dancing",2);
        Hero updatedHero = heroDao.findById(currentId);
        assertNotEquals(hero, updatedHero);
    }

    @Test
    public void deleteById_individualHeroIsDeletedCorrectlyByItsId() {
        Hero hero = setNewHero();
        Hero otherHero = new Hero("kevin",15,"playing","laughing", 1);
        heroDao.add(hero);
        heroDao.add(otherHero);
        heroDao.deleteById(hero.getId());
        assertEquals(1, heroDao.getAll().size());
    }

    @Test
    public void clearAllHeroes_allAddedHeroesCanBeCleared() {
        Hero hero = setNewHero();
        Hero otherHero = new Hero("kevin",15,"playing","laughing", 1);
        heroDao.add(hero);
        heroDao.add(otherHero);
        heroDao.clearAllHeroes();
        assertEquals(0, heroDao.getAll().size());
    }

    public Hero setNewHero(){
        return new Hero("Vincent", 25, "Reading", "fluency", 1);
    }
}