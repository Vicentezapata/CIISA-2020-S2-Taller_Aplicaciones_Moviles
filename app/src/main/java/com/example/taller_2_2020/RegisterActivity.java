package com.example.taller_2_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout til_date,til_rut,til_nombre,til_apellido,til_edad,til_pass,til_rpass;
    private int mYear, mMonth, mDay;
    private String fechaNac,rut,nombre,apellido,edad,pass,rpass;
    private Button btn_register_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Referencias WIDGETS
        til_date = (TextInputLayout) findViewById(R.id.til_fechaNac);
        til_rut = (TextInputLayout) findViewById(R.id.til_rut);
        til_nombre = (TextInputLayout) findViewById(R.id.til_nombre);
        til_apellido = (TextInputLayout) findViewById(R.id.til_apellido);
        til_edad = (TextInputLayout) findViewById(R.id.til_edad);
        til_pass = (TextInputLayout) findViewById(R.id.til_password);
        til_rpass = (TextInputLayout) findViewById(R.id.til_rpPassword);

        btn_register_layout = (Button) findViewById(R.id.btn_register_layout);

        //EVENTO ONCLICK BOTON
        btn_register_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // POBLAR VALORES
                fechaNac = til_date.getEditText().getText().toString();
                rut = til_rut.getEditText().getText().toString();
                nombre = til_nombre.getEditText().getText().toString();
                apellido = til_apellido.getEditText().getText().toString();
                edad = til_edad.getEditText().getText().toString();
                pass = til_pass.getEditText().getText().toString();
                rpass = til_rpass.getEditText().getText().toString();
                Toast.makeText(RegisterActivity.this, "fechaNac:"+fechaNac+",rut:"+rut+",nombre:"+nombre+",apellido:"+apellido+",edad:"+edad+",pass:"+pass+",rpass:"+rpass, Toast.LENGTH_SHORT).show();
            }
        });
        //EVENTO ONCLICK CALENDARIO
        final Calendar calendar = Calendar.getInstance();
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);

        til_date.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                                if(monthOfYear+1 > 9){
                                    til_date.getEditText().setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                                else{
                                    til_date.getEditText().setText(dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
}