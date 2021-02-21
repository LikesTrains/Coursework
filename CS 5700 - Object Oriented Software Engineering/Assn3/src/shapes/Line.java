package shapes;

import java.awt.Graphics;

/**
 *
 *  Line
 *
 *  This class represents line objects that can be moved.  Users of a line can also get its length and slope.
 *
 */
public class Line extends Shape {
    
    private void validateLine(Point point1, Point point2, String errorMessage) throws ShapeException {
    	if (Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2))<0.00000001)
    		throw new ShapeException(errorMessage);
    }

    /**
     * Constructor based on x-y Locations
     * @param x1                The x-location of first point -- must be a valid double.
     * @param y1                The y-location of first point -- must be a valid double.
     * @param x2                The x-location of second point -- must be a valid double.
     * @param y2                The y-location of second point -- must be a valid double.
     * @throws ShapeException   Exception throw if any parameter is invalid
     */
    public Line(double x1, double y1, double x2, double y2) throws ShapeException {
        this(new Point(x1, y1),new Point(x2, y2));
    }

    /**
     *
     * @param point1            The first point -- must not be null
     * @param point2            The second point -- must not be null
     * @throws ShapeException   Exception throw if any parameter is invalid
     */
    public Line(Point point1, Point point2) throws ShapeException {
    	Validator.validatePoint(point1, "Invalid Point");
    	Validator.validatePoint(point2, "Invalid Point");
    	
    	validateLine(point1, point2, "A line must have a length > 0");    	
        this.points.add(point1);
        this.points.add(point2);
        
    }


    /**
     * @return  The length of the line
     */
    public double computeLength() {
        return Math.sqrt(Math.pow(points.get(1).getX() - points.get(0).getX(), 2) +
                         Math.pow(points.get(1).getY() - points.get(0).getY(), 2));
    }

    /**
     * @return  The slope of the line
     */
    public double computeSlope() {
        return (points.get(1).getY() - points.get(0).getY())/(points.get(1).getX() - points.get(0).getX());
    }

	@Override
	public void draw(Graphics g) {
		g.drawLine((int)this.points.get(0).getX(), (int)this.points.get(0).getY(), (int)this.points.get(1).getX(), (int)this.points.get(1).getY());
	} 
    
}
