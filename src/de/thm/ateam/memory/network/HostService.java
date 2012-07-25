package de.thm.ateam.memory.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import de.thm.ateam.memory.engine.type.*;


public class HostService extends Service{

  private static final String TAG = HostService.class.getSimpleName();

  public static ArrayList<NetworkPlayer> clients = null;
  public static int current = 0;
  public static boolean gameAvailable = false;
  public static int afkCount = 0;
  @Override
  public IBinder onBind(Intent arg0) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId){
    Log.i(TAG, "Starte Server Service!");
    clients = new ArrayList<NetworkPlayer>();
    gameAvailable = true;
    Thread t = new Thread(new ServerTask());
    t.start();

    return START_NOT_STICKY;
  }


  public static NetworkPlayer findPlayerBySocket(Socket sock){
    if(clients.size() == 0) return null;
    for(NetworkPlayer p : clients){
      if(p.sock != null && p.sock.getLocalAddress().equals(sock.getLocalAddress())){
        return p;
      }
    }
    return null;
  }

  @Override
  public void onDestroy(){
    Log.i(TAG, "SERVICE WAS STOPPED!");
    super.onDestroy();
    stopSelf();
  }

  private class ServerTask implements Runnable{

    public void run() {
      try {
        ServerSocket servSock = new ServerSocket(6666);

        Log.i(TAG, "Server is waiting for clients...");

        while(true){
          Socket clientSock = servSock.accept();
          if(!gameAvailable){
            Log.i(TAG, "Client tried to connect, but Server is full.");
          }else{
            Log.i(TAG, "A Client has connected!");
            clients.add(new NetworkPlayer(clientSock));
            //prüfen ob schon ein Thread existiert
            Thread t = new Thread(new ClientConnection(clientSock));
            t.start();
          }
        }

      } catch (IOException e) {
        Log.e(TAG, "Server: Port 6666 could not be used ");
      }

    }

  }

  public static Player computeWinner() {
    if(clients.size() == 0) return null;
    int highscore = 0;
    Player winner = null;
    int numberOfWinners = 0;
    for(Player p : clients){
      if(p.roundHits > highscore){
        highscore = p.roundHits;
        numberOfWinners = 1;
        winner = p;
      }else if(p.roundHits == highscore){
        numberOfWinners++;
      }
      //reset it for the next round
      p.roundHits = 0;
    }
    if(numberOfWinners == 1){
      return winner;
    }
    return null;

  }

}
