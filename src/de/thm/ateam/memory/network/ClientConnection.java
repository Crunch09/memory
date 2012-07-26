package de.thm.ateam.memory.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.util.Log;


public class ClientConnection implements Runnable {
  
  private static final String TAG = ClientConnection.class.getSimpleName();
  
  public Socket sock = null;

  public ClientConnection(Socket s) {
    this.sock = s;
  }

  public void run() {
    try {
        BufferedReader in = new BufferedReader(
              new InputStreamReader(
              sock.getInputStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
          Log.i(TAG,"received Message from a client");
          Thread t = new Thread(new Response(inputLine, sock));
          t.start();
        }
        Log.i(TAG, "Server is not waiting anymore");
        
        //in.close();
        //sock.close();

    } catch (IOException e) {
        Log.e(TAG, "IOException");
    }

  }

}

