package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
//import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainAdd{
	
	static Stage stage;
	static File add;
	static File playlist;
	static List<Music> music = new ArrayList<Music>();
	static List<Playlist> list = new ArrayList<Playlist>();
	static List<Object> times = new ArrayList<Object>();
	static MediaPlayer player;
	
	static Text text;
	static Text showmusicname;
	static Text TTime;
	static VBox VMusic;
	static VBox VPlaylist;
	static Button PlayButton;
	static Slider TimeBar;
	
	static int playing;
	static int listing;
	static double volume = Math.pow(0.1, 0.4);
	
	static boolean random = true;
	static boolean isplaying = false;
	
	static void setAdd() {
		DirectoryChooser finder = new DirectoryChooser();
		finder.setInitialDirectory(new File("C:/"));
		finder.setTitle("Set file of the musics");
		File a = finder.showDialog(stage);
		text.setText(a.getPath());
		
		findMedia(a);
		
		Playlist add = new Playlist("Main",0);
		list.add(add);
		
		VMusic.getChildren().clear();
		System.out.println("VMusic Cleared");
		for(int i=0; i<music.size(); i++) {
			add.is[i] = true;
			VMusic.getChildren().add(music.get(i).getPane());
		}
		
		
		playlist = new File("./" + a.getPath().replace("\\", ".") + ".Ivy_Playlist.txt");
		System.out.println(playlist.getPath());
		try {
			readPlaylist(playlist);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<list.size(); i++) {
			VPlaylist.getChildren().add(list.get(i).getPane());
		}
		showPlaylist();
		
		System.out.println(music.size());
		for(int i=0; i<music.size(); i++) {
			try {
				String ss = music.get(i).name;
				System.out.println(new String(ss.getBytes("UTF-16BE"), "UTF-16BE"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
	static void findMedia(File a) {
		for(File b : a.listFiles()) {
//			System.out.println(b.getPath());
			if(b.isDirectory()) {
				findMedia(b);
			}
			else if(b.isFile()&&b.getName().endsWith(".mp3")) {
				music.add(new Music(b,music.size()));
			}
		}
		return;
	}
	
	static void readPlaylist(File a) throws IOException {
		BufferedReader r;
		int n, m, i, j, k;
		String s;
		Playlist t;
		try {
			r = new BufferedReader(new FileReader(a));
			
			n = Integer.parseInt(r.readLine());
			for(i=1; i<=n; i++) {
				s = r.readLine();
				t = new Playlist(s,i);
				m = Integer.parseInt(r.readLine());
				for(j=0; j<m; j++) {
					s = r.readLine();
					for(k=0; k<music.size(); k++) {
						if(music.get(k).address.equals(s)) {
							t.is[k] = true;
							System.out.println(t.name + " " + s);
						}
					}
				}
				list.add(t);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static void writePlaylist(File a) {
		try {
			BufferedWriter r = new BufferedWriter(new FileWriter(a));
			int j;
			boolean f = true;
			r.write(Integer.toString(list.size()-1));
			for(Playlist l : list) {
				if(f) {
					f = false;
				}
				else {
					r.newLine();
					r.write(new String(l.name.getBytes("UTF-16BE"), "UTF-16BE"));
					r.newLine();
					r.write(Integer.toString(l.size()));
					for(j=0; j<music.size(); j++) {
						if(l.is[j]) {
							r.newLine();
							r.write(new String(music.get(j).address.getBytes("UTF-16BE"), "UTF-16BE"));
						}
					}
				}
			}
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static MediaPlayer newPlayer(Media a) {
		MediaPlayer p = new MediaPlayer(a);
		p.setVolume(volume);
		p.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				MainAdd.playNext();
			}
		});
		
		return p;
	} 
	
	static void playNext(){
		player.stop();
		if(list.get(listing).size()==0) {
			return;
		}
		int s = playing;
		do {
			if(random) {
				playing = (int)(1000000*Math.random());
			}
			else {
				playing++;
			}
			playing %= music.size();
		} while(list.get(listing).is[playing] == false || (playing == s && list.get(listing).size()>1));
		
		player = newPlayer(music.get(playing).media);
		
		if(isplaying) {
			player = newPlayer(music.get(playing).media);
			player.play();
		}
		
		showmusicname.setText(music.get(playing).name);
		
		System.out.println("Next Playing - " + music.get(playing).name + " " + isplaying);
		times.add(playing);
		showMusic();
		return;
	}
	
	static void playNew(int a) {
		if(player != null) player.stop();
		playing = a;
		isplaying = true;
		PlayButton.setText("Pause");
		
		player = newPlayer(music.get(playing).media);
		player.play();
		
		showmusicname.setText(music.get(playing).name);
		
		System.out.println("SelectedPlay - " + music.get(playing).name + " " + isplaying);
		times.add(playing);
		showMusic();
		return;
	}
	
	static void playBack() {
		if(player.getCurrentTime().toSeconds()<5) {
			if(times.size()>1) {
				player.stop();
				times.remove(times.size()-1);
				playing = (int) times.get(times.size()-1);
				player = newPlayer(music.get(playing).media);
				showmusicname.setText(music.get(playing).name);
				if(isplaying) {
					player.play();
				}
			}
		}
		else {
			player.stop();
			player.play();
			isplaying = true;
			PlayButton.setText("Pause");
		}
		showMusic();
		return;
	}
	
	static void play() {
		player.play();
		isplaying = true;
		PlayButton.setText("Pause");		
	}
	
	static void pause() {
		player.pause();
		isplaying = false;
		PlayButton.setText("Play");
	}
	
	static void setPlaylist(int n) {
		listing = n;
		for(int i=0; i<music.size(); i++) {
			music.get(i).check.setSelected(list.get(listing).is[i]);
		}
		showPlaylist();
		return;
	} 
	
	static void showPlaylist() {
		for(int i=0; i<list.size(); i++) {
			if(i==listing) {
				VPlaylist.getChildren().get(i).setStyle("-fx-background-color: ivory;");
			}
			else {
				VPlaylist.getChildren().get(i).setStyle("");
			}
		}
	}
	
	static void showMusic() {
		for(int i=0; i<music.size(); i++) {
			if(i==playing) {
				VMusic.getChildren().get(i).setStyle("-fx-background-color: ivory;");
			}
			else {
				VMusic.getChildren().get(i).setStyle("");
			}
		}
	}
	
	static void debug() {
		System.out.println("");
		System.out.println("Now Playing - " + music.get(playing).address + " " + playing + " " + isplaying);
		System.out.println("PlayingFile - " + music.get(playing).file.getPath());
		System.out.println("Time : " + player.getCurrentTime().toSeconds() + " / " + player.getTotalDuration().toSeconds());
		System.out.println("");
		showMusic();
		showPlaylist();
	}
	
	static void AllSet() {
		for(int i=0; i<music.size(); i++) {
			list.get(listing).is[i] = true;
			music.get(i).check.setSelected(true);
		}
	}
	
	static void AllDeset() {
		for(int i=0; i<music.size(); i++) {
			list.get(listing).is[i] = false;
			music.get(i).check.setSelected(false);
		}
	}
	
	static void DelPlaylist() {
		if(listing==0) return;
		System.out.println(list.get(listing).name);
		list.remove(listing);
		VPlaylist.getChildren().remove(listing);
		for(int i=listing; i<list.size(); i++) {
			list.get(i).number--;
		}
		setPlaylist(0);
		showPlaylist();
		return;
	}
	
}