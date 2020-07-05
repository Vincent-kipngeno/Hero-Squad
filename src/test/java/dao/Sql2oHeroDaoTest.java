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
        String connectionString = "jdbc:postgresql://localhost:5432/hero_squad_test"; // connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "vincent", "Taptet#2001");
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

    public Hero setNewHero(){
        return new Hero("Vincent", 25, "Reading", "fluency", 1);
    }
}