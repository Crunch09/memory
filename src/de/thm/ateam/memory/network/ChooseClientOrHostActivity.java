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


  public void onButtonClick(View target){
    Intent i = null;
    switch(target.getId()){
    case R.id.btn_host:
      //if(startService(new Intent(getApplicationContext(),HostService.class))){
      startService(new Intent(getApplicationContext(),HostService.class));
      i = new Intent(this, WaitingRoomActivity.class);
      startActivity(i);
      //}else{
      //  Log.i("demo", "Service could not be started.");
      //}
      break;
    case R.id.btn_client:
      i = new Intent(this, EnterIpAddress.class);
      startActivityForResult(i, IPFORM);
      break;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
      Intent data){
    Log.i(TAG, "Adresse wurde eingegeben und empfangen");
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
