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
    private Button m_dishorder;
<<<<<<< HEAD
    private Button m_serSeatNum;
    private Button btn_register;
    private Button btn_login;;
=======
    private Button m_seatnum;
>>>>>>> 0a131d6e529872fcd1442402b941ce1d2be95977

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        m_signin = (Button)findViewById(R.id.button_01);
        m_publish = (Button)findViewById(R.id.button_02);
        m_bulk = (Button)findViewById(R.id.button_03);
<<<<<<< HEAD
        m_dishorder = (Button)findViewById(R.id.btn_dishorder);
        m_serSeatNum = (Button)findViewById(R.id.btn_seatnum);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_register);
=======
        m_dishorder = (Button)findViewById(R.id.button_04);
        m_seatnum = (Button)findViewById(R.id.button_05);
>>>>>>> 0a131d6e529872fcd1442402b941ce1d2be95977
        m_signin.setOnClickListener(this);
        m_publish.setOnClickListener(this);
        m_bulk.setOnClickListener(this);
        m_dishorder.setOnClickListener(this);
<<<<<<< HEAD
        m_serSeatNum.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
=======
        m_seatnum.setOnClickListener(this);
>>>>>>> 0a131d6e529872fcd1442402b941ce1d2be95977
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
		case R.id.button_04:
			Intent d = new Intent(getApplicationContext(), DishOrderActivity.class);
            startActivityForResult(d, 0);
<<<<<<< HEAD
        case R.id.btn_seatnum:
        	Intent s = new Intent(getApplicationContext(), AddSeatNumActivity.class);
            startActivityForResult(s, 0);
        case R.id.btn_login:
        	Intent a = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(a, 0);
        case R.id.btn_register:
        	Intent b = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivityForResult(b, 0);

  
			
=======
            break;
        case R.id.button_05:
        	Intent s = new Intent(getApplicationContext(), AddSeatNumActivity.class);
            startActivityForResult(s, 0);
            break;
>>>>>>> 0a131d6e529872fcd1442402b941ce1d2be95977
		default:
			break;
		}
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_OK) {
            	//��ȡ��ŵ����
                SharedPreferences preferences = getSharedPreferences("Text", 0);
                //displayContent.setText(preferences.getString("text", null));
                //Toast.makeText(HomePageActivity.this, preferences.getString("title", null),Toast.LENGTH_LONG ).show();
                //Toast.makeText(HomePageActivity.this, preferences.getString("content", null),Toast.LENGTH_LONG ).show();
            }
            if(resultCode == 998) {
            	//��ȡ��ŵ����
                SharedPreferences preferences = getSharedPreferences("Text", 0);
                //displayContent.setText(preferences.getString("text", null));
                Toast.makeText(HomePageActivity.this, preferences.getString("title", null),Toast.LENGTH_LONG ).show();
                Toast.makeText(HomePageActivity.this, preferences.getString("content", null),Toast.LENGTH_LONG ).show();
            }
            	
         }
        	
    }


}
