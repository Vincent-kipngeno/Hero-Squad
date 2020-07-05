package dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.Connection;

import static org.junit.Assert.*;

public class Sql2oHeroDaoTest {
    public static  Sql2oHeroDao heroDao;
    public static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void shutDown() throws Exception{

    }
}