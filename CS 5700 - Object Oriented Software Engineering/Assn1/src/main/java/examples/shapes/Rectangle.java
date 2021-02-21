package examples.shapes;

/**
 * Rectangle
 *
 * This class represents rectangle objects that can be moved.  Users of a rectangle can also get its width, height, and area.
 *
 */

public class Rectangle {
	
    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;
	
	private void validateRectangle(Point point1, Point point2, Point point3, Point point4, String errorMessage1, String errorMessage2) throws ShapeException {
		// This checks if the points and lines are valid. Also checks if adjacent points are in the same line
		Validator.validateDistinctEdges(new Line(point1, point2), new Line(point2, point3), errorMessage1);
		Validator.validateDistinctEdges(new Line(point2, point3), new Line(point3, point4), errorMessage1);
		Validator.validateDistinctEdges(new Line(point3, point4), new Line(point4, point1), errorMessage1);
		Validator.validateDistinctEdges(new Line(point4, point1), new Line(point1, point2), errorMessage1);
		
		// use center of mass approach to check if it's a rectangle
		double cx = (point1.getX()+point2.getX()+point3.getX()+point4.getX())/4;
		double cy = (point1.getY()+point2.getY()+point3.getY()+point4.getY())/4;
		Line l1 = new Line(point1.getX(), point1.getY(), cx, cy);
		Line l2 = new Line(point2.getX(), point2.getY(), cx, cy);
		Line l3 = new Line(point3.getX(), point3.getY(), cx, cy);
		Line l4 = new Line(point4.getX(), point4.getY(), cx, cy);
		
		if(Math.abs(l1.computeLength()-l2.computeLength())>0.001 || Math.abs(l2.computeLength()-l3.computeLength())>0.001 || Math.abs(l3.computeLength()-l4.computeLength())>0.001)
			throw new ShapeException(errorMessage2);
	}
    
    /**
    *
    * @param point1            The first point -- must not be null
    * @param point2            The second point -- must not be null
    * @param point3            The third point -- must not be null
    * @param point4            The fourth point -- must not be null
    * @throws ShapeException   Exception thrown if any parameter is invalid, adjacent points fall in the same line, or if the points do not form a rectangle
    */
    public Rectangle(Point point1, Point point2,Point point3,Point point4) 
    		throws ShapeException {
    	this(point1, point2, point3, point4, "Adjacent points must not be in the same line", 
    			"Invalid rectangle, inconsistent distance from vertex to center");
    }
    
    protected Rectangle(Point point1, Point point2,Point point3,Point point4, String errorMessage1, String errorMessage2) 
    		throws ShapeException {

        validateRectangle(point1, point2, point3, point4, errorMessage1, errorMessage2);        
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
    }
    
    /**
     * Constructor based on x-y Locations
     * @param x1                The x-location of first point -- must be a valid double.
     * @param y1                The y-location of first point -- must be a valid double.
     * @param x2                The x-location of second point -- must be a valid double.
     * @param y2                The y-location of second point -- must be a valid double.
     * @param x3                The x-location of third point -- must be a valid double.
     * @param y3                The y-location of third point -- must be a valid double.
     * @param x4                The x-location of fourth point -- must be a valid double.
     * @param y4                The y-location of fourth point -- must be a valid double.
     * @throws ShapeException   Exception thrown if any parameter is invalid, adjacent points fall in the same line, or if the points do not form a rectangle
     */
    public Rectangle(double x1, double y1, double x2, double y2, double x3, double y3,double x4, double y4) 
    		throws ShapeException {
        this(new Point(x1, y1),new Point(x2, y2),new Point(x3, y3),new Point(x4, y4));
    }    

    /**
     * @return  The first point
     */
    public Point getPoint1() { return point1; }
    
    /**
     * @return  The second point
     */
    public Point getPoint2() { return point2; }
    
    /**
     * @return  The third point
     */
    public Point getPoint3() { return point3; }
    
    /**
     * @return  The fourth point
     */
    public Point getPoint4() { return point4; }
    
    /**
     * Move a rectangle
     *
     * @param deltaX            The delta x-location by which the square should be moved -- must be a valid double
     * @param deltaY            The delta y-location by which the square should be moved -- must be a valid double
     * @throws ShapeException   Exception thrown if any parameter is invalid
     */
    public void move(double deltaX, double deltaY) 
    		throws ShapeException {
        point1.move(deltaX, deltaY);
        point2.move(deltaX, deltaY);
        point3.move(deltaX, deltaY);
        point4.move(deltaX, deltaY);
    }
    
    /**
     * @return  The area of the rectangle
     */
    public double ComputeArea(){
    	try {
    	Line l1 = new Line(point1, point2);
    	Line l2 = new Line(point1, point3);
    	Line l3 = new Line(point2, point3);
    	
    	Line final1 = null;
    	Line final2 = null;
    	
    	if(l1.computeLength()<l2.computeLength()) {
    		final1 = l1;
    		final2 = l2;
    	}
		else {
    		final1 = l2;
    		final2 = l1;
		}
    	
    	if(l3.computeLength()<final2.computeLength())
    		final2 = l3;
    	
    	Line horizontal = final1.computeSlope()<final2.computeSlope() ? final1 : final2;
    	Line vertical = final1.computeSlope()>final2.computeSlope() ? final1 : final2;
    	
    	return horizontal.computeLength()*vertical.computeLength();
    	}
    	// this never gets called, it just exists in order to get around the program  "throwing" exceptions
    	catch (ShapeException e) {
			e.printStackTrace();
		}
    	return -1;
    }
    /**
     * @return  The width of the rectangle
     */
    public double getWidth(){
    	
    	try {
    	Line l1 = new Line(point1, point2);
    	Line l2 = new Line(point1, point3);
    	Line l3 = new Line(point2, point3);
    	
    	Line final1 = null;
    	Line final2 = null;
    	
    	if(l1.computeLength()<l2.computeLength()) {
    		final1 = l1;
    		final2 = l2;
    	}
		else {
    		final1 = l2;
    		final2 = l1;
		}
    	
    	if(l3.computeLength()<final2.computeLength())
    		final2 = l3;
    	
    	Line horizontal = final1.computeSlope()<final2.computeSlope() ? final1 : final2;
    	
    	return horizontal.computeLength();
    	}
    	// this never gets called, it just exists in order to get around the program  "throwing" exceptions
    	catch (ShapeException e) {
			e.printStackTrace();
		}
    	return -1;
    }
    /**
     * @return  The height of the rectangle
     */
    public double getHeight(){
    	try {    	
    	Line l1 = new Line(point1, point2);
    	Line l2 = new Line(point1, point3);
    	Line l3 = new Line(point2, point3);
    	
    	Line final1 = null;
    	Line final2 = null;
    	
    	if(l1.computeLength()<l2.computeLength()) {
    		final1 = l1;
    		final2 = l2;
    	}
		else {
    		final1 = l2;
    		final2 = l1;
		}
    	
    	if(l3.computeLength()<final2.computeLength())
    		final2 = l3;
    	
    	Line vertical = final1.computeSlope()>final2.computeSlope() ? final1 : final2;
    	
    	return vertical.computeLength();
    	}
    	// this never gets called, it just exists in order to get around the program  "throwing" exceptions
    	catch (ShapeException e) {
			e.printStackTrace();
		}
    	return -1;
    }
}
