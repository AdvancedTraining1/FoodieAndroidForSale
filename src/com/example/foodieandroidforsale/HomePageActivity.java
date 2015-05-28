package com.example.foodieandroidforsale;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewAnimator;



public class HomePageActivity extends Activity implements OnClickListener{

    public static final String TAG = "HomePage";
    private Button m_signin;
    private Button m_publish;
    private Button m_bulk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        m_signin = (Button)findViewById(R.id.button_01);
        m_publish = (Button)findViewById(R.id.button_02);
        m_bulk = (Button)findViewById(R.id.button_03);
        m_signin.setOnClickListener(this);
        m_publish.setOnClickListener(this);
        m_bulk.setOnClickListener(this);
    }



	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button_01:
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
			break;
		case R.id.button_02:
			Intent j = new Intent(getApplicationContext(), PublishCouponsActivity.class);
            startActivityForResult(j, 0);
			break;
		case R.id.button_03:
			Intent k = new Intent(getApplicationContext(), BulkCoupons.class);
            startActivityForResult(k, 0);
			break;
		default:
			break;
		}
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_OK) {
            	//读取存放的数据
                SharedPreferences preferences = getSharedPreferences("Text", 0);
                //displayContent.setText(preferences.getString("text", null));
                //Toast.makeText(HomePageActivity.this, preferences.getString("title", null),Toast.LENGTH_LONG ).show();
                //Toast.makeText(HomePageActivity.this, preferences.getString("content", null),Toast.LENGTH_LONG ).show();
            }
            if(resultCode == 998) {
            	//读取存放的数据
                SharedPreferences preferences = getSharedPreferences("Text", 0);
                //displayContent.setText(preferences.getString("text", null));
                Toast.makeText(HomePageActivity.this, preferences.getString("title", null),Toast.LENGTH_LONG ).show();
                Toast.makeText(HomePageActivity.this, preferences.getString("content", null),Toast.LENGTH_LONG ).show();
            }
            	
         }
        	
    }


}
