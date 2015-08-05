package de.thm.ateam.memory.network;

import de.thm.ateam.memory.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * Activity contains a form which is used to enter the ip-address of a host
 *
 */
public class EnterIpAddress extends Activity{
  
  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.enterip);
  }
  
  public void onButtonClick(View target){
    switch(target.getId()){
    case R.id.submit_ip_btn:
      EditText txt = (EditText)findViewById(R.id.ip);
      /* return ip to ChooseClientOrHost Activity */
      setResult(RESULT_OK, getIntent().putExtra("ip", txt.getText().toString()));
      finish();
    }
  }

}
