package com.nasser.cypher;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Desencriptar extends Activity {
	Button volver;
	EditText mensajecodificado;
	EditText mensajedecodificado;
	String codSelected="";
	SHA_256 codificador;
	byte[] mensajeCod;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mensajeCod=getIntent().getByteArrayExtra("mensaje");
		setContentView(R.layout.desencriptar_layout);
		Spinner spiner=(Spinner)findViewById(R.id.spinner1);
		volver=(Button)findViewById(R.id.volver);
		mensajecodificado=(EditText)findViewById(R.id.textoEncriptado);
		mensajecodificado.setEnabled(false);
		mensajecodificado.setFocusable(false);
		mensajecodificado.setClickable(false);
		
		mensajedecodificado=(EditText)findViewById(R.id.textoDesencriptado);
		mensajedecodificado.setEnabled(false);
		mensajedecodificado.setFocusable(false);
		mensajedecodificado.setClickable(false);
		
		ArrayList <String> tipoEncriptadores= new ArrayList<String>();
		tipoEncriptadores.add("SHA-256");
		ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoEncriptadores);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spiner.setAdapter(spinner_adapter);
		
		mensajecodificado.setText(mensajeCod.toString());
		
		spiner.setOnItemSelectedListener(new OnItemSelectedListener() {
		
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if(parent.getId()==R.id.spinner1){
					codSelected=(String)parent.getItemAtPosition(pos);
					if(codSelected.equals("SHA-256"))
						codificador=new SHA_256();
				}
				
				try {
					
					mensajecodificado.setText(mensajeCod.toString());
					
					mensajedecodificado.setText(codificador.desencriptar(mensajeCod));
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		volver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}