package de.thm.ateam.memory.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;
import de.thm.ateam.memory.engine.type.*;


public class Response implements Runnable{

	private final String TAG = this.getClass().getSimpleName();

	String incMessage;
	Socket incSocket;

	public Response(String incMessage, Socket incSocket){
		this.incMessage = incMessage;
		this.incSocket = incSocket;
	}

	/**
	 * Sets the current Player to the next one, "Round Robin"
	 * @return current Player
	 */
	public NetworkPlayer nextTurn(){
		return HostService.clients.get((++HostService.current)%HostService.clients.size());
	}

	/**
	 * Gets the current Player (the one with the token) from the list
	 * @return current Player
	 */
	public NetworkPlayer currentPlayer(){
		return HostService.clients.get(HostService.current%HostService.clients.size());
	}

	/**
	 * send Responses to one or multiple players
	 */
	public void run() {
		PrintWriter out = null;
		boolean winnerIsChosen = false;
		if(incMessage.startsWith("[token]")){
			// aktueller Spieler hat seinen Zug gemacht, also nächsten Spieler benachrichtigen
			NetworkPlayer nextPlayer = nextTurn();
			// möglicherweise solange nächsten Spieler auswählen, bis einer gefunden wurde, der anwesend ist
			while(nextPlayer.afk){
				nextPlayer = nextTurn();
			}
			try {
				for(NetworkPlayer player : HostService.clients){
					if(player.sock != null){
						out = new PrintWriter(player.sock.getOutputStream(), true);
						if(player.sock.getLocalAddress().equals(nextPlayer.sock.getLocalAddress())){
							Log.i(TAG, "Server sends out new token");
							out.println("[token]");
						}else{
							out.println("[currentPlayer]"+ nextPlayer.nick);
						}
					}
				}

			} catch (IOException e) {
				Log.e(TAG, "IOException");
			}
		}else if(incMessage.startsWith("[next]")){
			Log.i(TAG, "received next token");
			NetworkPlayer p = currentPlayer();
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
			NetworkPlayer p = HostService.findPlayerBySocket(incSocket);
			p.afk = incMessage.startsWith("[afk]") ? true : false;
		}else if(incMessage.startsWith("[finish]")){
			Player winner = HostService.computeWinner();
			try {
				for(NetworkPlayer player : HostService.clients){
					if(player.sock != null){
						out = new PrintWriter(player.sock.getOutputStream(), true);
						if(winner == null){
							out.println("[finish]");
						}else{
							out.println("[finish]"+ winner.nick);
						}
					}
				}

			} catch (IOException e) {
				Log.e(TAG, "IOException");
			}
		}else if(incMessage.startsWith("[removePlayer")){
			NetworkPlayer p = HostService.findPlayerBySocket(incSocket);
			HostService.clients.remove(p);
		}else{
			/* here are the messages for all clients */
			NetworkPlayer playerWhoSentThisMessage = HostService.findPlayerBySocket(this.incSocket);
			for(NetworkPlayer player : HostService.clients){
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
						Log.i(TAG, "Sending join message to a client");
						String playerName = incMessage.substring(6, incMessage.length());
						// set nickname of this player in the client list
						if(player.sock.getLocalAddress().equals(incSocket.getLocalAddress())){
							player.nick = playerName;
						}
						out.println(playerName + " joined the Game");
					}else if(incMessage.startsWith("[delete]")){
						Log.i(TAG, "a pair was found");
						// increase round hits of the message's sender
						if(playerWhoSentThisMessage != null && playerWhoSentThisMessage.sock.getLocalAddress().equals(player.sock.getLocalAddress())){
							playerWhoSentThisMessage.roundHits += 1;
						}
						out.println(incMessage);
					}else{
						/* e.g. [flip], [field], [reset], [finish] !!! */
						out.println(incMessage);
					}
				}
			}
		}

	}

}
