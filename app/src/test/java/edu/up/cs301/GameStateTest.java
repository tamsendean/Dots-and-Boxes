package edu.up.cs301;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.db.DBGameState;
import edu.up.cs301.db.LineDirection;

/**
 * Unit Testing for the Dots and Boxes Game
 *
 * @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */

public class GameStateTest {
    @Before
    public void setUp() throws Exception{}

    @After
    public void tearDown() throws Exception{}

    /*
    testGameStateCtor() tests that the GameState ctor is working properly aswell as
    testing the getter methods in the GameState class
     */
    @Test
    public void testGameStateCtor(){
        DBGameState gameState = new DBGameState(LineDirection.HORIZONTAL, 3, 4);
        assertEquals(LineDirection.HORIZONTAL, gameState.direction());
        assertEquals(3, gameState.row());
        assertEquals(4, gameState.column());
    }
}