package de.thm.ateam.memory.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import de.thm.ateam.memory.R;
import de.thm.ateam.memory.engine.type.*;
import de.thm.ateam.memory.game.GameActivity;
import de.thm.ateam.memory.game.PlayerList;
import de.thm.ateam.memory.network.MyAlertDialog.MyAlertDialogListener;

/**
 * 
 * its the activity of the "Chat"-WaitingRoom
 *
 */
public class WaitingRoomActivity extends FragmentActivity implements MyAlertDialogListener{
  private final String TAG = this.getClass().getSimpleName();

  TextView chatWindow = null;
  EditText text = null;
  Button send_btn = null;
  Button start_game_btn = null;
  TextView server_ip = null;
  WaitForChatMessages wfc = null;
  FragmentManager fm = null;
  private boolean gameHasStarted = false;
  private String serverAddress = "";
  private NetworkPlayer currentPlayer = null;
  private PrintWriter out = null;
  private BufferedReader bf = null;
  private boolean resumed = false;

  public void onDestroy(){
    super.onDestroy();
    if(serverAddress.equals("127.0.0.1")){
      /* it's the client on the server who should 
       * only be able to do this
      */ 
      stopService(new Intent(getApplicationContext(),HostService.class));
    }
    if(wfc != null) {
    	wfc.cancel(true);
    }
  }

  private class WaitForChatMessages extends AsyncTask<Void, String, Integer>{
    /**
     * wait for incoming chat messages until GameHasStarted 
     * 
     */
    @Override
    protected Integer doInBackground(Void... arg0) {
      InetAddress adr = null;
      try {
        adr = InetAddress.getByName(serverAddress);
      } catch (UnknownHostException e) {
        Log.i(TAG, "Could not find Server");
        MyAlertDialog alertDialog = new MyAlertDialog(R.string.could_not_connect_to_server);
        alertDialog.show(fm, "dialog");
      }
      try {
        Log.i(TAG, "now waiting for chat messages");
        if(currentPlayer.sock == null){
          currentPlayer.sock = new Socket(adr, 6666);
          Log.i(TAG, "New Client has connected!");
        }
        bf = new BufferedReader(new InputStreamReader(currentPlayer.sock.getInputStream()));
        String inputLine;
        
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(currentPlayer.sock.getOutputStream())), true);
        //send name to Server if this activity is not resumed
        if(!resumed){
          out.println("[nick]"+ currentPlayer.nick);
        }
        while ((inputLine = bf.readLine()) != null && !gameHasStarted) {
          publishProgress(inputLine);
          if(inputLine.equals("[start]")) break;
        }
        Log.i(TAG, "Client waiting loop has ended"+ inputLine);
      } catch (IOException e) {
        Log.e(TAG, "could not connect to server or reader threw io-exception");
        MyAlertDialog alertDialog = new MyAlertDialog(R.string.could_not_connect_to_server);
        alertDialog.show(fm, "dialog");
        e.printStackTrace();
      } 
      return 0;
    }


    @Override
    protected void onProgressUpdate(String...messages){
      Log.i(TAG, "Client received "+ messages.length + " new message(s).");
      for(String message : messages){
        if(message.startsWith("[start]")){
          gameHasStarted = true;
          findViewById(R.id.send_btn).setClickable(false);
          if(serverAddress.equals("127.0.0.1")){
            /* start Button is only available on the client on serverside */
            findViewById(R.id.start_game_btn).setClickable(false);
          }
          findViewById(R.id.chat_edit).setEnabled(false);

          final ProgressDialog dialog = new ProgressDialog(WaitingRoomActivity.this);
          dialog.setMessage("Loading...");
          dialog.setCancelable(false);
          dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          dialog.show();

          if(wfc.cancel(true)){
            Log.i(TAG, "Server is no longer waiting for clients");
          }else{
            Log.i(TAG, "Server could not be stopped.");
          }
          Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
            public void run() {
              dialog.dismiss();
              Intent i = new Intent(WaitingRoomActivity.this, GameActivity.class);
              i.putExtra("networkgame", true);
              
              //its the hosting player
              if(serverAddress.equals("127.0.0.1")){
                i.putExtra("host", true);
              }else{
                i.putExtra("host", false);
              }
              //start Game
              startActivity(i);
            }}, 3000);  // 3000 milliseconds


          if(wfc.cancel(true)){
            Log.i(TAG, "Server is no longer waiting for clients");
          }else{
            Log.i(TAG, "Server could not be stopped.");
          }
        }else{
          //append a message to the chat if it is not a command
          if(!message.startsWith("[")){
            chatWindow.append(message + "\n");
          }
        }
      }

    }


    @Override
    protected void onPostExecute(Integer v){
      Log.i(TAG, "stopped to listen for chat messages");
    }

  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle b = getIntent().getExtras();
    resumed = false;
    gameHasStarted = false;
    // check if this is client or host
    if(b != null){
      serverAddress = b.getString("serverAddress");
      setContentView(R.layout.client);
    }else{
      serverAddress = "127.0.0.1";
      setContentView(R.layout.host);
    }
    currentPlayer = PlayerList.getInstance().currentPlayer;
    fm = getSupportFragmentManager();
    chatWindow = (TextView) findViewById(R.id.chat);
    chatWindow.setMovementMethod(new ScrollingMovementMethod());
    text = (EditText) findViewById(R.id.chat_edit);
    send_btn = (Button) findViewById(R.id.send_btn);
    start_game_btn = (Button) findViewById(R.id.start_game_btn);
    server_ip = (TextView) findViewById(R.id.server_ip);

    // check if device is in a wifi
    if(serverAddress.equals("127.0.0.1")){
      WifiManager myWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
      WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
      int ipAddress = myWifiInfo.getIpAddress();
      if(myWifiInfo.getNetworkId() == -1 || ipAddress == 0){
        MyAlertDialog alertDialog = new MyAlertDialog(R.string.could_not_connect_to_server);
        alertDialog.show(fm, "dialog");
      }else{
      server_ip.setText("Your WiFi address is " + android.text.format.Formatter.formatIpAddress(ipAddress));
      }
    }
    wfc = new WaitForChatMessages();
    wfc.execute();
    
  }

  public void onButtonClick(View target){
    switch(target.getId()){
    case R.id.start_game_btn:
      Log.i(TAG, "Game about to begin...");
      // let the other players know that a game was started
      out.println("[system]Game about to begin!");
      break;
    case R.id.send_btn:
    	if(!text.getText().toString().equals("")) {
        out.println(currentPlayer.nick +": "+ text.getText());
        text.setText("");
    	}
      break;
    }
  }


  public void onFinishAlertDialog() {
    finish();
  }
  
  /**
   * a game was finished and this activity is restarted, so set everthing
   * up for a round of chatting :)
   */
  @Override
  protected void onResume(){
    super.onResume();
    if(wfc.getStatus() == AsyncTask.Status.FINISHED){
      wfc = new WaitForChatMessages();
      wfc.execute();
      Log.i(TAG, "Chat-AsyncTask was restarted");
      gameHasStarted = false;
      findViewById(R.id.send_btn).setClickable(true);
      if(serverAddress.equals("127.0.0.1")){
        findViewById(R.id.start_game_btn).setClickable(true);
        HostService.gameAvailable = true;
      }
      findViewById(R.id.chat_edit).setEnabled(true);
    }
  }
  
  protected void onPause(){
    super.onPause();
    /* now we know the activity is not started the first time ... */
    resumed = true;
  }
}
