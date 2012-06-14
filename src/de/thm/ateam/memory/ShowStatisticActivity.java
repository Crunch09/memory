package de.thm.ateam.memory;



import java.sql.Date;
import java.util.ArrayList;

import java.util.List;


import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;
import android.app.Activity;
import android.os.Bundle;


public class ShowStatisticActivity extends Activity{
  
  List<Player> players = new ArrayList<Player>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    WinningProbabilityChart chart = new WinningProbabilityChart();
    startActivity((chart.getIntent(this)));
  }

  
}
