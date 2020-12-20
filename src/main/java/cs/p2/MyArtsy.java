package cs1302.p2;

import cs1302.effects.Artsy;
import javafx.scene.image.*;


public class MyArtsy implements Artsy {

	@Override
	public Image doCheckers(Image src1, Image src2, int size) {

		int width = (int) src1.getWidth();
		int height = (int) src1.getHeight();

		WritableImage checkeredImage = new WritableImage(width, height);

		PixelReader prFirstImage = src1.getPixelReader();
		PixelReader prSecondImage = src2.getPixelReader();

		PixelWriter pw = checkeredImage.getPixelWriter();


		for (int y = 0; y < height; ++y) {

			int ySize = y/size;
			boolean yEven = false;
			if(ySize % 2 == 0) 
				yEven = true; //make sure written pixels dont go past designated size

			for (int x = 0; x < width; ++x) {
				int xSize = x/size;
				boolean xEven = false;

				if(xSize % 2 == 0) xEven = true;
				if(yEven){
					if(xEven){
						pw.setArgb(x, y, prFirstImage.getArgb(x, y));
					}	else {
						pw.setArgb(x, y, prSecondImage.getArgb(x, y));
					}
				} else {
					if(xEven){
						pw.setArgb(x, y, prSecondImage.getArgb(x, y));
					} else {
						pw.setArgb(x, y, prFirstImage.getArgb(x, y));
					}
				}                
			}

		}

		return checkeredImage;	
	} // doCheckers

	@Override
	public Image doHorizontalStripes(Image src1, Image src2, int height) {
		int imageWidth = (int) src1.getWidth();
		int imageHeight = (int) src1.getHeight();

		WritableImage stripedImage = new WritableImage(imageWidth, imageHeight);

		PixelReader prFirstImage = src1.getPixelReader();
		PixelReader prSecondImage = src2.getPixelReader();

		PixelWriter pw = stripedImage.getPixelWriter();

		for(int y = 0; y < imageHeight; ++y){
			int yHeight = y/height;
			boolean yEven = false;

			if(yHeight % 2 == 0){
				yEven = true;
			}

			for(int x = 0; x < imageWidth; ++x){
				if(yEven){
					pw.setArgb(x, y, prFirstImage.getArgb(x, y));
				}else{
					pw.setArgb(x, y, prSecondImage.getArgb(x, y));
				}
			}
		}


		return stripedImage;
	} // doHorizontalStripes

	@Override
	public Image doRotate(Image src, double degrees) {

		double radians = Math.toRadians(degrees);
		int imageWidth = (int) src.getWidth();
		int imageHeight = (int) src.getHeight();

		WritableImage rotatedImage = new WritableImage(imageWidth, imageHeight);
		PixelReader prImage = src.getPixelReader();
		PixelWriter pw = rotatedImage.getPixelWriter();

		int imageX = 0;
		int imageY = 0;
		int angleX = 0;
		int angleY = 0;
		int rotatedX = 0;
		int rotatedY = 0;

		if(!(radians % 2*Math.PI == 0))//prevent shifting
			try{
				for(int x = 0; x < imageWidth; ++x){

					for(int y = 0; y < imageHeight; ++y){

						try{
							imageX = x - (imageWidth/2); //origin setting
							imageY = y - (imageHeight/2);

							angleX = (int)((imageX * Math.cos(radians)) + (imageY * Math.sin(radians))); //rotating
							angleY = (int)((-imageX * Math.sin(radians)) + (imageY * Math.cos(radians)));

							rotatedX = (angleX + (imageWidth/2)); //adding back origin
							rotatedY = (angleY + (imageHeight/2));

							pw.setArgb(x, y, prImage.getArgb(rotatedX, rotatedY));
						}catch (Exception e){continue;}//prevent break
					}
				}}catch(Exception e){}

		return rotatedImage;
	}

	@Override
	public Image doVerticalStripes(Image src1, Image src2, int width) {
		int imageWidth = (int) src1.getWidth();
		int imageHeight = (int) src1.getHeight();

		WritableImage verticalImage = new WritableImage(imageWidth, imageHeight);

		PixelReader prFirstImage = src1.getPixelReader();
		PixelReader prSecondImage = src2.getPixelReader();

		PixelWriter pw = verticalImage.getPixelWriter();


		for(int x = 0; x < imageWidth; ++x){
			int xWidth = x/width;
			boolean xEven = false;
			if(xWidth % 2 == 0){
				xEven = true;
			}

			for(int y = 0; y < imageHeight; ++y){
				if(xEven){
					pw.setArgb(x, y, prFirstImage.getArgb(x, y));
				}
				else{
					pw.setArgb(x, y, prSecondImage.getArgb(x, y));
				}
			}
		}


		return verticalImage;

	} // doVerticalStripes

} // MyArtsy


