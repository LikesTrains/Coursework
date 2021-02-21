package shapes;

import java.awt.Graphics;

/**
 * Point
 *
 * This class represents point objects that can be moved and copied
 */
public class Point extends Shape {
    
	
	private double x;
    private double y;
    
    public Point() throws ShapeException {
    	this(0,0);
    }

    /**
     * Constructor
     *
     * @param x                 The x-location of the point -- must be a valid double
     * @param y                 The y-location of the point -- must be a valid double
     * @throws ShapeException   Exception throw if any parameter is invalid
     */
    public Point(double x, double y) throws ShapeException {
        Validator.validateDouble(x, "Invalid x-location");
        Validator.validateDouble(y, "Invalid y-location");
        this.x = x;
        this.y = y;
        this.points.add(this);
    }
    /**
     * @return  The x-location of the point
     */
    public double getX() { return x; }

    /**
     * @return  The y-location of the point
     */
    public double getY() { return y; }

    /**
     * Move the point in the x direction
     *
     * @param deltaX            The delta amount to move the point -- must be a valid double
     * @throws ShapeException   Exception thrown if the parameter is invalid
     */
    protected void moveX(double deltaX) throws ShapeException {
        x += deltaX;
    }

    /**
     * Move the point in the y direction
     *
     * @param deltaY            The delta amount to move the point -- must be a valid double
     * @throws ShapeException   Exception thrown if the parameter is invalid
     */
    protected void moveY(double deltaY) throws ShapeException {
        y += deltaY;
    }

    /**
     * Copy the point
     * @return                  A new point with same x and y locations
     * @throws ShapeException   Should never thrown because the current x and y are valid
     */
    public Point copy() throws ShapeException {
        return new Point(x, y);
    }

	@Override
	public void draw(Graphics g) {
		g.drawLine((int)this.x, (int)this.y, (int)this.x, (int)this.y);		
	}

}
