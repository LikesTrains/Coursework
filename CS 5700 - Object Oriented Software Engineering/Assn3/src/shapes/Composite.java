package shapes;

import java.awt.Graphics;
import java.util.ArrayList;

public class Composite extends Shape {
	
	private ArrayList<Shape> shapes;
	
	private void validateShape(Shape shape) throws ShapeException {
		if(shape==null) {
			throw new ShapeException("Shape cannot be null");
		}
	}
	
	public ArrayList<Shape> getShapes() {
		return this.shapes;
	}

	public Composite() {
		this.shapes = new ArrayList<Shape>();
	}
	
	public Composite(Shape newShape) throws ShapeException {
		this.shapes = new ArrayList<Shape>();
		validateShape(newShape);
		shapes.add(newShape);
	}
	
	public Composite(ArrayList<Shape> newShapes) throws ShapeException {
		this.shapes = new ArrayList<Shape>();
		
		for (Shape shape : newShapes) {
			validateShape(shape);
		}
		
		shapes.addAll(newShapes);
	}

	public void addShape(Shape addable) throws ShapeException {
		validateShape(addable);
		this.shapes.add(addable);
	}
	
	public void removeShape(int index) throws ShapeException {
		Validator.validatePositiveDouble(index, "Invalid index");
		this.shapes.remove(index);		
	}
	
	public void removeAll() {
		this.shapes.removeAll(this.shapes);
	}
	@Override
	public void move(double deltaX,double deltaY) throws ShapeException {
		for (Shape movable: this.shapes) {
			movable.move(deltaX, deltaY);
		}
	}
	@Override
	public double getArea() {
		double total = 0;
		for(Shape adding:this.shapes) {
			total+=adding.getArea();
		}
		return total;
	}
	@Override
	public ArrayList<Point> getPoints() {
		ArrayList <Point> allPoints = new ArrayList<Point>();
		for(Shape source:this.shapes) {
			allPoints.addAll(source.getPoints());
		}
		return allPoints;
	}
	@Override
	public String toString() {
		String base = "Composite,"+this.getShapes().size();		
		for(Shape sh:this.shapes) {
			base += "\n"+sh.toString();
		}		
		return base;
	}

	@Override
	public void draw(Graphics g) {
		for(Shape s:this.shapes) {
			s.draw(g);
		}		
	}
}