package edu.up.cs301.GameFramework.utilities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Map;

import edu.up.cs301.GameFramework.infoMessage.PlayerInfo;
import edu.up.cs301.GameFramework.players.Player;
import edu.up.cs301.db.DBMainActivity;
import edu.up.cs301.db.DBPlayer;
import edu.up.cs301.db.DumbComputerPlayer;
import edu.up.cs301.game.R;

public class MainMenu extends AppCompatActivity {
    Button humanGame;
    Button dumbGame;
    Button avgGame;
    Button smartGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        humanGame = (Button) findViewById(R.id.humanBtn);
        humanGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, DBMainActivity.class);
                i.putExtra("id", 0);
                setContentView(R.layout.activity_main);
                startActivity(i);
            }
        });
        dumbGame = (Button) findViewById(R.id.dumbBtn);
        dumbGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(MainMenu.this)
                                .setTitle("Select Player 1: ")
                                .setPositiveButton("Computer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent in = new Intent(MainMenu.this, DBMainActivity.class);
                                        in.putExtra("id", 10);
                                        setContentView(R.layout.activity_main);
                                        startActivity(in);
                                    }
                                })
                                .setNegativeButton("Human", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent in = new Intent(MainMenu.this, DBMainActivity.class);
                                        in.putExtra("id", 01);
                                        setContentView(R.layout.activity_main);
                                        startActivity(in);
                                    }
                                }).show();
                    }
                });
            }
        });
        avgGame = (Button) findViewById(R.id.avgBtn);
        avgGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(MainMenu.this)
                                .setTitle("Select Player 1: ")
                                .setPositiveButton("Computer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent in = new Intent(MainMenu.this, DBMainActivity.class);
                                        in.putExtra("id", 20);
                                        setContentView(R.layout.activity_main);
                                        startActivity(in);
                                    }
                                })
                                .setNegativeButton("Human", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent in = new Intent(MainMenu.this, DBMainActivity.class);
                                        in.putExtra("id", 02);
                                        setContentView(R.layout.activity_main);
                                        startActivity(in);
                                    }
                                }).show();
                    }
                });
            }
        });
        smartGame = (Button) findViewById(R.id.smartBtn);
        smartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(MainMenu.this)
                                .setTitle("Select Player 1: ")
                                .setPositiveButton("Computer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent in = new Intent(MainMenu.this, DBMainActivity.class);
                                        in.putExtra("id", 30);
                                        setContentView(R.layout.activity_main);
                                        startActivity(in);
                                    }
                                })
                                .setNegativeButton("Human", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent in = new Intent(MainMenu.this, DBMainActivity.class);
                                        in.putExtra("id", 03);
                                        setContentView(R.layout.activity_main);
                                        startActivity(in);
                                    }
                                }).show();
                    }
                });
            }
        });
    }
}
