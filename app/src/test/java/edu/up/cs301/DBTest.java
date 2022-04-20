package edu.up.cs301;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.db.BoxObj;
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

public class DBTest {
    @Before
    public void setUp() throws Exception{}

    @After
    public void tearDown() throws Exception{}

    /**
     * Testing DBGameState
     */

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

    /*
    testGameStateEquals() tests the GameState equals() method when input the same and different
    also testing if 2 separate GameState objects have same values
     */
    @Test
    public void testGameStateEquals(){
        DBGameState gameState = new DBGameState(LineDirection.HORIZONTAL, 1, 2);
        DBGameState gameState2 = new DBGameState(LineDirection.HORIZONTAL, 1, 2);
        assertEquals(true, gameState.equals(gameState));
        assertEquals(false, gameState.equals(gameState2));
    }

    /*
    testGameStateToString() tests that the toString() method in GameState is what we expect in the correct format
     */
    @Test
    public void testGameStateToString() {
        DBGameState gameState = new DBGameState(LineDirection.HORIZONTAL, 1, 2);
        assertEquals("direction: HORIZONTAL, row: 1, column: 2", gameState.toString());
    }

    /**
     * Testing BoxObj
     */

    /*
    testBoxObjCtor() tests the BoxObj ctor works properly while also testing getter methods
     */
    @Test
    public void testBoxObjCtor(){
        BoxObj box = new BoxObj(true, true, true, false);
        assertEquals(true, box.top());
        assertEquals(true, box.bottom());
        assertEquals(true, box.left());
        assertEquals(false, box.right());
    }

    /*
    testBoxObjLineCount() tests the lineCount() method. It should work with complete and incomplete boxes
     */
    @Test
    public void testBoxObjLineCount(){
        BoxObj box = new BoxObj(true, true, true, false);
        BoxObj box2 = new BoxObj(true, true, true, true);
        BoxObj box3 = new BoxObj(false, false, false, false);
        assertEquals(3, box.lineCount());
        assertEquals(4, box2.lineCount());
        assertEquals(0, box3.lineCount());
    }
}