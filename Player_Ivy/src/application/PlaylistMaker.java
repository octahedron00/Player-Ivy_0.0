package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class PlaylistMaker implements Initializable {

	@FXML Button OK;
	@FXML TextField Textfield;
	
	void makePlaylist() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Popup.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Name Setting");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		OK.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if(!Textfield.getText().isEmpty()) {
					add();
				}
			}
		});
		
		Textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode()==KeyCode.ENTER&&!Textfield.getText().isEmpty()) {
					add();
				}
			}
			
		});
	}
	
	void add() {
		Playlist a = new Playlist(Textfield.getText(),MainAdd.list.size());
		for(int i=0; i<MainAdd.music.size(); i++) {
			MainAdd.music.get(i).check.setSelected(true);
			a.is[i] = true;
		}
		MainAdd.list.add(a);
		MainAdd.VPlaylist.getChildren().add(MainAdd.list.get(MainAdd.list.size()-1).getPane());
		MainAdd.setPlaylist(a.number);
		Stage stage = (Stage) OK.getScene().getWindow();
		stage.close();
	}

}
