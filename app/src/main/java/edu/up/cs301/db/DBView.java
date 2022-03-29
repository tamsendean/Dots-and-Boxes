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

import edu.up.cs301.game.R;

public class DBView extends View implements Observer {
    protected static final float radius = (float) 14 / 824;
    protected static final float start = (float) 6 / 824;
    protected static final float dot1 = (float) 18 / 824;
    protected static final float dot2 = (float) 2 / 824;
    protected static final float dot3 = (float) 14 / 824;
    protected static final float dot4 = (float) 141 / 824;
    protected static final float dot5 = (float) 159 / 824;
    protected static final float dot6 = (float) 9 / 824;

    protected final int[] playerColors;
    protected DBAction game;
    protected GameState move;
    protected Paint paint;
    protected PlayerInfo playersState;

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

    public void setPlayersState(PlayerInfo playersState) {
        this.playersState = playersState;
    }

    public void startGame(Player[] players) {
        game = new DBAction(5, 5, players);
        game.addObserver(this);
        new Thread() {
            @Override
            public void run() {
                game.start();
            }
        }.start();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0x00FFFFFF);
        int min = Math.min(getWidth(), getHeight());
        float radius = DBView.radius * min;
        float start = DBView.start * min;
        float dot1 = DBView.dot1 * min;
        float dot2 = DBView.dot2 * min;
        float dot4 = DBView.dot4 * min;
        float dot5 = DBView.dot5 * min;
        float dot6 = DBView.dot6 * min;

        //paint lines
        paint.setColor(0xFF000000);
        for (int i = 0; i < game.getHeight() + 1; i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                GameState horizontal = new GameState(LineDirection.HORIZONTAL, i, j);
                if (game.lineChecked(horizontal)) {
                    if (game.getPlayerLine(horizontal) == 1)
                        paint.setColor(playerColors[0]);
                    else
                        paint.setColor(playerColors[1]);
                } else {
                    paint.setColor(0xFFFFFFFF);
                }
                final float bottom = start + dot5 * i + dot1
                        - dot2;
                canvas.drawRect(start + dot5 * j + dot1, start + dot5 * i
                        + dot2, start + dot5 * (j + 1), bottom, paint);

                GameState vertical = new GameState(LineDirection.VERTICAL, j, i);
                if (game.lineChecked(vertical)) {
                    if (game.getPlayerLine(vertical) == 1)
                        paint.setColor(playerColors[0]);
                    else
                        paint.setColor(playerColors[1]);
                } else {
                    paint.setColor(0xFFFFFFFF);
                }
                canvas.drawRect(start + dot5 * i + dot2, start + dot5 * j
                        + dot1, bottom, start + dot5
                        * (j + 1), paint);
            }
        }

        //paint boxes
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                paint.setColor(game.getPlayerBox(j, i) == null ? Color.TRANSPARENT : playerColors[Player.indexIn(game.getPlayerBox(j, i), game.getPlayers())]);
                canvas.drawRect(start + dot5 * i + dot1 + dot2, start
                        + dot5 * j + dot1 + dot2, start + dot5 * i + dot1
                        + dot4 - dot2, start + dot5 * j + dot1 + dot4
                        - dot2, paint);
            }
        }

        //paint points
        paint.setColor(Color.BLACK);
        for (int i = 0; i < game.getHeight() + 1; i++) {
            for (int j = 0; j < game.getWidth() + 1; j++) {
                canvas.drawCircle(start + dot6 + j * dot5 + 1, start + dot6 + i * dot5 + 1,
                        radius, paint);
            }
        }

        invalidate();
    }

    private void receiveInput(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return;

        float XClicked = event.getX();
        float YClicked = event.getY();
        int min = Math.min(getWidth(), getHeight());
        float start = DBView.start * min;
        float dot1 = DBView.dot1 * min;
        float dot2 = DBView.dot2 * min;
        float dot3 = DBView.dot3 * min;
        float dot5 = DBView.dot5 * min;
        int d = -1, a = -1, b = -1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                final float v = start + dot5 * i + dot2 - dot3;
                final float v1 = start + dot5 * j + dot1 - dot3;
                final float v2 = start + dot5 * i + dot1 - dot2 + dot3;
                if (v1 <= XClicked
                        && XClicked <= (start + dot5 * (j + 1) + dot3)
                        && YClicked >= v
                        && YClicked <= v2) {
                    d = 0;
                    a = i;
                    b = j;
                }
                if (v <= XClicked
                        && XClicked <= v2
                        && YClicked >= v1
                        && YClicked <= start + dot5 * (j + 1) + dot3) {
                    d = 1;
                    a = j;
                    b = i;
                }
            }
        }

        if ((a != -1) && (b != -1)) {
            LineDirection direction;
            if (d == 0)
                direction = LineDirection.HORIZONTAL;
            else
                direction = LineDirection.VERTICAL;
            move = new GameState(direction, a, b);
            ((DBPlayer) game.currentPlayer()).add(move);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        playersState.setCurrentPlayer(game.currentPlayer());
        Map<Player, Integer> playerBoxCount = new HashMap<>();
        for (Player player : game.getPlayers()) {
            playerBoxCount.put(player, game.getPlayerBoxCount(player));
        }
        playersState.setScore(playerBoxCount);

        Player winner = game.getWinner();
        if (winner != null) {
            playersState.setWinner(winner);
        }
    }
}