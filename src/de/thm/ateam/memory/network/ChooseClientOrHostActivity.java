package de.thm.ateam.memory.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import de.thm.ateam.memory.R;

public class ChooseClientOrHostActivity extends Activity{
  
  private final String TAG = this.getClass().getSimpleName();
  private static final int IPFORM = 1;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.client_or_host);
  }

  /**
   * Is triggered when the user chooses to be host or client
   * @param target the button which was touched
   */
  public void onButtonClick(View target){
    Intent i = null;
    switch(target.getId()){
    case R.id.btn_host:
      // TODO: check if service could be started
      /* start Service */
      startService(new Intent(getApplicationContext(),HostService.class));
      i = new Intent(this, WaitingRoomActivity.class);
      startActivity(i);
      break;
    case R.id.btn_client:
      i = new Intent(this, EnterIpAddress.class);
      startActivityForResult(i, IPFORM);
      break;
    }
  }
  
  /**
   * Returns from EnterIpAddress-Activity
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode,
      Intent data){
    Log.i(TAG, "Adress was entered and received");
    if(requestCode == IPFORM){
      if(resultCode == RESULT_OK){
        Bundle b = data.getExtras();
        String ip = b.getString("ip");
        if(!ip.equals("")){
          Intent i = new Intent(this, WaitingRoomActivity.class);
          i.putExtra("serverAddress", ip);
          startActivity(i);
        }
      }
    }
  }
}
