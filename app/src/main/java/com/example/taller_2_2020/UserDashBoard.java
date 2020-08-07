package com.example.taller_2_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserDashBoard extends AppCompatActivity {

    private ListView listView;
    private Spinner spinner;

    private List <String> cocteles;
    private List <String> baseCoctel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        // Referencias WIDGETS es lo mismo que el getElementById de JS para HTML
        listView = (ListView) findViewById(R.id.lvRegistros);
        spinner = (Spinner)  findViewById(R.id.spBasesCoct);

        //LISTA de DATOS
        cocteles = new ArrayList<String>();
        //Se pobla con datos Samples
        cocteles.add("Mojito");
        cocteles.add("Pisco Sour");
        cocteles.add("Tequila Sour");
        cocteles.add("Cuba Libre");
        cocteles.add("Tegroni");
        cocteles.add("Negroni");
        cocteles.add("Amaretto Sour");
        cocteles.add("Gin Fizz");
        cocteles.add("Tequila Sonrise");

        baseCoctel = new ArrayList<String>();
        baseCoctel.add("Ron");
        baseCoctel.add("Tequila");
        baseCoctel.add("Pisco");
        baseCoctel.add("Gin");



        //___________________________________________SPINNER_____________________________________
        ArrayAdapter<String> adapterBaseCoct = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,baseCoctel);
        spinner.setAdapter(adapterBaseCoct);
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

    }
}