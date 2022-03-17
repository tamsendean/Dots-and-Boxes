package edu.up.cs301.db;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.up.cs301.game.R;


public class DBMainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button testButton = findViewById(R.id.testButton);

		testButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditText text = findViewById(R.id.editText);
				text.setText(" ");
				String testTxt = ""; // init text displays as blank

				DBGameState firstInstance = new DBGameState();
				DBGameState firstCopy = new DBGameState(firstInstance);

				// methods for first instance
				firstInstance.prepareGame(); // gameState methods still in progress
				firstInstance.drawLine();   // most of these are dummy methods
				firstInstance.mainMenu();
				firstInstance.rules();
				firstInstance.restart();
				firstInstance.quit();

				DBGameState secondInstance = new DBGameState();
				DBGameState secondCopy = new DBGameState(secondInstance);

				testTxt += "Copy 1: " + firstCopy.toString() + "\n"; // display gameState toString()
				testTxt += "Copy 2: " + secondCopy.toString() + "\n";

				if (firstCopy.toString().equals(secondCopy.toString())) { // check if same
					testTxt += "\nCopies are equal! :)";
				} else {
					testTxt += "\nCopies are not equal. :(";
				}
				text.setText(testTxt); // display text

			}
		});
	}

}