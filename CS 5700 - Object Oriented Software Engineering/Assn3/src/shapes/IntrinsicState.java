package shapes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class IntrinsicState {

	private ArrayList <BufferedImage> images;
	
	public ArrayList<BufferedImage> getImages() {
		return images;
	}
	
	
	public IntrinsicState() {
		images = new ArrayList<BufferedImage>();
	}

}
