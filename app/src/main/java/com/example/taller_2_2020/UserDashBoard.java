package com.example.taller_2_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserDashBoard extends AppCompatActivity {

    private ListView listView;
    private List <String> registros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        // Referencias WIDGETS es lo mismo que el getElementById de JS para HTML
        listView = (ListView) findViewById(R.id.lvRegistros);

        //LISTA de DATOS
        registros = new ArrayList<String>();
        //Se pobla con datos Samples
        registros.add("27-07-2020 3 Cocteles");
        registros.add("28-07-2020 1 Cocteles");
        registros.add("29-07-2020 5 Cocteles");
        registros.add("30-07-2020 3 Cocteles");
        registros.add("31-07-2020 7 Cocteles");
        registros.add("01-08-2020 9 Cocteles");
        registros.add("02-08-2020 12 Cocteles");
        registros.add("03-08-2020 22 Cocteles");
        registros.add("04-08-2020 3 Cocteles");
        registros.add("05-08-2020 5 Cocteles");
        registros.add("06-08-2020 12 Cocteles");

        //ADAPTADOR es la forma visual de renderizar los datos
        //se crea un adapter, un adaptador es la forma de mostrar los datos, como se renderizara (un template de render)
        //__________________________________________INICIO LISTVIEW SIMPLE ADAPTER____________________________________________
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,registros);
        listView.setAdapter(adapter);
        //EVENTO CLICK
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) {
                //Toast.makeText(UserDashBoard.this,"Clicked"+registros.get(posicion),Toast.LENGTH_SHORT).show();
                //EJECUCION PARA CAMBIAR DE LAYOUT
                Intent intent = new Intent(view.getContext(),RegistroElement.class);
                intent.putExtra("elemRegistro",registros.get(posicion));
                startActivity(intent);



            }
        });


        //__________________________________________FIN LISTVIEW SIMPLE ADAPTER____________________________________________


    }
}