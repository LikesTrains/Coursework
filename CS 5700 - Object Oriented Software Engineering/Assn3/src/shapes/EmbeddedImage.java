package shapes;

import java.awt.Graphics;
import java.io.IOException;

public class EmbeddedImage extends Rectangle {
	// consider using a hash map/dictionary for these images
	
	private int key;
	private IntrinsicState images;
	
	
	public int getKey() {
		return key;
	}	

	public EmbeddedImage(double width, double height, int key, IntrinsicState IS) throws ShapeException, IOException {
		super(new Point(0,0),new Point(width,0),new Point(0,height),new Point(width,height));
		Validator.validatePositiveDouble(width, "Width cannot be a negative value");
		Validator.validatePositiveDouble(height, "Height cannot be a negative value");
		this.key = key;
		this.images = IS;
	}
	@Override
	public String toString() {
		String base = "embedded,"+this.points.get(1).getX() + ","+this.points.get(2).getY();
		return base+",file,src/res/image"+this.key+".png";
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(this.images.getImages().get(this.key), (int)this.points.get(0).getX(), (int)this.points.get(0).getY(),(int) this.getWidth(), (int)this.getHeight(), null);
		
	}
}