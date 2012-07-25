package de.thm.ateam.memory.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.thm.ateam.memory.ImageAdapter;
import de.thm.ateam.memory.R;
import de.thm.ateam.memory.Theme;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.Card;
import de.thm.ateam.memory.game.Game;
import de.thm.ateam.memory.game.MemoryAttributes;
import de.thm.ateam.memory.game.PlayerList;

public class NetworkMemory extends Game{

  Context ctx;
  private MemoryAttributes attr;
  private Activity envActivity;
  private static NetworkMemory instance = null;
  private int ROW_COUNT = 0;
  private int COL_COUNT = 0;
  private int left; //how many cards are left
  private int card = -1; //should better be an object of card
  private int numberOfPicks = 0;
  boolean host;
  private GridView mainView;
  private Theme theme;
  public ImageAdapter imageAdapter;
  public TextView infoView;
  private final String TAG = this.getClass().getSimpleName();
  Player currentPlayer = null;
  int numberOfClicks = 0;
  PrintWriter out = null;
  private UpdateCardsHandler resHandler;
  private DeleteCardsHandler delHandler;
  private static Object lock = new Object(); //synchronous actions suck.



  private NetworkMemory(Context ctx, MemoryAttributes attr) {
    super(ctx, attr);
    this.ctx = ctx;
    this.attr = attr;
    this.envActivity = (Activity)ctx;
  }
  
  public static NetworkMemory getInstance(Context ctx, MemoryAttributes attr){
    if(instance != null)return instance;
    instance = new NetworkMemory(ctx, attr);
    return instance;
  }



