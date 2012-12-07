package com.nasser.cypher;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Submenu extends Activity {
	Button Encriptar;
	Button Desencriptar;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submenu);
		Encriptar=(Button)findViewById(R.id.button1);
		Desencriptar=(Button)findViewById(R.id.button2);
		
		Encriptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					v.getContext();
					Intent i = new Intent(v.getContext(), Encriptar.class);
					startActivity(i);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		Desencriptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					Intent intent= new Intent(v.getContext(),MenuDesencriptar.class);
					startActivity(intent);
				}catch (Exception ex){}
				
			}
		});
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_login, menu);
	        return true;
	    }
}
