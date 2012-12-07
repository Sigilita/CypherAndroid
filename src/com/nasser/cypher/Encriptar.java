package com.nasser.cypher;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.io.*;

public class Encriptar extends Activity {
	Button aceptar;
	Button guardar;
	EditText mensaje;
	EditText mensajeCodificado;
	SHA_256 codificador;
	String codSelected="";
	String NombreArchivoAGuardar="01.dat";
	FileOutputStream fos;
	BufferedOutputStream bos;
	byte[] mensajebyte;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.encriptar_layout);
		Spinner spiner=(Spinner)findViewById(R.id.spinner1);
		aceptar=(Button)findViewById(R.id.button1);
		guardar=(Button)findViewById(R.id.button2);
		mensaje=(EditText)findViewById(R.id.editText1);
		mensajeCodificado=(EditText)findViewById(R.id.editText2);
		mensajeCodificado.setEnabled(false);
		mensajeCodificado.setFocusable(false);
		mensajeCodificado.setClickable(false);
		
		ArrayList <String> tipoEncriptadores= new ArrayList<String>();
		tipoEncriptadores.add("SHA-256");
		ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoEncriptadores);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spiner.setAdapter(spinner_adapter);
		spiner.setOnItemSelectedListener(new OnItemSelectedListener() {
		
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if(parent.getId()==R.id.spinner1){
					codSelected=(String)parent.getItemAtPosition(pos);
					if(codSelected.equals("SHA-256"))
						codificador=new SHA_256();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		aceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String textoAguardar=mensaje.getText().toString();
				try {
					mensajebyte=codificador.encode(textoAguardar);
					String mensaje=mensajebyte.toString();
					mensajeCodificado.setText(mensaje);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		guardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String sdmontada=Environment.getExternalStorageState();
					if(!sdmontada.equals(Environment.MEDIA_MOUNTED)){
						//NO HAY SD
					}
					File directorioInterno=Environment.getExternalStorageDirectory();
					File archivo= new File(directorioInterno.getAbsolutePath()+File.separator+"01.dat");
					fos= new FileOutputStream(archivo,true);
					bos= new BufferedOutputStream(fos);
					bos.write(mensajebyte, 0, mensajebyte.length);
					bos.write(13);
					bos.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
}
