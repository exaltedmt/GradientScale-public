package cs1302.p2;

import cs1302.effects.Artsy;
import java.io.File;

import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.net.*;

/**
 * Takes file path or url and makes images
 * @author Exalted's PC
 *
 */
public class OpenImage {

	private String takenString = "";
	private final TextField entryImport = new TextField();
	private final TextField entryURL = new TextField();
	public File openedFile = null;
	public URL openedURL = null;

	/**
	 * Open an image through a GUI
	 * @param src - image box to call setImage method
	 * @param title - open image (x)
	 */
	public OpenImage(ImageVBox src,String title){

		HBox remoteHBox = new HBox(15);
		VBox remoteVBox = new VBox(10);
		VBox localVBox = new VBox(10);
		HBox localHBox = new HBox(15);

		Stage stage = new Stage();
		stage.setTitle(title);
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root);

		TabPane tabPane = new TabPane();//layout
		tabPane.prefWidthProperty().bind(stage.widthProperty());
		Tab local = new Tab();
		Tab remote = new Tab();

		local.setText("Local File");
		local.setContent(localVBox);

		remote.setText("Remote File");
		remote.setContent(remoteVBox);

		tabPane.getTabs().addAll(local,remote);

		entryImport.setPromptText("Enter a path from the file System here or click \"Browse\"");
		entryURL.setPromptText("Enter URL");

		Button localOpenFile = new Button("Open File");
		Button remoteOpenFile = new Button("Open File");
		Button browse = new Button("Browse");
		browse.setText("Browse");

		browse.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();//open images with extension
				fileChooser.setTitle("Open Image");
				fileChooser.getExtensionFilters().addAll( 
						new FileChooser.ExtensionFilter("PNG", "*.png"),
						new FileChooser.ExtensionFilter("JPG", "*.jpg"),
						new FileChooser.ExtensionFilter("GIF", "*.gif"),
						new FileChooser.ExtensionFilter("BNP", "*.bnp"));
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					entryImport.setText(file.getPath());
				}
			}

		});

		remoteOpenFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if ((entryURL.getText() != null && !entryURL.getText().isEmpty())) {
					Scanner reader = null;
					String okay = entryURL.getText();
					reader = new Scanner(okay);

					if(reader.hasNextLine()){
						takenString = reader.nextLine();//reading in input

						openedURL = null;
						try {
							openedURL = new URL(takenString);

						} catch (Exception e1) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error found.");
							alert.setHeaderText(null);
							alert.setContentText("Please enter a proper filepath/URL into textfield.");
							alert.show();
						}

						src.setImage(openedURL);
						stage.close();	
					}else {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error found.");
						alert.setHeaderText(null);
						alert.setContentText("Please enter a proper filepath/URL into textfield.");
						alert.show();
					}

				} else {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error found.");
					alert.setHeaderText(null);
					alert.setContentText("Please enter a proper filepath/URL into textfield.");
					alert.show();
				}


			}


		});

		localOpenFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if ((entryImport.getText() != null && !entryImport.getText().isEmpty())) {
					Scanner reader = null;
					String okay = entryImport.getText();
					reader = new Scanner(okay);

					if(reader.hasNextLine()){
						takenString = reader.nextLine();
						stage.close();
					}

					else {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error found.");
						alert.setHeaderText(null);
						alert.setContentText("Please enter a proper filepath/URL into textfield.");
						alert.show();
					}

				} else {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error found.");
					alert.setHeaderText(null);
					alert.setContentText("Please enter a proper filepath/URL into textfield.");
					alert.show();
				}

				openedFile = new File(takenString);
				src.setImage(openedFile);
			}


		});

		localHBox.setPadding(new Insets(15));
		localVBox.setPadding(new Insets(10));
		remoteHBox.setPadding(new Insets(15));
		remoteVBox.setPadding(new Insets(10));

		localHBox.getChildren().addAll(entryImport,browse);
		remoteHBox.getChildren().addAll(entryURL);
		localVBox.getChildren().addAll(localHBox,localOpenFile);
		remoteVBox.getChildren().addAll(remoteHBox,remoteOpenFile);

		root.getChildren().addAll(tabPane);

		stage.setScene(scene);
		stage.show();	
	}


}

