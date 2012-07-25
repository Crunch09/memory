package de.thm.ateam.memory.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;
import de.thm.ateam.memory.engine.type.Player;


public class Response implements Runnable{

  private static final String TAG = Response.class.getSimpleName();

  String incMessage;
  Socket sock;

  public Response(String incMessage, Socket sock){
    this.incMessage = incMessage;
    this.sock = sock;
  }

  public Player nextTurn(){
    return HostService.clients.get((++HostService.current)%HostService.clients.size());
  }

  public Player currentPlayer(){
    return HostService.clients.get(HostService.current%HostService.clients.size());
  }

  public void run() {
    PrintWriter out = null;
    if(incMessage.startsWith("[token]")){
      // aktueller Spieler hat seinen Zug gemacht, also nächsten Spieler benachrichtigen
      Player p = nextTurn();
      // möglicherweise solange nächsten Spieler auswählen, bis einer gefunden wurde, der anwesend ist
      while(p.afk){
        p = nextTurn();
      }
      try {
        out = new PrintWriter(p.sock.getOutputStream(), true);
        Log.i(TAG, "Server sends out new token");
        out.println("[token]");
      } catch (IOException e) {
        Log.e(TAG, "IOException");
      }
    }else if(incMessage.startsWith("[next]")){
      Log.i(TAG, "received next token");
      Player p = currentPlayer();
      try {
        out = new PrintWriter(p.sock.getOutputStream(), true);
        out.println("[next]");
        p = nextTurn();
        Log.i(TAG, "Server sends out new token");
        out = new PrintWriter(p.sock.getOutputStream(), true);
        out.println("[token]");
      } catch (IOException e) {
        Log.e(TAG, "IOException");
      }
    }else if(incMessage.startsWith("[afk]") || incMessage.startsWith("[resume]")){
      Log.i(TAG, "Player switched AFK status");
      for(Player p : HostService.clients){
        if(p.sock.getLocalAddress().equals(sock.getLocalAddress())){
          if(incMessage.startsWith("[afk]")){
            p.afk = true;
          }else{
            p.afk = false;
          }
          break;
        }
      }
    }else{
      for(Player player : HostService.clients){
        if(player.sock != null){
          try {
            out = new PrintWriter(player.sock.getOutputStream(), true);
          } catch (IOException e) {
            Log.e(TAG, "IOException while opening writer");
          }
          if(incMessage.startsWith("[system]")){
            Log.i(TAG, "Sending start message to a client");
            out.println("[start]");
          }else if(incMessage.startsWith("[nick]")){
            Log.i(TAG, "Sending join message to clients");
            String playerName = incMessage.substring(6, incMessage.length());
            if(player.sock.getLocalAddress().equals(sock.getLocalAddress())){
              player.nick = playerName;
              out.println(playerName + " joined the Game");
            }
          }else{
            /* z.B. [flip], [delete], [field], [reset] !!! */
            out.println(incMessage);
            //out.close();
          }
        }
      }
    }

  }

}
