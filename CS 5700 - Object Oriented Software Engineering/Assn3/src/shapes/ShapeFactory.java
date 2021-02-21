package shapes;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.imageio.ImageIO;

import javafx.util.Pair;

public class ShapeFactory {

	private IntrinsicState intrinsic;
	
	public IntrinsicState getIntrinsic() {
		return intrinsic;
	}

	private BufferedImage checkValidImageFile(InputStream input) throws IOException {
		if(input == null) {
			throw new IOException("Invalid input");
		}
	
		if(input.available()==0) {
			throw new IOException("File is empty");
		}
		BufferedImage testImg = ImageIO.read(input);
		
		if(testImg==null) {
			throw new IOException("File is not an image");
		}
		
		return testImg;
	}
	
	private void createImageFile(BufferedImage img) throws IOException	{
		File file = new File("src/res/image"+(this.intrinsic.getImages().size()-1)+".png");
		ImageIO.write(img, "png", file);
	}
	
	public boolean compareImages(BufferedImage bfa, BufferedImage bfb) {
        DataBuffer dbA = bfa.getData().getDataBuffer();
        int sizeA = dbA.getSize();                      
        DataBuffer dbB = bfb.getData().getDataBuffer();
        int sizeB = dbB.getSize();
        if(sizeA == sizeB) {
            for(int i=0; i<sizeA; i++) { 
                if(dbA.getElem(i) != dbB.getElem(i)) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
	}
	
	private int findImageIndex(BufferedImage searchable) {
		int index = -1;
		for (int i = 0; i<this.intrinsic.getImages().size();i++) {
			if(compareImages(searchable,this.intrinsic.getImages().get(i))) {
				index = i;
			}
		}
		return index;		
	}
	
	private boolean isNewImage(BufferedImage input) throws IOException {
		for (BufferedImage existing:this.intrinsic.getImages()) {
			if(compareImages(input, existing)) {
				return false;
			}
		}
		return true;
	}
	
	public ShapeFactory() {
		intrinsic = new IntrinsicState();
	}
	
	public Shape createShape(InputStream in) throws IOException, NumberFormatException, ShapeException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        //StringBuilder out = new StringBuilder();
        String fin = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            fin = fin.concat(line).concat("\n");
        }
		return createShape(fin);
	}	
	
	
	
	public Shape createShape(String in) throws NumberFormatException, ShapeException, IOException {
		
		String [] lines  = in.split("\n");
		Shape toReturn = null;
		
		String [] line = lines[0].split(",");
		
		if(line[0].trim().toLowerCase().equals("point")) {
			toReturn = new Point(Double.parseDouble(line[1].trim()),Double.parseDouble(line[2].trim()));
		}
		if(line[0].trim().toLowerCase().equals("line")) {
			Point p1 = new Point(Double.parseDouble(line[1].trim()),Double.parseDouble(line[2].trim()));
			Point p2 = new Point(Double.parseDouble(line[3].trim()),Double.parseDouble(line[4].trim()));
			toReturn = new Line(p1,p2);
		}
		if(line[0].trim().toLowerCase().equals("circle")) {
			Point p1 = new Point(Double.parseDouble(line[1].trim()),Double.parseDouble(line[2].trim()));
			toReturn = new Circle(p1,Double.parseDouble(line[3].trim()));
		}
		if(line[0].trim().toLowerCase().equals("ellipse")) {
			Point p1 = new Point(Double.parseDouble(line[1].trim()),Double.parseDouble(line[2].trim()));
			return new Ellipse(p1,Double.parseDouble(line[3].trim()),Double.parseDouble(line[4].trim()));
		}
		if(line[0].trim().toLowerCase().equals("triangle")) {
			Point p1 = new Point(Double.parseDouble(line[1].trim()),Double.parseDouble(line[2].trim()));
			Point p2 = new Point(Double.parseDouble(line[3].trim()),Double.parseDouble(line[4].trim()));
			Point p3 = new Point(Double.parseDouble(line[5].trim()),Double.parseDouble(line[6].trim()));
			toReturn = new Triangle(p1,p2,p3);
		}
		if(line[0].trim().toLowerCase().equals("rectangle")) {
			Point p1 = new Point(Double.parseDouble(line[1].trim()),Double.parseDouble(line[2].trim()));
			Point p2 = new Point(Double.parseDouble(line[3].trim()),Double.parseDouble(line[4].trim()));
			Point p3 = new Point(Double.parseDouble(line[5].trim()),Double.parseDouble(line[6].trim()));
			Point p4 = new Point(Double.parseDouble(line[7].trim()),Double.parseDouble(line[8].trim()));
			toReturn = new Rectangle(p1,p2,p3,p4);
		}
		if(line[0].trim().toLowerCase().equals("square")) {
			Point p1 = new Point(Double.parseDouble(line[1].trim()),Double.parseDouble(line[2].trim()));
			Point p2 = new Point(Double.parseDouble(line[3].trim()),Double.parseDouble(line[4].trim()));
			Point p3 = new Point(Double.parseDouble(line[5].trim()),Double.parseDouble(line[6].trim()));
			Point p4 = new Point(Double.parseDouble(line[7].trim()),Double.parseDouble(line[8].trim()));
			toReturn = new Square(p1,p2,p3,p4);
		}
		if(line[0].trim().toLowerCase().equals("embedded")) {
			double width = Double.parseDouble(line[1].trim());
			double height = Double.parseDouble(line[2].trim());
			InputStream input = null;
			
			if("file".equals(line[3].trim().toLowerCase())) {
				input = new FileInputStream(line[4].trim());
			}
			if("resource".equals(line[3].trim().toLowerCase())) {
				try {
				input = getClass().getResource(line[4].trim()).openStream();
				}
				catch(Exception e) {}
			}
			int key;
			BufferedImage intr = checkValidImageFile(input);
			if(this.isNewImage(intr)) {
				this.intrinsic.getImages().add(intr);
				createImageFile(intr);
			}
			key = this.findImageIndex(intr);
			toReturn = new EmbeddedImage(width,height,key,intrinsic);
		}
		if(line[0].trim().toLowerCase().equals("composite")) {
			Composite bigGuy = new Composite();
			Deque<Pair<Composite,Integer>> stack = new ArrayDeque<Pair<Composite, Integer>>();
			
			stack.push(new Pair<>(bigGuy,Integer.parseInt(line[1].trim())));
			for (int i = 1; i<lines.length;i++) {
				String curLine = lines[i];
				//System.out.println(curLine);
				String[] terms = curLine.split(",");
				if(terms[0].toLowerCase().trim().equals("composite")){
					Composite toAdd =new Composite();
					stack.push(new Pair<>(toAdd,Integer.parseInt(terms[1].trim())));
				}
				else {
					
					stack.peek().getKey().addShape(createShape(curLine));
				}
				while(stack.peek().getKey().getShapes().size()==stack.peek().getValue() && stack.size()>1) {
					Pair<Composite, Integer> temp = stack.pop();
					stack.peek().getKey().addShape(temp.getKey());
				}
			}
			toReturn = bigGuy;
		}
		return toReturn;
	}
}
