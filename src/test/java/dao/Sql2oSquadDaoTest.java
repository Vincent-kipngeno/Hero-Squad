package dao;

import models.Hero;
import models.Squad;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oSquadDaoTest {
    private static Sql2oSquadDao squadDao;
    private static Sql2oHeroDao heroDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        //String connectionString = "jdbc:postgresql://localhost:5432/hero_squad_test"; // connect to postgres test database
        String connectionString = "jdbc:postgresql://ec2-35-168-54-239.compute-1.amazonaws.com:5432/d7v15029ta6m";
        Sql2o sql2o = new Sql2o(connectionString, "edqfbqkssenioc", "275aa32494363e11780caf12553918ae7f1712c74a42c769cdf11d9dd4d989fb");
        squadDao = new Sql2oSquadDao(sql2o);
        heroDao = new Sql2oHeroDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        squadDao.clearAllSquads();
        heroDao.clearAllHeroes();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingSquadSetsId() throws Exception {
        Squad squad = setupNewSquad();
        int originalSquadId = squad.getId();
        squadDao.add(squad);
        assertNotEquals(originalSquadId, squad.getId());
    }

    @Test
    public void existingSquadsCanBeFoundById() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.add(squad);
        int originalSquad = squad.getId();
        Squad foundSquad = squadDao.findById(originalSquad);
        assertEquals(squad, foundSquad);
    }

    @Test
    public void addedSquadsAreReturnedFromGetAll() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.add(squad);
        assertEquals(1, squadDao.getAll().size());
    }

    @Test
    public void noSquadsReturnsEmptyList() throws Exception {
        assertEquals(0, squadDao.getAll().size());
    }

    @Test
    public void update_squadIsUpdatedCorrectly() {
        Squad squad = setupNewSquad();
        squadDao.add(squad);
        int currentId = squad.getId();
        squadDao.update(currentId, "indiscipline", "discipline");
        Squad updatedSquad = squadDao.findById(currentId);
        assertNotEquals(squad, updatedSquad);
    }

    @Test
    public void deleteByIdDeletesCorrectSquad() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.add(squad);
        squadDao.deleteById(squad.getId());
        assertEquals(0, squadDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllSquads() throws Exception {
        Squad squad = setupNewSquad();
        Squad otherSquad = new Squad( "female", "feminism");
        squadDao.add(squad);
        squadDao.add(otherSquad);
        int daoSize = squadDao.getAll().size();
        squadDao.clearAllSquads();
        assertTrue(daoSize > 0 && daoSize > squadDao.getAll().size());
    }

    @Test
    public void getAllHeroesBySquadReturnsHeroesCorrectly() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.add(squad);
        int squadId = squad.getId();
        Hero newHero = new Hero("Vincent", 25, "Reading", "fluency", squadId);
        Hero otherHero = new Hero("Kevin", 20, "Learning", "passing", squadId);
        Hero thirdHero = new Hero("Vincent", 45, "Leadership", "talking", squadId);
        heroDao.add(newHero);
        heroDao.add(otherHero);
        assertEquals(2, squadDao.getAllHeroesBySquad(squadId).size());
        assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(newHero));
        assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(otherHero));
        assertFalse(squadDao.getAllHeroesBySquad(squadId).contains(thirdHero)); //things are accurate!
    }


    // helper method
    public Squad setupNewSquad(){
        return new Squad("discipline", "indiscipline");
    }
}