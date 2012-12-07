package com.nasser.cypher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.nasser.cypher.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	TextView usuario;
	TextView pass;
	Button aceptar;
	File archivoLogin;
	AssetManager as;
    InputStream is= null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario=(TextView)findViewById(R.id.txtUserName);
        pass=(TextView)findViewById(R.id.txtPassword);
        aceptar=(Button)findViewById(R.id.ButtonLog);
        as=getAssets();
               /*String estadoTarjetaExterna=Environment.getExternalStorageState();
    	if(estadoTarjetaExterna.equals(Environment.MEDIA_MOUNTED)){
    		File externalDir= Environment.getExternalStorageDirectory();
    		archivoLogin=new File(externalDir.getAbsolutePath()+File.separator+"users.dat");
    	}*/
        
        aceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TextBoxVacios())
					try {
						if(checkUserAndPass(usuario.getText().toString(), pass.getText().toString()))
						  {
							try{
								Intent i = new Intent(v.getContext(), Submenu.class);
								startActivity (i);
							}catch (Exception e){}
						}else{
							Toast t1= Toast.makeText(getApplicationContext(), "Este usuario no esta dado de alta", Toast.LENGTH_SHORT);
				    		t1.setGravity(Gravity.CENTER|Gravity.FILL_HORIZONTAL, 0, 0);
				    		t1.show();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    public boolean checkUserAndPass(String usuario,String pass) throws IOException{
    	boolean existe=false;
    	ArrayList<String> usuarios= new ArrayList<String>();
    	String comprobacion=usuario+"/"+pass;
    	/*FileReader lectura  = new FileReader(archivoLogin);*/
    	BufferedReader bufferLectura;
    	try {
    		is=as.open("users.dat");
    		InputStreamReader in=new InputStreamReader(is);
    		bufferLectura= new BufferedReader(in);
			String linea;
			while((linea=bufferLectura.readLine())!=null)
				usuarios.add(linea);
			bufferLectura.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

    	for(String datos:usuarios)
    		if(datos.equals(comprobacion))
    			existe=true;
    	else
    		existe=false;
    	return existe;
    }

    public boolean TextBoxVacios(){
    	String user=usuario.getText().toString();
    	String passUser=pass.getText().toString();
    	boolean userbool=false;
    	boolean passbool=false;
    	
    	if(user.equals("")){
    		Toast t1=Toast.makeText(getApplicationContext(), "Falta usuario",Toast.LENGTH_SHORT);
    		t1.setGravity(Gravity.CENTER, 0, 0);
    		t1.show();
    		userbool=false;
    	}else userbool=true;
    	if(passUser.equals("")){
    		Toast t1= Toast.makeText(getApplicationContext(), "Falta la contraseña", Toast.LENGTH_SHORT);
    		t1.setGravity(Gravity.CENTER|Gravity.FILL_HORIZONTAL, 0, 0);
    		t1.show();
    		passbool=false;
    	}else passbool=true;
    	
    	if((passbool==true)&(userbool)==true)
    		return true;
    		else return false;
    }

}
