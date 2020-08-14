package com.example.taller_2_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class UserDashBoard extends AppCompatActivity {

    private ListView listView;
    private Spinner spBase1,spBase2,spJugo1,spJugo2;
    private FloatingActionButton fabAdd;
    private TextInputLayout tilNomCoct;
    private Button btnBuscar;
    private List <String> cocteles;
    private List <String> baseCoctel,basesJugo;
    private String nomCoct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        // Referencias WIDGETS es lo mismo que el getElementById de JS para HTML
        listView = (ListView) findViewById(R.id.lvRegistros);
        spBase1 = findViewById(R.id.spBasesCoct);
        spBase2 = findViewById(R.id.spiBasesCoct2);
        spJugo1 = findViewById(R.id.spJugo1);
        spJugo2 = findViewById(R.id.spJugo2);
        tilNomCoct = findViewById(R.id.til_nom_coct);
        btnBuscar = findViewById(R.id.btnBuscar);
        fabAdd = findViewById(R.id.fabAdd);


        //LISTA de DATOS
        baseCoctel = new ArrayList<String>();
        baseCoctel.add("Ron");
        baseCoctel.add("Tequila");
        baseCoctel.add("Pisco");
        baseCoctel.add("Gin");

        basesJugo = new ArrayList<String>();
        basesJugo.add("Piña");
        basesJugo.add("Limon");
        basesJugo.add("Frutilla");
        basesJugo.add("Frambuesa");

        //METODO LISTAR DATOS DE LA DB
        cocteles = new ArrayList<String>();
        //Se pobla con datos Samples
        //cocteles.add("Mojito");
        //cocteles.add("Pisco Sour");
        //cocteles.add("Tequila Sour");
        //cocteles.add("Cuba Libre");
        //cocteles.add("Tegroni");
        //cocteles.add("Negroni");
        //cocteles.add("Amaretto Sour");
        //cocteles.add("Gin Fizz");
        //cocteles.add("Tequila Sonrise");
        cocteles = listarDatos();


        //___________________________________________SPINNER_____________________________________
        //GENERAMOS UN ADAPTER PARA CADA SPINNER
        ArrayAdapter<String> adapterBaseCoct = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,baseCoctel);
        ArrayAdapter<String> adapterJugoCoct = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,basesJugo);
        //ASIGNAR ADAPTER A SPINNER
        spBase1.setAdapter(adapterBaseCoct);
        spBase2.setAdapter(adapterBaseCoct);
        spJugo1.setAdapter(adapterJugoCoct);
        spJugo2.setAdapter(adapterJugoCoct);
        //___________________________________________SPINNER_____________________________________



        //ADAPTADOR es la forma visual de renderizar los datos
        //se crea un adapter, un adaptador es la forma de mostrar los datos, como se renderizara (un template de render)
        //__________________________________________INICIO LISTVIEW SIMPLE ADAPTER____________________________________________
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,registros);
        listView.setAdapter(adapter);*/


        //__________________________________________FIN LISTVIEW SIMPLE ADAPTER____________________________________________

        //__________________________________________LISTVIEW CUSTON ADAPTER____________________________________________
        AdaptadorCocteles adaptadorCocteles = new AdaptadorCocteles(this,R.layout.list_item_coctel,cocteles);
        listView.setAdapter(adaptadorCocteles);
        //__________________________________________LISTVIEW CUSTON ADAPTER____________________________________________

        //EVENTO CLICK
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) {
                //Toast.makeText(UserDashBoard.this,"Clicked"+registros.get(posicion),Toast.LENGTH_SHORT).show();
                //EJECUCION PARA CAMBIAR DE LAYOUT
                Intent intent = new Intent(view.getContext(),RegistroElement.class);
                intent.putExtra("elemRegistro",cocteles.get(posicion));
                startActivity(intent);
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),NewCoctel.class);
                startActivity(intent);
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomCoct = tilNomCoct.getEditText().getText().toString();
                buscarDatos(nomCoct);
            }
        });
        //EVENTO KEY UP



    }
    public List<String> listarDatos(){
        List<String> cocteles = new ArrayList<String>();
        DBhelper dBhelper =  new DBhelper(this,"DataBase_CIISA",null,1);
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        if(db != null){
            Cursor cr = db.rawQuery("SELECT * FROM tbl_datos",null);
            if(cr.moveToFirst()){
                do{
                    //SE POBLA VECTOR COCTEL
                    String base1,base2,jugo1,jugo2,nomCoct;
                    nomCoct = cr.getString(1);
                    base1 = cr.getString(2);
                    base2 = cr.getString(3);
                    jugo1 = cr.getString(4);
                    jugo2 = cr.getString(5);
                    cocteles.add(nomCoct+" "+base1+" "+base2+" "+jugo1+" "+jugo2);
                }while(cr.moveToNext());
            }
        }
        return cocteles;
    }
    public void buscarDatos(String nom_coctel){
        List<String> cocteles = new ArrayList<String>();
        DBhelper dBhelper =  new DBhelper(this,"DataBase_CIISA",null,1);
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        if(db != null){
            Cursor cr = db.rawQuery("SELECT * FROM tbl_datos WHERE nombre_coctel LIKE  '"+nom_coctel+"%'",null);
            if(cr.moveToFirst()){
                do{
                    //SE POBLA VECTOR COCTEL
                    String base1,base2,jugo1,jugo2,nomCoct;
                    nomCoct = cr.getString(1);
                    base1 = cr.getString(2);
                    base2 = cr.getString(3);
                    jugo1 = cr.getString(4);
                    jugo2 = cr.getString(5);
                    cocteles.add(nomCoct+" "+base1+" "+base2+" "+jugo1+" "+jugo2);
                }while(cr.moveToNext());
            }
        }
        AdaptadorCocteles adaptadorCocteles = new AdaptadorCocteles(this,R.layout.list_item_coctel,cocteles);
        listView.setAdapter(adaptadorCocteles);
    }


}