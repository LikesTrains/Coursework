package shapes;

import java.awt.Graphics;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class Shape {
	protected ArrayList<Point> points = new ArrayList<Point>();
	
	public ArrayList<Point> getPoints() {
		return this.points;
	}
	public double getArea() {
		return 0;
	}
	public void move(double deltaX, double deltaY) throws ShapeException {	
		Validator.validateDouble(deltaX, "Invalid Double");
		Validator.validateDouble(deltaY, "Invalid Double");
		
		for (Point pt :points) {
			pt.moveX(deltaX);
			pt.moveY(deltaY);
		}
	}
	@Override
	public String toString() {
		String returnable = "";
		String end = "";
		// attach name of shape
		returnable = returnable.concat(this.getClass().getSimpleName()).concat(",");
		// attach coordinates of final point
		end = end.concat(String.valueOf(this.points.get(this.points.size()-1).getX())).concat(",").concat(String.valueOf(this.points.get(this.points.size()-1).getY()));
		// attach coordinates of first to second to last point
		for (int i=0; i<this.points.size()-1;i++) {
			returnable = returnable.concat(String.valueOf(this.points.get(i).getX())).concat(",").concat(String.valueOf(this.points.get(i).getY())).concat(",");
		}
		returnable = returnable.concat(end);
		return returnable;
	}
	
	public void outputShape(OutputStream os) throws IOException {
		PrintWriter pw = new PrintWriter(os);
		String out = this.toString();
		pw.print(out);
		pw.close();
	}
	public boolean equals(Shape sh) {
		if(sh==null)
			return false;
		return this.toString().equals(sh.toString());
	}
	public abstract void draw(Graphics g);
}