  public void newGame(){
    ROW_COUNT = attr.getRows();
    COL_COUNT = attr.getColumns();
    left = ROW_COUNT * COL_COUNT;
    currentPlayer = PlayerList.getInstance().currentPlayer;
    host = envActivity.getIntent().getExtras().getBoolean("host");
    resHandler = new UpdateCardsHandler();
    delHandler = new DeleteCardsHandler();
    try {
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(currentPlayer.sock.getOutputStream())), true);
    } catch (IOException e) {
      Log.e(TAG, "couldn't open outputStream");
      e.printStackTrace();
    }
    
    
    //infoView.setText(PlayerList.getInstance().currentPlayer.nick);
  }
  
  public String createField(){
    ROW_COUNT = attr.getRows();
    COL_COUNT = attr.getColumns();
    imageAdapter = new ImageAdapter(ctx, ROW_COUNT, COL_COUNT);
    imageAdapter.shuffleImages();
    String field = "";
    for(Card[]c : imageAdapter.getPositions()){
      field += c[0] +";"+ c[1] +"Ende";
    }
    return field;
  }


  public View assembleLayout(){
    infoView = new TextView(ctx);
    infoView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.05f)); //this is fixed value, should be fine though
    infoView.setText(R.string.initialize);

    newGame();

    theme = imageAdapter.getTheme();
    
    if(host){
      //host player hat am Anfang das Token
      currentPlayer.hasToken = true;
      
    }

    mainView = new GridView(ctx);
    mainView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f)); //relations are 1 to .05
    mainView.setNumColumns(attr.getColumns());
    mainView.getLayoutParams().width = imageAdapter.maxSize();
    mainView.setAdapter(imageAdapter);
    mainView.setOnItemClickListener(new MemoryClickListener());


    LinearLayout linLay = new LinearLayout(envActivity.getApplicationContext());
    linLay.setOrientation(LinearLayout.VERTICAL);
    linLay.addView(infoView);
    linLay.addView(mainView);
    return linLay;
  }
  
  /**
   * 
   * Function which flips the card on the position
   * 
   * @param position
   */
  public void flip(int position) {
    ImageView clicked = (ImageView) imageAdapter.getItem(position);
    clicked.setImageBitmap(theme.getPicture(clicked.getId()));

  }

  public void onDestroy() {
    for(int i = 0; i < theme.getCount(); i++) {
      if(theme.getPicture(i) != null)
        theme.getPicture(i).recycle();
    }
  }


  /**
   * Function is exclusively used to turn a pair of cards to the back side.
   * Launches a TimedTask, a ResetTask to be exact.
   * 
   * @param pos
   * @param pos2
   * 
   */
  public void reset(int pos, int pos2) {

    ResetTask task = new ResetTask(pos, pos2);
    Timer t = new Timer(false);
    t.schedule(task, 1000);

  }

  /**
   * Function is exclusively used to make a pair of cards invisible and unclickable.
   * Launches a TimedTask, a DeleteTask to be exact.
   * 
   * @param pos
   * @param pos2
   * 
   */
  public void delete(int pos, int pos2) {

    DeleteTask task = new DeleteTask(pos, pos2);
    Timer t = new Timer(false);
    t.schedule(task, 1000);

  }

  // TODO add java doc
  class ResetTask extends TimerTask  {
    private int pos, pos2;
    public ResetTask(int pos, int pos2) {
      this.pos = pos;
      this.pos2 = pos2;
    }

    @Override
    public void run() {
      Bundle data = new Bundle();
      data.putInt("pos", pos);
      data.putInt("pos2", pos2);
      Message msg = new Message();
      msg.setData(data);
      resHandler.sendMessage(msg);
    }
  }

  class DeleteTask extends TimerTask {
    private int pos, pos2;

    public DeleteTask(int pos, int pos2){
      this.pos = pos;
      this.pos2 = pos2;
    }

    @Override
    public void run(){
      Bundle data = new Bundle();
      data.putInt("pos", pos);
      data.putInt("pos2", pos2);
      Message msg = new Message();
      msg.setData(data);
      delHandler.sendMessage(msg);
    }
  }

  @SuppressLint("HandlerLeak")
  class UpdateCardsHandler extends Handler{
    @Override
    public  void handleMessage(Message msg) {
      synchronized (lock) {
        doJob(msg);
      }
    }
    public void doJob(Message msg){
      ImageView clicked = (ImageView) imageAdapter.getItem(msg.getData().getInt("pos"));
      clicked.setImageBitmap(theme.getBackSide());
      clicked = (ImageView) imageAdapter.getItem(msg.getData().getInt("pos2"));
      clicked.setImageBitmap(theme.getBackSide());
      numberOfPicks = 0;
    }
  }

  @SuppressLint("HandlerLeak")
  class DeleteCardsHandler extends Handler{
    @Override
    public  void handleMessage(Message msg) {
      synchronized (lock) {
        doJob(msg);
      }
    }
    public void doJob(Message msg){
      ImageView clicked = (ImageView) imageAdapter.getItem(msg.getData().getInt("pos"));
      clicked.setImageBitmap(null);
      clicked.setEnabled(false);

      clicked = (ImageView) imageAdapter.getItem(msg.getData().getInt("pos2"));
      clicked.setImageBitmap(null);
      clicked.setEnabled(false);
      numberOfPicks = 0; 
    }
  }


  class MemoryClickListener implements OnItemClickListener {
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
      if(!currentPlayer.hasToken){
        return;
      }
      numberOfPicks++;
      if(numberOfPicks > 2){
        return;
      }

      /*
       * simple recognition of hits or misses,
       * must be overridden by a round-based player system
       */

      synchronized (lock) { //just to avoid unwanted behavior
        if(card == -1){
          out.println("[flip]"+ position);
          card = position;
          /* Increase the number of turns */
          currentPlayer.turn();
          //Toast.makeText(ctx,"select " +position+ " first move", Toast.LENGTH_SHORT).show();
        }else{ 
          if(card != position) {
            numberOfPicks++;
            out.println("[flip]"+ position);
            
            
            if(imageAdapter.getItemId(card) == imageAdapter.getItemId(position)){
              out.println("[delete]"+ position +","+ card); 
              //Toast.makeText(ctx,"card "+ " select " +position+ " hit, next player", Toast.LENGTH_SHORT).show();
              card = -1;
              //current.hit();
              left -= 2;
              if(left<=0){
                //Memory.this.getWinner();
                numberOfPicks = 0;
                String victoryMsg = "";
                out.println("[finish]");
//                for(Player p : attr.getPlayers()){
//                  if(p.roundWin)
//                    victoryMsg += p.nick + ",";
//                }
//                // deletes last comma if there is a winner
//                if(!victoryMsg.equals("")){
//                  victoryMsg = victoryMsg.substring(0, victoryMsg.length()-1);
//                  victoryMsg += " has won!!!";
//                }else{
//                  victoryMsg = "Last round was a draw.";
//                }
//
//                Thread t = new Thread(new StatsUpdate(envActivity.getApplicationContext()));
//                t.run();
              }


            }else{
              currentPlayer.hasToken = false;
              //Toast.makeText(ctx,"card "+ " select " +position+ "miss, next player", Toast.LENGTH_SHORT).show();
              out.println("[reset]"+ position +","+ card);
              card = -1;
              //Token verschicken
              
              out.println("[token]");
              //current = turn();
              //infoView.setText(current.nick);
            }
          }
        }
      }

    }
  }



}
