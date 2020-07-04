package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeroTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void newHero_instantiatesCorrectly() {
        Hero hero = helperMethod();
        assertTrue(hero instanceof Hero);
    }

    @Test
    public void getName_returnsCorrectName() {
        Hero hero = helperMethod();
        assertEquals("Vincent", hero.getName());
    }

    @Test
    public void getAge_returnsCorrectAge() {
        Hero hero = helperMethod();
        assertEquals(25, hero.getAge());
    }

    @Test
    public void getSpecialPower_returnsCorrectSpecialPower() {
        Hero hero = helperMethod();
        assertEquals("Reading", hero.getSpecialPower());
    }

    @Test
    public void getWeakness_correctWeaknessIsReturned() {
        Hero hero = helperMethod();
        assertEquals("fluency", hero.getWeakness());
    }

    @Test
    public void getId() {
        Hero hero = helperMethod();
        hero.setId(1);
        assertEquals(1, hero.getId());
    }

    @Test
    public void getSquadId() {
    }

    @Test
    public void setId() {
    }

    public Hero helperMethod(){
        return new Hero("Vincent", 25, "Reading", "fluency", 1);
    }
}