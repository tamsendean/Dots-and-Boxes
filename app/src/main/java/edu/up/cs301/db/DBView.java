package edu.up.cs301.db;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import edu.up.cs301.game.R;

public class DBView extends View implements Observer {
    protected static final float radius = (float) 14 / 824;
    protected static final float start = (float) 6 / 824;
    protected static final float add1 = (float) 18 / 824;
    protected static final float add2 = (float) 2 / 824;
    protected static final float add3 = (float) 14 / 824;
    protected static final float add4 = (float) 141 / 824;
    protected static final float add5 = (float) 159 / 824;
    protected static final float add6 = (float) 9 / 824;

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
        float add1 = DBView.add1 * min;
        float add2 = DBView.add2 * min;
        float add4 = DBView.add4 * min;
        float add5 = DBView.add5 * min;
        float add6 = DBView.add6 * min;

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
                canvas.drawRect(start + add5 * j + add1, start + add5 * i
                        + add2, start + add5 * (j + 1), start + add5 * i + add1
                        - add2, paint);

                GameState vertical = new GameState(LineDirection.VERTICAL, j, i);
                if (game.lineChecked(vertical)) {
                    if (game.getPlayerLine(vertical) == 1)
                        paint.setColor(playerColors[0]);
                    else
                        paint.setColor(playerColors[1]);
                } else {
                    paint.setColor(0xFFFFFFFF);
                }
                canvas.drawRect(start + add5 * i + add2, start + add5 * j
                        + add1, start + add5 * i + add1 - add2, start + add5
                        * (j + 1), paint);
            }
        }

        //paint boxes
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                paint.setColor(game.getPlayerBox(j, i) == null ? Color.TRANSPARENT : playerColors[Player.indexIn(game.getPlayerBox(j, i), game.getPlayers())]);
                canvas.drawRect(start + add5 * i + add1 + add2, start
                        + add5 * j + add1 + add2, start + add5 * i + add1
                        + add4 - add2, start + add5 * j + add1 + add4
                        - add2, paint);
            }
        }

        //paint points
        paint.setColor(Color.BLACK);
        for (int i = 0; i < game.getHeight() + 1; i++) {
            for (int j = 0; j < game.getWidth() + 1; j++) {
                canvas.drawCircle(start + add6 + j * add5 + 1, start + add6 + i * add5 + 1,
                        radius, paint);
            }
        }

        invalidate();
    }

    private void receiveInput(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return;

        float touchX = event.getX();
        float touchY = event.getY();
        int min = Math.min(getWidth(), getHeight());
        float start = DBView.start * min;
        float add1 = DBView.add1 * min;
        float add2 = DBView.add2 * min;
        float add3 = DBView.add3 * min;
        float add5 = DBView.add5 * min;
        int d = -1, a = -1, b = -1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if ((start + add5 * j + add1 - add3) <= touchX
                        && touchX <= (start + add5 * (j + 1) + add3)
                        && touchY >= start + add5 * i + add2 - add3
                        && touchY <= start + add5 * i + add1 - add2 + add3) {
                    d = 0;
                    a = i;
                    b = j;
                }
                if (start + add5 * i + add2 - add3 <= touchX
                        && touchX <= start + add5 * i + add1 - add2 + add3
                        && touchY >= start + add5 * j + add1 - add3
                        && touchY <= start + add5 * (j + 1) + add3) {
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
            try {
                ((DBPlayer) game.currentPlayer()).add(move);
            } catch (Exception e) {
                Log.e("DBView", e.toString());
            }
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
