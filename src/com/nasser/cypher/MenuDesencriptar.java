package com.nasser.cypher;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MenuDesencriptar extends Activity {
	String NombreArchivoAGuardar="01.dat";
	FileInputStream fis;
	BufferedInputStream bis;
	byte[] mensajebyte;
	ArrayList <byte[]> listaDeMensajes= new ArrayList<byte[]>();
	ArrayList<String> Enunciado=new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.decrip);
		 ListView list1 = (ListView) findViewById(R.id.list);
		//ListView listView = (ListView) findViewById(R.id.listView1);
		try {
			String sdmontada=Environment.getExternalStorageState();
			if(!sdmontada.equals(Environment.MEDIA_MOUNTED)){
				//NO HAY SD
			}
			File directorioInterno=Environment.getExternalStorageDirectory();
			File archivo= new File(directorioInterno.getAbsolutePath()+File.separator+"01.dat");
			FileInputStream fis = new FileInputStream(archivo);
			BufferedInputStream bis = new BufferedInputStream(fis);
			mensajebyte=new byte[(int) archivo.length()];
			int i=0;
			int cont=0;
			while((i=bis.read())!=-1){
				mensajebyte[cont]=(byte) i;
				cont++;
			}
				
			bis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int cont=0;
		byte[]aux= new byte[32];
		for(int i=0;i<mensajebyte.length;i++){
			if(mensajebyte[i]==13){
				listaDeMensajes.add(aux);
				cont=0;
			}else{
			aux[cont]=mensajebyte[i];
			cont++;
			}
		}
		cont=0;
		for(byte[] b:listaDeMensajes){
			
			Enunciado.add("Mensaje " + cont);
			cont++;
		}
			
		
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,Enunciado);
       list1.setAdapter(adapter);
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				try{
					Intent i = new Intent(getApplicationContext(),Desencriptar.class);
					i.putExtra("mensaje", listaDeMensajes.get(position));
					startActivity(i);
					
				}catch (Exception e)
				{
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
