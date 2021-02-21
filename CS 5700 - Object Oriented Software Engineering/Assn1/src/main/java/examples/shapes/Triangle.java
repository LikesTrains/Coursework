package examples.shapes;
/**
*
*  Triangle
*
*  This class represents triangle objects that can be moved.  Users of a triangle can also get its area
*
*/
public class Triangle {
	
    private Point point1;
    private Point point2;
    private Point point3;
    
    private void validateTriangle(Point point1, Point point2,Point point3) throws ShapeException {
    	Line line1 = new Line(point1, point2);
    	Line line2 = new Line(point1, point3);
    	Line line3 = new Line(point2, point3);
    	
    	Validator.validateDistinctEdges(line1, line2, "All 3 points must not be in the same line");
    	Validator.validateDistinctEdges(line1, line3, "All 3 points must not be in the same line");
    }
    
    /**
     * Constructor based on x-y Locations
     * @param x1                The x-location of first point -- must be a valid double.
     * @param y1                The y-location of first point -- must be a valid double.
     * @param x2                The x-location of second point -- must be a valid double.
     * @param y2                The y-location of second point -- must be a valid double.
     * @param x3                The x-location of third point -- must be a valid double.
     * @param y3                The y-location of third point -- must be a valid double.
     * @throws ShapeException   Exception thrown if any parameter is invalid
     */
    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) throws ShapeException {
        this(new Point(x1, y1), new Point(x2, y2),new Point(x3, y3));
    }
    
    /**
    *
    * @param point1            The first point -- must not be null
    * @param point2            The second point -- must not be null
    * @param point3            The third point -- must not be null
    * @throws ShapeException   Exception thrown if any parameter is invalid or if all points fall in the same line
    */
    public Triangle(Point point1, Point point2,Point point3) throws ShapeException {
    	validateTriangle(point1, point2, point3);
    	
    	this.point1 = point1;
    	this.point2 = point2;
    	this.point3 = point3;        
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
     * Move a triangle
     *
     * @param deltaX            The delta x-location by which the triangle should be moved -- must be a valid double
     * @param deltaY            The delta y-location by which the triangle should be moved -- must be a valid double
     * @throws ShapeException   Exception thrown if any parameter is invalid
     */
    public void move(double deltaX, double deltaY) throws ShapeException {
        point1.move(deltaX, deltaY);
        point2.move(deltaX, deltaY);
        point3.move(deltaX, deltaY);
    }
    
    /**
     * @return  The area of the triangle
     */
    public double ComputeArea(){
    	double d1 = Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
    	double d2 = Math.sqrt(Math.pow(point1.getX() - point3.getX(), 2) + Math.pow(point1.getY() - point3.getY(), 2));
    	double d3 = Math.sqrt(Math.pow(point2.getX() - point3.getX(), 2) + Math.pow(point2.getY() - point3.getY(), 2));
    	    	
    	double semiP = (d1+d2+d3)/2;
    	double area = Math.sqrt(semiP*(semiP-d1)*(semiP-d2)*(semiP-d3));   	
    	
    	return area;
    }
}