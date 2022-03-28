package edu.up.cs301.db;

public class BoxObj {
	boolean top;
	boolean bottom;
	boolean left;
	boolean right;
	boolean checked;

	BoxObj(boolean t, boolean b, boolean l, boolean r) {
		this.top = t;
		this.bottom = b;
		this.left = l;
		this.right = r;
		this.checked = (l && t && r && b);
	}
	int lineCount() {
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