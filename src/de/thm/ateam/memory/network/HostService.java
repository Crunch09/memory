package de.thm.ateam.memory.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import de.thm.ateam.memory.engine.type.Player;


public class HostService extends Service{

  private static final String TAG = HostService.class.getSimpleName();

  public static ArrayList<Player> clients = null;
  public static int current = 0;
  private static boolean gameAvailable = false;
  public static int afkCount = 0;
  @Override
  public IBinder onBind(Intent arg0) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId){
    Log.i(TAG, "Starte Server Service!");
    clients = new ArrayList<Player>();
    gameAvailable = true;
    Thread t = new Thread(new ServerTask());
    t.start();

    return START_NOT_STICKY;
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
            break;
          }
          Log.i(TAG, "A Client has connected!");
          clients.add(new Player(clientSock));
          //prüfen ob schon ein Thread existiert
          Thread t = new Thread(new ClientConnection(clientSock));
          t.start();
        }

      } catch (IOException e) {
        Log.e(TAG, "Server: Port 6666 could not be used ");
      }

    }

  }

}
