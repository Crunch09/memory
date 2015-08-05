package de.thm.ateam.memory.engine.type;

import java.net.Socket;

import android.database.Cursor;

public class NetworkPlayer extends Player{

	public boolean hasToken;
	public Socket sock;


	public NetworkPlayer(Cursor c){
		super(c);
		this.sock = null;
		this.hasToken = false;
	}

	protected NetworkPlayer(){}

	public NetworkPlayer(String nick){
		super(nick);
		this.sock = null;
		this.hasToken = false;
	}
	
	public NetworkPlayer(Player p){
		this.id = p.id;
		this.nick = p.nick;
		this.win = p.win;
		this.lose = p.lose;
		this.draw = p.draw;
		this.hit = p.hit;
		this.turn = p.turn;
		this.sock = null;
		this.hasToken = false;
	}

	public NetworkPlayer(Socket sock){
		this.sock = sock;
		this.nick = "";
		this.win  = 0;
		this.lose = 0;
		this.draw = 0;
		this.hit  = 0;
		this.turn = 0;
		this.roundDraw = false;
		this.roundLose = false;
		this.roundWin = false;
		this.hasToken = false;
	}

}
