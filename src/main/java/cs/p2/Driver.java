package cs1302.p2;

import cs1302.effects.Artsy;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.embed.swing.SwingFXUtils;

/**
 * This is the driver for this application.
 */

public class Driver extends Application {

	public static MyArtsy artsy = new MyArtsy();
	public static ImageVBox resultVBox = new ImageVBox("result");
	public boolean dialogOpen = false;
	public KeyCode[] konami = {KeyCode.UP, KeyCode.UP, KeyCode.DOWN, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.B, KeyCode.A};
	public int buttonSpot = 0;
	public boolean[] buttonCorrect = {false,false,false,false,false,false,false,false,false,false};
	public boolean allTrue = false;

	@Override
	public void start(Stage primaryStage) {

		ImageVBox imageVBox1 = new ImageVBox("image1");
		ImageVBox imageVBox2 = new ImageVBox("image2");

		primaryStage.setTitle("Artsy!");
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root, 950, 475);

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				if(!dialogOpen)//if dialogs arent open 
					if (key.getCode() == konami[buttonSpot]){
						buttonCorrect[buttonSpot] = true;//has to match konami
						buttonSpot++; //checks each spot after
					}else {grumpyReset();}

				allTrue = allTrue();

				if(allTrue){

					Stage grumpStage = new Stage();
					grumpStage.setTitle("Grumpy Cat is Grumpy and So Am I.");
					FlowPane grumpPane = new FlowPane();
					Scene grumpScene = new Scene(grumpPane, 300, 300);

					Image grumpyCat = new Image("file:resources/GrumpyCat.png", 300, 300, false, false);
					ImageView grumpView = new ImageView(grumpyCat);
					grumpPane.getChildren().add(grumpView);

					grumpStage.setScene(grumpScene);
					grumpStage.show();

					grumpyReset();
				}
			}
		});



		//menu bar and items
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

		Menu fileMenu = new Menu("File");
		Menu openMenu = new Menu("Open");
		MenuItem saveMenu = new MenuItem("Save Result Image");
		MenuItem exitMenu = new MenuItem("Exit");
		MenuItem openImage1 = new MenuItem("Open Image 1");

		//opens image 1
		openImage1.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {

				dialogOpen = true;
				OpenImage openImage1 = new OpenImage(imageVBox1, "Open Image 1 Options");
				dialogOpen = false;
			}
		});

		MenuItem openImage2 = new MenuItem("Open Image 2");

		//opens image 2
		openImage2.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {

				dialogOpen = true;
				OpenImage openImage2 = new OpenImage(imageVBox2, "Open Image 2 Options");
				dialogOpen = false;
			}
		});

		//saves result
		saveMenu.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {

				dialogOpen = true;
				try {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Save Image");
					fileChooser.getExtensionFilters().addAll( 
							new FileChooser.ExtensionFilter("PNG", "*.png"),
							new FileChooser.ExtensionFilter("JPG", "*.jpg"),
							new FileChooser.ExtensionFilter("GIF", "*.gif"),
							new FileChooser.ExtensionFilter("BNP", "*.bnp"));
					File file = fileChooser.showSaveDialog(primaryStage);

					Image savedImage = resultVBox.getImage();
					BufferedImage bImage = SwingFXUtils.fromFXImage(savedImage, null);
					ImageIO.write(bImage, "png", file);

				} catch (IOException e) {}
				dialogOpen = false;
			}
		});

		//exits
		exitMenu.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {

				primaryStage.close();
			}
		});


		openMenu.getItems().addAll(openImage1,openImage2);

		fileMenu.getItems().addAll(openMenu,saveMenu,exitMenu);
		menuBar.getMenus().addAll(fileMenu);

		HBox topButtons = new HBox(15);
		topButtons.setPadding(new Insets(10));

		//doCheckers
		Button checkers = new Button();
		checkers.setText("Checkers");
		checkers.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				dialogOpen = true;
				String prompt = "Please enter the desired checker width, in pixels.";
				String title = "Checker Image Options";
				ThreeButtonsGUI checkerB = new ThreeButtonsGUI(imageVBox1.getImage(), imageVBox2.getImage(),prompt,title,"checker");
				dialogOpen = false;
			}

		});

		HBox.setHgrow(checkers, Priority.ALWAYS);

		//doHStripes
		Button hStripes = new Button();
		hStripes.setText("Horizontal Stripes");
		hStripes.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				dialogOpen = true;
				String prompt = "Please enter the desired stripe height, in pixels.";
				String title = "Horizontal Stripe Image Options";
				ThreeButtonsGUI horizB = new ThreeButtonsGUI(imageVBox1.getImage(), imageVBox2.getImage(),prompt,title,"horiz");
				dialogOpen = false;
			}

		});

		HBox.setHgrow(hStripes, Priority.ALWAYS);

		//doVStripes
		Button vStripes = new Button();
		vStripes.setText("Vertical Stripes");
		vStripes.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				dialogOpen = true;
				String prompt = "Please enter the desired stripe width, in pixels.";
				String title = "Vertical Stripe Image Options";
				ThreeButtonsGUI vertB = new ThreeButtonsGUI(imageVBox1.getImage(), imageVBox2.getImage(),prompt,title,"vert");
				dialogOpen = false;
			}

		});

		HBox.setHgrow(vStripes, Priority.ALWAYS);
		topButtons.getChildren().addAll(checkers, hStripes, vStripes);

		HBox imageBoxes = new HBox(15);
		imageBoxes.setPadding(new Insets(10));
		imageVBox1.finishedImage();
		imageVBox2.finishedImage();
		resultVBox.finishedImage();
		imageBoxes.getChildren().addAll(imageVBox1.imagePlace,imageVBox2.imagePlace,resultVBox.imagePlace);

		VBox bottomButtons = new VBox(15);
		bottomButtons.setPadding(new Insets(10));

		root.getChildren().addAll(menuBar,topButtons,imageBoxes);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * Checks to see if all booleans inside the buttonCorrect array are set to true
	 * @return allTrue boolean
	 */
	public boolean allTrue(){

		boolean allTrue = false;

		for(int x = 0; x < buttonCorrect.length; x++)
			if(buttonCorrect[x])
				allTrue = true;
			else {allTrue = false; break;}

		return allTrue;
	}

	/**
	 * Sets boolean array to true and incrementer to zero
	 */
	public void grumpyReset(){

		buttonSpot = 0;

		for(int x = 0; x < buttonCorrect.length; x++)
			if(buttonCorrect[x])
				buttonCorrect[x] = false;
	}

	public static void main(String[] args) {
		launch(args);
	} // main

} // Driver
