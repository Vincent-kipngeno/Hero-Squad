package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SquadTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void getMaximumSize_correctMaxSizeIsReturned() {
        Squad squad =  helperMethod();
        assertEquals(10, squad.getMaximumSize());
    }

    @Test
    public void getName_correctNameIsReturned() {
        Squad squad =  helperMethod();
        assertEquals("discipline", squad.getName());
    }

    @Test
    public void getCauseToFight_correctCauseToFightIsReturned() {
        Squad squad =  helperMethod();
        assertEquals("indiscipline", squad.getCauseToFight());
    }

    @Test
    public void getId_correctIdIsReturned() {
        Squad squad =  helperMethod();
        squad.setId(1);
        assertEquals(1, squad.getId());
    }

    @Test
    public void setId_correctIdIsSetAndReturned() {
        Squad squad =  helperMethod();
        squad.setId(1);
        assertEquals(1, squad.getId());
    }

    public Squad helperMethod(){
        return new Squad( "discipline", "indiscipline");
    }
}