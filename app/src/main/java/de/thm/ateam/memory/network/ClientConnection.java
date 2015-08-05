package de.thm.ateam.memory.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.util.Log;

/**
 * 
 * Used to handle a connection between a client and a server
 *
 */
public class ClientConnection implements Runnable {
  
  private final String TAG = this.getClass().getSimpleName();
  
  public Socket sock = null;

  public ClientConnection(Socket s) {
    this.sock = s;
  }

  /**
   * waits for incoming messages from a specific socket
   */
  public void run() {
    try {
        BufferedReader in = new BufferedReader(
              new InputStreamReader(
              sock.getInputStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
          Log.i(TAG,"received Message from a client");
          /* let a Response-Thread handle the answer */
          Thread t = new Thread(new Response(inputLine, sock));
          t.start();
        }
        Log.i(TAG, "Server is not waiting anymore");

    } catch (IOException e) {
        Log.e(TAG, "IOException");
    }

  }

}

