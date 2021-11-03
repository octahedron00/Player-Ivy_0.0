package application;

public class TimeBar implements Runnable {

	@Override
	public void run() {
		while(true) {
			if(!MainAdd.TimeBar.isPressed()) MainAdd.TimeBar.setValue((MainAdd.player.getCurrentTime().toSeconds()/MainAdd.player.getTotalDuration().toSeconds())*100);
			MainAdd.TTime.setText((int)MainAdd.player.getCurrentTime().toMinutes() + ":" + (int)(MainAdd.player.getCurrentTime().toSeconds()%60)/10 + (int)MainAdd.player.getCurrentTime().toSeconds()%10 + " / " + (int)MainAdd.player.getTotalDuration().toMinutes() + ":" + (int)(MainAdd.player.getTotalDuration().toSeconds()%60)/10 + (int)MainAdd.player.getTotalDuration().toSeconds()%10);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
