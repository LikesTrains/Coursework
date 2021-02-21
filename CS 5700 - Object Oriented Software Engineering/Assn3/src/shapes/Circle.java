package shapes;

/**
 * Circle
 *
 * This class represents circle objects that can be moved and scales.  Users of a circle can also get its area.
 *
 */
public class Circle extends Ellipse {
	/**
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @throws ShapeException
	 */
	public Circle(double x, double y, double radius) throws ShapeException {
		this(new Point(x, y), radius);
	}
	/**
	 * 
	 * @param center
	 * @param radius
	 * @throws ShapeException
	 */
	public Circle(Point center, double radius) throws ShapeException{
		super(center, radius, radius);
	}
	/**
	 * 
	 * @return
	 */
	public double getRadius() {
		return this.getRadius1();
	}
    @Override
    public String toString() {
    	String base = "Circle"+ ","+ this.getPoints().get(0).getX() + ","+this.getPoints().get(0).getY() + ",";
    	return base.concat(String.valueOf(this.getRadius1()));
    }
}
