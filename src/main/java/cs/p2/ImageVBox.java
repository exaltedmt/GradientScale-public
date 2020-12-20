package cs1302.p2;

import cs1302.effects.Artsy;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Holds images and buttons
 * @author Exalted's PC
 *
 */
public class ImageVBox{

	private Image defaultImage = new Image("file:resources/default.png", 300, 300, false, false);
	private Image image = null;
	private String imageURL = "file:resources/default.png";
	private File imageFile = new File(imageURL);
	private ImageView iv = null;
	public VBox imagePlace = new VBox(10);
	public HBox bottomButtons = new HBox(10);
	private Label imageName = new Label();
	private String id = "";
	public int takenValue = 0;
	private Button rotate = new Button();
	private Button reset = new Button();
	public double takenRotation = 0;

	/**
	 * Creates objects to store the images
	 * @param id
	 */
	public ImageVBox(String id){

		image = defaultImage;
		iv = new ImageView(image);
		this.id = id;

		imageName.setFont(new Font(10));
		imageName.setTextAlignment(TextAlignment.JUSTIFY);

		if(this.id.equalsIgnoreCase("result"))
			imageName.setText("Result");
		else imageName.setText(imageFile.getName());

		rotate.setText("Rotate");
		rotate.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				Stage stage = new Stage();
				String prompt = "Please enter your angle, in degrees";
				String title = "Rotate Image Options";
				HBox hbox = new HBox(15);
				VBox vbox = new VBox(10);
				stage.setTitle(title);
				FlowPane root = new FlowPane();
				Scene scene = new Scene(root);
				Label message = new Label(prompt);
				final TextField entry = new TextField();
				Button ok = new Button("Okay");
				ok.setText("Okay");

				ok.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {

						if ((entry.getText() != null && !entry.getText().isEmpty())) {
							Scanner reader = null;
							String okay = entry.getText();
							reader = new Scanner(okay);

							if(reader.hasNextDouble()){
								takenRotation = reader.nextDouble();
								if(takenRotation > 0){
									image = (Driver.artsy.doRotate(image, takenRotation));
									iv.setImage(image);
									stage.close();

								} else {

									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setTitle("Error found.");
									alert.setHeaderText(null);
									alert.setContentText("Please enter a double above zero into textfield.");
									alert.show();
								}
							} else{
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Error found.");
								alert.setHeaderText(null);
								alert.setContentText("Please enter a proper double into textfield.");
								alert.show();
							}



						} else {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error found.");
							alert.setHeaderText(null);
							alert.setContentText("Please enter a proper double into textfield.");
							alert.show();
						}
					}

				});


				Button cancel = new Button("Cancel");
				cancel.setText("Cancel");

				cancel.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent event) {

						stage.close();
					}

				});

				hbox.setPadding(new Insets(15));
				vbox.setPadding(new Insets(10));

				hbox.getChildren().addAll(ok,cancel);
				vbox.getChildren().addAll(message,entry,hbox);
				root.getChildren().addAll(vbox);

				stage.setScene(scene);
				stage.show();

			}

		});


		reset.setText("Reset");
		reset.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				takenValue = 0;
				reset();
			}

		});

		bottomButtons.getChildren().addAll(rotate,reset);

	}

	/**
	 * Resets image, imagefile, imageURL, and imageview
	 */
	public void reset(){

		image = null;
		imageFile = null;

		imageURL = "file:resources/default.png";
		imageFile = new File(imageURL);
		image = defaultImage;
		iv.setImage(image);
	}

	/**
	 * Makes image from opened file
	 * @param file
	 */
	public void setImage(File file){

		image = null;
		imageFile = null;

		imageFile = file;
		imageURL = imageFile.getPath();


		image = new Image("file:"+imageURL, 300, 300, false, false);
		iv.setImage(image);
		imageName.setText(imageFile.getName());
	}

	/**
	 * Creates image from url link
	 * @param url
	 */
	public void setImage(URL url){

		image = null;
		imageFile = null;

		URL vBoxURL = url;//incase url is edited

		BufferedImage openedImage = null;
		try {
			openedImage = ImageIO.read(vBoxURL);//makes url image
			BufferedImage resizedImage = new BufferedImage(300, 300, openedImage.getType());//holds resized url image

			Graphics2D resize = resizedImage.createGraphics();
			resize.drawImage(openedImage, 0, 0, 300, 300, null);//all pixels inside resized image
			resize.dispose();

			int width = (int) resizedImage.getWidth(); //method from myArtsy
			int height = (int) resizedImage.getHeight();

			WritableImage wr = new WritableImage(width, height);
			PixelWriter pw = wr.getPixelWriter();

			for(int x = 0; x < width; ++x)
				for(int y = 0; y < height; ++y){
					pw.setArgb(x, y, resizedImage.getRGB(x, y));
				}


			imageFile = new File(vBoxURL.toURI().toString());	//new file from url
			imageURL = imageFile.getPath(); //for naming vbox later

			imageName.setText(imageFile.getName());

			image = wr;
			iv.setImage(image);


		} catch (Exception e1) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error found.");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a proper filepath/URL into textfield.");
			alert.show();

		}
	}

	/**
	 * Makes this.image the image
	 * @param image
	 */
	public void setImage(Image image){

		this.image = null;

		this.image = image;
		iv.setImage(this.image);
	}

	/**
	 * adds to the vbox
	 */
	public void finishedImage(){

		imagePlace.getChildren().addAll(imageName,iv,bottomButtons);
	}

	/**
	 * calls image
	 * @return image
	 */
	public Image getImage(){

		return this.image;
	}

}

