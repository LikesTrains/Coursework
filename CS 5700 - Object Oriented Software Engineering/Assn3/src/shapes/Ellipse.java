package shapes;

import java.awt.Graphics;

/**
 * Ellipse
 *
 * This class represents ellipse objects that can be moved and scales.  Users of a ellipse can also get its area.
 *
 */
public class Ellipse extends Shape {
    private double radius1, radius2;

    /**
     * Constructor with x-y Location for center
     *
     * @param x                 The x-location of the center of the ellipse -- must be a valid double
     * @param y                 The y-location of the center of the ellipse
     * @param radius            The radius of the ellipse -- must be greater or equal to zero.
     * @throws ShapeException   The exception thrown if the x, y, or z are not valid
     */
    public Ellipse(double x, double y, double radius1, double radius2) throws ShapeException {
    	this(new Point(x, y), radius1, radius2);
    }

    /**
     * Constructor with a Point for center
     *
     * @param center            The x-location of the center of the ellipse -- must be a valid point
     * @param radius            The radius of the ellipse -- must be greater or equal to zero.
     * @throws ShapeException   The exception thrown if the x, y, or z are not valid
     */
    public Ellipse(Point center, double radius1, double radius2) throws ShapeException {
        Validator.validatePositiveDouble(radius1, "Invalid radius");
        Validator.validatePositiveDouble(radius2, "Invalid radius");
        Validator.validatePoint(center, "Invalid center point");

        this.points.add(center);
        this.radius1 = radius1;
        this.radius2 = radius2;
    }

    /**
     * @return  The center of the ellipse
     */
    public Point getCenter() { return this.points.get(0); }

    /**
     * @return  The radius of the ellipse
     */
    public double getRadius1() { return radius1; }
    
    /**
     * @return  The radius of the ellipse
     */
    public double getRadius2() { return radius2; }

    /**
     * Scale the ellipse
     *
     * @param scaleFactor       a non-negative double that represents the percentage to scale the ellipse.
     *                          0>= and <1 to shrink.
     *                          >1 to grow.
     * @throws ShapeException   Exception thrown if the scale factor is not valid
     */
    public void scale(double scaleFactor) throws ShapeException {
        Validator.validatePositiveDouble(scaleFactor, "Invalid scale factor");
        radius1 *= scaleFactor;
        radius2 *= scaleFactor;
    }

    /**
     * @return  The area of the ellipse.
     */
    public double getArea() {
        return Math.PI * radius1*radius2;
    }
    @Override
    public String toString() {
    	String base = super.toString().concat(",");
    	return base.concat(String.valueOf(this.radius1)).concat(",").concat(String.valueOf(this.radius2));
    }

	@Override
	public void draw(Graphics g) {
		g.drawOval((int)(this.points.get(0).getX()-this.radius1), (int)(this.points.get(0).getY()-this.radius2),(int) this.radius1*2, (int)this.radius2*2);
		
	}
}