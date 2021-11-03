package application;

import java.io.File;
//import java.io.UnsupportedEncodingException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.text.Text;

public class Music {
	File file;
	String name;
	String address;
	
	private int number = 0;
	Media media;
	
	HBox pane = new HBox();
	CheckBox check = new CheckBox();
	Text title = new Text();
	Text add = new Text();
	Text num = new Text();
	
	Music(File f, int n){
		this.file = f;
		this.name = f.getName();
		this.address = f.getPath();
		this.media = new Media(f.toURI().toString());
		this.number = n;
		name = name.replace(".mp3", "");
		
//		try {
//			title.setText(new String(this.name.getBytes("UTF-16BE"), "UTF-16BE"));
//		} catch (UnsupportedEncodingException e) {}
		title.setText(this.name);
		title.setWrappingWidth(400);
		num.setText(Integer.toString(this.number+1));
		num.setWrappingWidth(30);
		add.setText(this.address.replace(this.name+".mp3", ""));
		add.setWrappingWidth(300);
		check.setSelected(true);
		pane.getChildren().add(num);
		pane.getChildren().add(check);
		pane.getChildren().add(title);
		pane.getChildren().add(add);
		HBox.setMargin(num, new Insets(10));
		HBox.setMargin(check, new Insets(10));
		HBox.setMargin(add, new Insets(10));
		
		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Clickedplay next - " + name + " " + number);
				MainAdd.playNew(n);
			}
		});
		
		check.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Checked - "+name + " " + check.isSelected());
				MainAdd.list.get(MainAdd.listing).is[number] = check.isSelected();
			}
			
		});
/*		check.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
*/
	}	
	
	Node getPane(){
		return pane;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o.getClass().getName()=="Music") {
			Music a = (Music)o;
			if(a.file==this.file) {
				return true;
			}
		}
		return false;
	}
	
	boolean isRadio() {
//		return radio.is
		return true;
	}
	
}
