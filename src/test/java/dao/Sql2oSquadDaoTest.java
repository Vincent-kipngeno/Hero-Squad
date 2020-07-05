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
        String connectionString = "jdbc:postgresql://localhost:5432/hero_squad_test"; // connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "vincent", "Taptet#2001");         // changed user and pass to null
        squadDao = new Sql2oSquadDao(sql2o);
        heroDao = new Sql2oHeroDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        squadDao.clearAllSquads(); // clear all categories after every test
        heroDao.clearAllHeroes(); // clear all tasks after every test
    }

    @AfterClass // changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception { //changed to static and shutDown
        conn.close(); // close connection once after this entire test file is finished
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
        squadDao.update(currentId, 6, "indiscipline", "discipline");
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
        Squad otherSquad = new Squad(4, "female", "feminism");
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
        heroDao.add(otherHero); //we are not adding task 3 so we can test things precisely.
        assertEquals(2, squadDao.getAllHeroesBySquad(squadId).size());
        assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(newHero));
        assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(otherHero));
        assertFalse(squadDao.getAllHeroesBySquad(squadId).contains(thirdHero)); //things are accurate!
    }

    @Test
    public void getAllSquadsWithSpace_noSquadIsReturnedWhenAllItsMaximumSizeHasReached() throws Exception {
        Squad squad = new Squad(3, "discipline", "indiscipline");
        squadDao.add(squad);
        int squadId = squad.getId();
        Hero newHero = new Hero("Vincent", 25, "Reading", "fluency", squadId);
        Hero otherHero = new Hero("Kevin", 20, "Learning", "passing", squadId);
        Hero thirdHero = new Hero("Vincent", 45, "Leadership", "talking", squadId);
        heroDao.add(newHero);
        heroDao.add(otherHero); //we are not adding task 3 so we can test things precisely.
        heroDao.add(thirdHero);
        List<Squad> allSquads = squadDao.getAll();
        assertTrue(squadDao.getAllSquadsWithSpace(allSquads, squadDao).size() > 0);
    }

    // helper method
    public Squad setupNewSquad(){
        return new Squad(4, "discipline", "indiscipline");
    }
}