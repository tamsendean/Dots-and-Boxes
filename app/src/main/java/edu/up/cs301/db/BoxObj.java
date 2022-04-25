package edu.up.cs301.db;

/**
 * BoxObj class for the Dots and Boxes Game
 *
 * @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */

public class BoxObj {
	boolean top;
	boolean bottom;
	boolean left;
	boolean right;
	boolean checked;

	/**
	 * Ctor of BoxObj creates a box if all sides are checked
	 * @param t - boolean if top is checked
	 * @param b - boolean if bottom is checked
	 * @param l - boolean if left is checked
	 * @param r - boolean if right is checked
	 * @return checked - boolean: check if all the above are true
	 */
	public BoxObj(boolean t, boolean b, boolean l, boolean r) {
		this.top = t;
		this.bottom = b;
		this.left = l;
		this.right = r;
		this.checked = (l && t && r && b);
	}

	/**
	 * getter methods for side variables
	 */
	public boolean top(){return this.top;}
	public boolean bottom(){return this.bottom;}
	public boolean right(){return this.right;}
	public boolean left(){return this.left;}

	/**
	 * lineCount() counts the borders for our squares
	 * @return count - returns the number of the final count
	 */
	public int lineCount() {
		int count = 0;

		if (this.left)
			count++;
		if (this.right)
			count++;
		if (this.top)
			count++;
		if (this.bottom)
			count++;

		return count;
	}
}