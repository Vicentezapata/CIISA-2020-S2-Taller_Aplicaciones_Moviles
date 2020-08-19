package com.example.taller_2_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class RegistroElement extends AppCompatActivity {

    private Spinner spBase1,spBase2,spJugo1,spJugo2;
    private Button btnUpdate,btnDelete;
    private TextInputLayout tilNomCoct;
    private List<String> baseCoctel,basesJugo;
    private TextView tvRegistro;
    String[] intentRegistro;
    String idElemn;
    String base1,base2,jugo1,jugo2,nomCoct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_element);
        //REFERENCIAS
        tvRegistro = (TextView) findViewById(R.id.tvRegistro);
        spBase1 = findViewById(R.id.spBasesCoct);
        spBase2 = findViewById(R.id.spiBasesCoct2);
        spJugo1 = findViewById(R.id.spJugo1);
        spJugo2 = findViewById(R.id.spJugo2);
        tilNomCoct = findViewById(R.id.til_nom_coct);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        //OBTENEMOS VARIABLES DE INTENT
        intentRegistro = getIntent().getStringArrayExtra("elemRegistro");

        //SETEAR EL VALOR EN EL TEXTVIEW
        //tvRegistro.setText(intentRegistro);

        //LISTA de DATOS
        baseCoctel = new ArrayList<String>();
        baseCoctel.add("Seleccione la opción");
        baseCoctel.add("Ron");
        baseCoctel.add("Tequila");
        baseCoctel.add("Pisco");
        baseCoctel.add("Gin");

        basesJugo = new ArrayList<String>();
        basesJugo.add("Seleccione la opción");
        basesJugo.add("Piña");
        basesJugo.add("Limon");
        basesJugo.add("Frutilla");
        basesJugo.add("Frambuesa");

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

        //RELLENAMOS LA INTERFAZ
        tilNomCoct.getEditText().setText(intentRegistro[1]);
        spBase1.setSelection(baseCoctel.indexOf(intentRegistro[2]));
        spBase2.setSelection(baseCoctel.indexOf(intentRegistro[3]));
        spJugo1.setSelection(basesJugo.indexOf(intentRegistro[4]));
        spJugo2.setSelection(basesJugo.indexOf(intentRegistro[5]));

        idElemn = intentRegistro[0];

        //EVENTO ONCLIKC BOTON DELETE
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //METODO DELETE
                deleteRegistro(idElemn);
            }
        });
        //EVENTO ONCLIKC BOTON UPDATE
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //METODO ACTUALIZAR
                base1 = spBase1.getSelectedItem().toString();
                base2 = spBase2.getSelectedItem().toString();
                jugo1 = spJugo1.getSelectedItem().toString();
                jugo2 = spJugo2.getSelectedItem().toString();
                nomCoct = tilNomCoct.getEditText().getText().toString();
                updateRegistro(base1,base2,jugo1,jugo2,nomCoct,idElemn);

            }
        });


    }
    private void updateRegistro(String base1,String base2,String jugo1,String jugo2,String nomCoct,String id){
        DBhelper dBhelper = new DBhelper(this,"DataBase_CIISA",null,1);
        //ORM
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        if(db!=null){
            //CREAMOS UN OBJETO TEMPORAL PARA INSERTAR DATOS
            ContentValues cv = new ContentValues();
            cv.put("nombre_coctel",nomCoct);
            cv.put("base1",base1);
            cv.put("base2",base2);
            cv.put("jugo1",jugo1);
            cv.put("jugo2",jugo2);
            long nFilas = db.update("tbl_datos",cv,"id_dato = "+ id,null);
            if(nFilas > 0){
                Intent intent = new Intent(this,UserDashBoard.class);
                Toast.makeText(this, "Coctel actualizado exitosamente", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void deleteRegistro(String id){
        DBhelper dBhelper = new DBhelper(this,"DataBase_CIISA",null,1);
        //ORM
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        if(db!=null){
            long nFilas = db.delete("tbl_datos","id_dato = "+ id,null);
            if(nFilas > 0){
                Intent intent = new Intent(this,UserDashBoard.class);
                Toast.makeText(this, "Coctel eliminado exitosamente", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        }

    }
}