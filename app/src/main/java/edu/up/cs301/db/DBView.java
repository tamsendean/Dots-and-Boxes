package edu.up.cs301.db;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import edu.up.cs301.GameFramework.infoMessage.PlayerInfo;
import edu.up.cs301.GameFramework.players.Player;
import edu.up.cs301.game.R;

/**
 * DBView- this class draws our board and other features and interacts
 * with the DB Main Activity class.
 *
 * @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */

public class DBView extends View implements Observer {
    protected static final float radius = (float) 0.02;
    protected static final float start = (float) 0.01;
    protected static final float lineWidth = (float) 0.02;
    protected static final float fillSize = (float) 0.002;
    protected static final float clickXPos = (float) 0.017;
    protected static final float clickYPos = (float) 0.17;
    protected static final float gridSize = (float) 0.19;
    protected static final float dotsPos = (float) 0.01;

    protected final int[] playerColors;
    protected DBLocalGame game;
    protected DBGameState move;
    protected Paint paint;
    protected PlayerInfo currentPlayer;

    /**
     * Ctor of DBView sets onTouchListener, receives motion events
     * @param context
     * @param attributeSet
     */
    public DBView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        paint = new Paint();

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                receiveInput(event);
                return false;
            }
        });

        playerColors = new int[]{getResources().getColor(R.color.colorP1),
                getResources().getColor(R.color.colorP2)};
    }

    /**
     * setCurrentPlayer() gets/sets current player
     * @param currentPlayer - info on current player
     */
    public void setCurrentPlayer(PlayerInfo currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * startGame() creates new game of width and height with 2 players, adds observer to it
     * which will update the info automatically
     * @param players - players in game
     */
    public void startGame(Player[] players) {
        game = new DBLocalGame(5, 5, players);
        game.addObserver(this);
        new Thread() {
            @Override
            public void run() {
                game.start();
            }
        }.start();
        postInvalidate();
    }

    /**
     * onDraw() allows our surface view to be drawn
     * @param canvas - game board
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        int pos = Math.min(getWidth(), getHeight());
        float radius = DBView.radius * pos;
        float start = DBView.start * pos;
        float lineWidth = DBView.lineWidth * pos;
        float fillSize = DBView.fillSize * pos;
        float clickYPos = DBView.clickYPos * pos;
        float gridSize = DBView.gridSize * pos;
        float dotsPos = DBView.dotsPos * pos;

        /**
         External Citation
         Date: 22 March, 2022
         Problem: Trying to draw a grid of squares
         Resource:
         https://stackoverflow.com/questions/48724534/trying-to-adapt-an-old-program-to-draw-a-grid-of-squares
         Solution: Although this person's code had issues, I used their ideas of separator ratios and
         available space from floor to ceiling in order to draw a 6x6 grid with dots at each intersection.
         */
        for (int i = 0; i < game.getHeight() + 1; i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                DBGameState horizontal = new DBGameState(LineDirection.HORIZONTAL, i, j);
                if (game.lineChecked(horizontal)) {
                    if (game.getPlayerLine(horizontal) == 1)
                        paint.setColor(playerColors[0]);
                    else
                        paint.setColor(playerColors[1]);
                } else {
                    paint.setColor(0xFFF3F3F3);
                }
                final float bottom = start + gridSize * i + lineWidth
                        - fillSize;
                canvas.drawRect(start + gridSize * j + lineWidth, start + gridSize * i
                        + fillSize, start + gridSize * (j + 1), bottom, paint);

                DBGameState vertical = new DBGameState(LineDirection.VERTICAL, j, i);
                if (game.lineChecked(vertical)) {
                    if (game.getPlayerLine(vertical) == 1)
                        paint.setColor(playerColors[0]);
                    else
                        paint.setColor(playerColors[1]);
                } else {
                    paint.setColor(0xFFF3F3F3);
                }
                canvas.drawRect(start + gridSize * i + fillSize, start + gridSize * j
                        + lineWidth, bottom, start + gridSize
                        * (j + 1), paint);
            }
        }

        //paint boxes
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                paint.setColor(game.getPlayerBox(j, i) == null ? Color.TRANSPARENT : playerColors[Player.playerNum(game.getPlayerBox(j, i), game.getPlayers())]);
                canvas.drawRect(start + gridSize * i + lineWidth + fillSize, start
                        + gridSize * j + lineWidth + fillSize, start + gridSize * i + lineWidth
                        + clickYPos - fillSize, start + gridSize * j + lineWidth + clickYPos
                        - fillSize, paint);
            }
        }

        //paint points
        paint.setColor(Color.BLACK);
        for (int i = 0; i < game.getHeight() + 1; i++) {
            for (int j = 0; j < game.getWidth() + 1; j++) {
                canvas.drawCircle(start + dotsPos + j * gridSize + 1, start + dotsPos + i * gridSize + 1,
                        radius, paint);
            }
        }

        invalidate();
    }

    /**
     * receiveInput() gets input from user clicking board
     * @param event - MotionEvent of clicking lines
     */
    private void receiveInput(MotionEvent event) {
        /**
         External Citation
         Date: 22 March, 2022
         Problem: Clicking on line to create motion event
         Resource: https://stackoverflow.com/questions/3476779/how-to-get-the-touch-position-in-android/3476840#3476840
         Solution: I got help from these answers to learn about how to implement motion events in the grid.
         */
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return;

        float XClicked = event.getX();
        float YClicked = event.getY();
        int pos = Math.min(getWidth(), getHeight());
        float start = DBView.start * pos;
        float lineWidth = DBView.lineWidth * pos;
        float fillSize = DBView.fillSize * pos;
        float clickXPos = DBView.clickXPos * pos;
        float gridSize = DBView.gridSize * pos;
        int x = -1, y = -1, z = -1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                final float v = start + gridSize * i + fillSize - clickXPos;
                final float v1 = start + gridSize * j + lineWidth - clickXPos;
                final float v2 = start + gridSize * i + lineWidth - fillSize + clickXPos;
                if (v1 <= XClicked
                        && XClicked <= (start + gridSize * (j + 1) + clickXPos)
                        && YClicked >= v
                        && YClicked <= v2) {
                    x = 0;
                    y = i;
                    z = j;
                }
                if (v <= XClicked
                        && XClicked <= v2
                        && YClicked >= v1
                        && YClicked <= start + gridSize * (j + 1) + clickXPos) {
                    x = 1;
                    y = j;
                    z = i;
                }
            }
        }

        if ((y != -1) && (z != -1)) {
            LineDirection direction;
            if (x == 0)
                direction = LineDirection.HORIZONTAL;
            else
                direction = LineDirection.VERTICAL;
            move = new DBGameState(direction, y, z);
            ((DBPlayer) game.currentPlayer()).add(move);
        }
    }

    /**
     * update() updates board with score data and if player has won
     * @param observable - observable object
     * @param data - data object
     */
    @Override
    public void update(Observable observable, Object data) {
        currentPlayer.setCurrentPlayer(game.currentPlayer());
        Map<Player, Integer> playerBoxCount = new HashMap<>();
        for (Player player : game.getPlayers()) {
            playerBoxCount.put(player, game.getPlayerBoxCount(player));
        }
        currentPlayer.setScore(playerBoxCount);

        Player winner = game.getWinner();
        if (winner != null) {
            currentPlayer.setWinner(winner);
        }
    }
}