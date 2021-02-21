package examples.shapes;

public class Validator {
    public static void validateDouble(double value, String errorMessage) throws ShapeException {
        if (Double.isInfinite(value) || Double.isNaN(value))
            throw new ShapeException(errorMessage);
    }
    public static void validatePositiveDouble(double value, String errorMessage) throws ShapeException {
        validateDouble(value, errorMessage);
        if (value<0)
            throw new ShapeException(errorMessage);
    }
    
    public static void validatePoint(Point point, String errorMessage) throws ShapeException {
        if (point == null)
            throw new ShapeException(errorMessage);
    }
    
    public static void validateDistinctEdges(Line l1, Line l2, String errorMessage) throws ShapeException {
    	if (Math.abs(l1.computeSlope()-l2.computeSlope()) < 0.0001)
    		throw new ShapeException(errorMessage);
    }
}