package com.example.foodieandroidforsale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewAnimator;



public class HomePageActivity extends Activity implements OnClickListener{

    public static final String TAG = "HomePage";
    private Button m_signin;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        m_signin = (Button)findViewById(R.id.button_01);
        m_signin.setOnClickListener(this);
    }



	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button_01:
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}




}
