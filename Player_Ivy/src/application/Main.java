package application;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	
	static String Version = "Ver 0.1.2 by octo";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Player.fxml"));
			Scene scene = new Scene(root,1000,1000);
			
			primaryStage.setTitle("Ivy");
			primaryStage.setScene(scene);

			primaryStage.show();
			MainAdd.stage = primaryStage;
			MainAdd.setAdd();
			MainAdd.playNew(0);
			Thread thread = new Thread(new TimeBar());
			thread.start();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					if(MainAdd.list.size()>1) MainAdd.writePlaylist(MainAdd.playlist);
				}
				
			});
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
}
