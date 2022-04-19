package edu.up.cs301.db;

import android.app.Activity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.Map;

import edu.up.cs301.GameFramework.infoMessage.PlayerInfo;
import edu.up.cs301.GameFramework.players.Player;
import edu.up.cs301.game.R;


public class DBMainActivity extends Activity implements PlayerInfo {
	protected DBView gameView;
	protected TextView player1, player2, player1score, player2score;
	Player[] players;
	Integer[] playerPoints = new Integer[]{0, 0};
	Player currentPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gameView = (DBView) findViewById(R.id.gameView);
		gameView.setCurrentPlayer(this);

		player1 = (TextView) findViewById(R.id.player1name);
		player2 = (TextView) findViewById(R.id.player2name);
		player1score = (TextView) findViewById(R.id.player1score);
		player2score = (TextView) findViewById(R.id.player2score);

		players = new Player[]{new DBPlayer("Human"), new AverageComputerPlayer("Computer")};
		startGame(players);
	}

	//this starts the game
	private void startGame(Player[] players) {
		gameView.startGame(players);
		updateState();
	}

	// this is a thread to update the player score
	public void updateState() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				player1score.setText("Score: " + playerPoints[0]);
				player2score.setText("Score: " + playerPoints[1]);
			}
		});
	}

	// creates player
	@Override
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
		updateState();
	}

	// sets the initial score of the players
	@Override
	public void setScore(Map<Player, Integer> playerBoxCount) {
		playerPoints[0] = (playerBoxCount.get(players[0]));
		playerPoints[1] = (playerBoxCount.get(players[1]));
		updateState();
	}

	// this is the message window that displays who won and if they wanna play again
	@Override
	public void setWinner(final Player winner) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(DBMainActivity.this)
						.setTitle("Dots And Boxes")
						.setMessage(winner.getName() + " Wins!")
						.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								recreate();
							}
						})
						.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
							}
						}).show();
			}
		});
	}

	// this is our game menu option
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// this is the menu where we have popup messages displayed
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.menu_help) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					new AlertDialog.Builder(DBMainActivity.this)
							.setTitle("Dots And Boxes Rules")
							.setMessage("Click on a line to move. When a box of lines is formed by a player, the player receives a point and they may go again.").show();
				}
			});
		}

		//this exits the game
		if (id == R.id.exit_game) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					new AlertDialog.Builder(DBMainActivity.this)
							.setMessage("Goodbye!").show();
				}
			});
			finish();
		}

		// loads the initial start of the game
		if (id == R.id.load_game) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					new AlertDialog.Builder(DBMainActivity.this)
							.setTitle("Dots And Boxes")
							.setMessage("New Game Against: ")
							.setPositiveButton("Dumb Computer", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									new AlertDialog.Builder(DBMainActivity.this)
											.setTitle("Select Player 1: ")
											.setPositiveButton("Computer", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialogInterface, int i) {
													players = new Player[]{new DumbComputerPlayer("Computer"),
															new DBPlayer("Human")};
													startGame(players);

													player1.setText("Computer");
													player2.setText("Human");
												}
											})
											.setNegativeButton("Human", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialogInterface, int i) {
													players = new Player[]{new DBPlayer("Human"),
															new DumbComputerPlayer("Computer")};
													startGame(players);

													player1.setText("Human");
													player2.setText("Computer");
												}
											}).show();
								}
							})
							.setNeutralButton("Smart Computer", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialogInterface, int i) {
											new AlertDialog.Builder(DBMainActivity.this)
													.setTitle("Select First Player: ")
													.setPositiveButton("Computer", new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialogInterface, int i) {
															players = new Player[]{new SmartComputerPlayer("Computer"),
																	new DBPlayer("Human")};
															startGame(players);

															player1.setText("Computer");
															player2.setText("Human");
														}
													})
													.setNegativeButton("Human", new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialogInterface, int i) {
															players = new Player[]{new DBPlayer("Human"),
																	new SmartComputerPlayer("Computer")};
															startGame(players);

															player1.setText("Human");
															player2.setText("Computer");
														}
													}).show();
										}
									})
							.setNegativeButton("Average Computer", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									new AlertDialog.Builder(DBMainActivity.this)
											.setTitle("Select First Player: ")
											.setPositiveButton("Computer", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialogInterface, int i) {
													players = new Player[]{new AverageComputerPlayer("Computer"),
															new DBPlayer("Human")};
													startGame(players);

													player1.setText("Computer");
													player2.setText("Human");
												}
											})
											.setNegativeButton("Human", new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialogInterface, int i) {
													players = new Player[]{new DBPlayer("Human"),
															new AverageComputerPlayer("Computer")};
													startGame(players);

													player1.setText("Human");
													player2.setText("Computer");
												}
											}).show();
								}
							}).show();
				}
			});
		}

		return super.onOptionsItemSelected(item);
			}
		}