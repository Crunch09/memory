package de.thm.ateam.memory.game;

import android.content.Context;
import android.util.Log;
import de.thm.ateam.memory.engine.MemoryPlayerDAO;
import de.thm.ateam.memory.engine.type.Player;

public class StatsUpdate implements Runnable {
  
  Context ctx;
  
  public StatsUpdate(Context ctx){
    this.ctx = ctx;
  }

  public void run() {
    MemoryPlayerDAO playerDAO = MemoryPlayerDAO.getInstance(ctx);
    for(Player p : PlayerList.getInstance().session){
      if(p.roundLose){
        p.lose++;
      }else if(p.roundDraw){
        p.draw++;
      }else{
        p.win++;
      }
      p.hit += p.roundHits;
      p.turn += p.roundTurns;
      // reset the round stats
      p.roundLose = false;
      p.roundDraw = false;
      p.roundWin =  false;
      p.roundHits = 0;
      p.roundTurns = 0;
      p.updatePlayer(playerDAO);
    }
  }

}
