package examples.shapes;

public class Square extends Rectangle {
	
	private void validateSquare(Point point1, Point point2, Point point3, Point point4, String errorMessage) throws ShapeException {
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
    	
    	if (horizontal.computeLength()-vertical.computeLength()>0.001)
    		throw new ShapeException(errorMessage);
	}

	public Square(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4)
			throws ShapeException {
		this(new Point (x1, y1), new Point(x2, y2),new Point (x3, y3), new Point(x4, y4));
	}
	public Square(Point p1, Point p2, Point p3, Point p4)
			throws ShapeException {
		this(p1, p2, p3, p4,"Adjacent points must not be in the same line", "Invalid square, inconsistent distance from vertex to center","Invalid square, width and height do not match");
		
	}
	protected Square(Point p1, Point p2, Point p3, Point p4, String errorMessage1, String errorMessage2, String errorMessage3)
			throws ShapeException {
		super(p1,p2,p3,p4, errorMessage1, errorMessage2);
		validateSquare(p1, p2, p3, p4, errorMessage3);
	}
}
