package com.example.taller_2_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RegistroElement extends AppCompatActivity {

    private TextView tvRegistro;
    String intentRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_element);
        //REFERENCIAS
        tvRegistro = (TextView) findViewById(R.id.tvRegistro);

        //OBTENEMOS VARIABLES DE INTENT
        intentRegistro = getIntent().getStringExtra("elemRegistro");

        //SETEAR EL VALOR EN EL TEXTVIEW
        tvRegistro.setText(intentRegistro);


    }
}