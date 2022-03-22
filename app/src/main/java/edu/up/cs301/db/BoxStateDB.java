package edu.up.cs301.counter;

import edu.up.cs301.GameFramework.infoMessage.DBGameState;


/**
 * This contains the state for the Counter game. The state consist of simply
 * the value of the counter.
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 */
public class BoxStateDB extends DBGameState {
	boolean top = false;
	boolean bottom = false;
	boolean left = false;
	boolean right = false;
	char c = ' ';
	
	// to satisfy Serializable interface
	private static final long serialVersionUID = 7737393762469851826L;
	
	// the value of the counter
	private int counter;
	
	/**
	 * constructor, initializing the counter value from the parameter
	 * 
	 * @param counterVal
	 * 		the value to which the counter's value should be initialized
	 */
	public BoxStateDB(boolean Top, boolean Bottom, boolean Left, boolean Right, char C) {
		setTop(top);
		setBottom(bottom);
		setLeft(left);
		setRight(right);
		setC(c);
	}
	public BoxStateDB(int counterVal) {
		counter = counterVal;
	}
	
	/**
	 * copy constructor; makes a copy of the original object
	 * 
	 * @param orig
	 * 		the object from which the copy should be made
	 */
	public BoxStateDB(BoxStateDB orig) {
		// set the counter to that of the original
		this.counter = orig.counter;
	}

	/**
	 * getter method for the counter
	 * 
	 * @return
	 * 		the value of the counter
	 */
	public int getCounter() {
		return counter;
	}
	
	/**
	 * setter method for the counter
	 * 
	 * @param counter
	 * 		the value to which the counter should be set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}




		public boolean isTop() {
			return this.top;
		}

		public void setTop(boolean top) {
			this.top = top;
		}

		public boolean isBottom() {
			return this.bottom;
		}

		public void setBottom(boolean bottom) {
			this.bottom = bottom;
		}

		public boolean isLeft() {
			return this.left;
		}

		public void setLeft(boolean left) {
			this.left = left;
		}

		public boolean isRight() {
			return this.right;
		}

		public void setRight(boolean right) {
			this.right = right;
		}

		public char getC() {
			return this.c;
		}

		public void setC(char c) {
			this.c = c;
		}


	}
