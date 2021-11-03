package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Player implements Initializable {

	
	@FXML Button PlayButton;	//
	@FXML Button BBack;			//
	@FXML Button BFront;		//
	@FXML Button BRandom;		//
	@FXML Button BMakePlaylist;	//
	@FXML Button BBrowse;		//
	@FXML Button BSelectAll;	//
	@FXML Button BDeselectAll;	//
	@FXML Button BShowChange;	
	
	@FXML Slider VolumeBar;
	@FXML Slider Timeline;

	@FXML Text Version;
	@FXML Text PlayingName;
	@FXML Text FilePos;
	@FXML Text TTime;

	@FXML VBox VMusic;
	@FXML VBox VPlaylist;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Version.setText(Main.Version);
		
		MainAdd.showmusicname = this.PlayingName;
		MainAdd.VMusic = this.VMusic;
		MainAdd.VPlaylist = this.VPlaylist;
		MainAdd.PlayButton = this.PlayButton;
		MainAdd.text = this.FilePos;
		MainAdd.TimeBar = this.Timeline;
		MainAdd.TTime = this.TTime;
		VolumeBar.setValue(80);
		
	
		PlayButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if(MainAdd.isplaying) {
					MainAdd.pause();
				}
				else {
					MainAdd.play();
				}
			}
		});

		
		BRandom.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(MainAdd.random) {
					MainAdd.random = false;
					System.out.println("Random OFF");
					BRandom.setText("Random OFF");
				}
				else {
					MainAdd.random = true;
					System.out.println("Random ON");
					BRandom.setText("Random ON");
				}
			}
		});
		
		
		BBrowse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				MainAdd.setAdd();
				MainAdd.debug();
			}
		});
		
		BFront.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainAdd.playNext();
			}
			
		});
		
		BBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainAdd.playBack();
			}
		});
		
		BMakePlaylist.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				PlaylistMaker a = new PlaylistMaker();
				a.makePlaylist();
			}
		});
		
		VolumeBar.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				MainAdd.volume = Math.pow(0.1, (100-arg2.doubleValue())/50);
				MainAdd.player.setVolume(MainAdd.volume);
				System.out.println(MainAdd.volume);
				
			}
			
		});
		
		BSelectAll.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainAdd.AllSet();
			}
			
		});
		
		BDeselectAll.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainAdd.AllDeset();
			}
			
		});
		
		BShowChange.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MainAdd.DelPlaylist();
			}
			
		});
	}
}
