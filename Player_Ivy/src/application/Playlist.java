package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Playlist {
	String name;
	int number;
	boolean[] is = new boolean[9999];
	
	Text title = new Text();
	HBox box = new HBox();
	
	
	Playlist(String a, int n){
		this.name = a;
		this.number = n;
		this.title.setText(name);
		
		title.setFont(new Font(20));
		HBox.setMargin(title, new Insets(10));
		box.getChildren().add(title);
		box.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Playlist chosen - "+name);
				MainAdd.setPlaylist(number);
			}
		});
	}
	
	Node getPane() {
		return box;
	}
	
	int size() {
		int s = 0;
		for(int i=0; i<MainAdd.music.size(); i++) {
			if(this.is[i]) {
				s++;
			}
		}
		return s;
	}
}

