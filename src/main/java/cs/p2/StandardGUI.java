package cs1302.p2;

import cs1302.effects.Artsy;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Base for the top three buttons above image boxes
 * @author Exalted's PC
 *
 */
public abstract class StandardGUI {

	public int takenValue = 0;
	public double takenRotation = 0;
	public String id = "";

	/**
	 * Forms a GUI to read user input
	 * 
	 * @param src1 - image to be modified by methodSelection
	 * @param src2 - image to be modified by methodSelection
	 * @param prompt - translucent text in textfield
	 * @param title - title of stage
	 * @param id - which method to be selected in methodSelection
	 */
	public StandardGUI(Image src1, Image src2, String prompt, String title, String id){

		this.id = id;
		HBox hbox = new HBox(15);
		VBox vbox = new VBox(10);
		Stage stage = new Stage();
		stage.setTitle(title);
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root);
		Label message = new Label(prompt);
		final TextField entry = new TextField();
		Button ok = new Button();
		Button cancel = new Button();

		ok = okButton(src1, src2, entry,stage);
		cancel = cancelButton(stage);

		hbox.setPadding(new Insets(15));
		vbox.setPadding(new Insets(10));

		hbox.getChildren().addAll(ok,cancel);
		vbox.getChildren().addAll(message,entry,hbox);
		root.getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Confirms user input
	 * 
	 * @param src1 - image to be passed into methodSelection
	 * @param src2 - image to be passed into methodSelection
	 * @param entry - where input is read from
	 * @param stage - stage to close
	 * @return
	 */
	public Button okButton(Image src1, Image src2, TextField entry, Stage stage){

		Button ok = new Button();
		ok.setText("Okay");

		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if ((entry.getText() != null && !entry.getText().isEmpty())) {
					Scanner reader = null;
					String okay = entry.getText();
					reader = new Scanner(okay);

					if(reader.hasNextInt()){
						takenValue = reader.nextInt();
						methodSelection(src1,src2,stage);
					}else{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error found.");
						alert.setHeaderText(null);
						alert.setContentText("Please enter a proper integer into textfield.");
						alert.show();
					}

				} else {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error found.");
					alert.setHeaderText(null);
					alert.setContentText("Please enter a proper integer into textfield.");
					alert.show();
				}
			}

		});

		return ok;
	}

	/**
	 * Chooses the proper doMethod from myArtsy to create resulting image
	 * 
	 * @param src1 - image to be modified
	 * @param src2 - image to be modified
	 * @param stage - stage to be closed
	 */
	public void methodSelection(Image src1, Image src2, Stage stage){

		if(takenValue > 0){
			if(id.equalsIgnoreCase("checker"))
				Driver.resultVBox.setImage(Driver.artsy.doCheckers(src1, src2, takenValue));

			if(id.equalsIgnoreCase("horiz"))
				Driver.resultVBox.setImage(Driver.artsy.doHorizontalStripes(src1, src2, takenValue));

			if(id.equalsIgnoreCase("vert"))
				Driver.resultVBox.setImage(Driver.artsy.doVerticalStripes(src1, src2, takenValue));
			stage.close();
		}else {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error found.");
			alert.setHeaderText(null);
			alert.setContentText("Please enter an integer above zero into textfield.");
			alert.show();
		}
	}


	/**
	 * Cancels operation
	 * @param stage to be closed
	 * @return a button that closes stages
	 */
	public Button cancelButton(Stage stage){

		Button cancel = new Button();
		cancel.setText("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				stage.close();
			}

		});

		return cancel;
	}

	public int getTakenValue(){

		return this.takenValue;
	}

	public double getTakenRotation(){

		return this.takenRotation;
	}
}
