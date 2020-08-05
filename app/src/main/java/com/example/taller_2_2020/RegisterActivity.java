package com.example.taller_2_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout til_date,til_rut,til_nombre,til_apellido,til_edad,til_pass,til_rpass;
    private RadioGroup rdgGender;
    private RadioButton rdbGender;
    private int mYear, mMonth, mDay;
    private String fechaNac,rut,nombre,apellido,edad,pass,rpass,gender;
    private Button btn_register_layout;

    @Override
    //ESTE METODO SE EJECUTA AL CREAR LA INTERFAZ
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Referencias WIDGETS es lo mismo que el getElementById de JS para HTML
        til_date = (TextInputLayout) findViewById(R.id.til_fechaNac);
        til_rut = (TextInputLayout) findViewById(R.id.til_rut);
        til_nombre = (TextInputLayout) findViewById(R.id.til_nombre);
        til_apellido = (TextInputLayout) findViewById(R.id.til_apellido);
        til_edad = (TextInputLayout) findViewById(R.id.til_edad);
        til_pass = (TextInputLayout) findViewById(R.id.til_password);
        til_rpass = (TextInputLayout) findViewById(R.id.til_rpPassword);

        btn_register_layout = (Button) findViewById(R.id.btn_register_layout);
        rdgGender = (RadioGroup) findViewById(R.id.rdgGender);

        //EVENTO ONCLICK BOTON
        btn_register_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Radio Button
                int selectedIdGender = rdgGender.getCheckedRadioButtonId();
                rdbGender = (RadioButton) findViewById(selectedIdGender);

                // POBLAR VALORES
                fechaNac = til_date.getEditText().getText().toString();
                rut = til_rut.getEditText().getText().toString();
                nombre = til_nombre.getEditText().getText().toString();
                apellido = til_apellido.getEditText().getText().toString();
                edad = til_edad.getEditText().getText().toString();
                pass = til_pass.getEditText().getText().toString();
                rpass = til_rpass.getEditText().getText().toString();
                gender = rdbGender.getText().toString();


                //MOSTRAR DATOS EN PANTALLA
                //Toast.makeText(RegisterActivity.this, "fechaNac:"+fechaNac+",rut:"+rut+",nombre:"+nombre+",apellido:"+apellido+",edad:"+edad+",pass:"+pass+",rpass:"+rpass+",gender:"+gender, Toast.LENGTH_SHORT).show();


                //PROCEDEMOS A LA VALIDACION DE CAMPOS
                //VALIDACION TRADICIONAL
                if(nombre.equals("")){
                    til_nombre.setError("Nombre invalido");
                }
                else{
                    til_nombre.setError(null);
                }

                //VALIDACION CON CLASE
                if(validarDatos() == 0){
                    //ACA COLOCAREMOS CODIGO PARA INSERTAR EN SQLITE

                    //EJECUCION PARA CAMBIAR DE LAYOUT
                    Intent intent = new Intent(view.getContext(),MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Estimado uno o mas de los campos es invalido favor revisar", Toast.LENGTH_SHORT).show();
                }

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
    //VALIDACION DE DATOS
    private int validarDatos()  {
        Validate validate = new Validate();
        String fechaNac = til_date.getEditText().getText().toString();
        String rut = til_rut.getEditText().getText().toString();
        String nombre = til_nombre.getEditText().getText().toString();
        String apellido = til_apellido.getEditText().getText().toString();
        String edad = til_edad.getEditText().getText().toString();
        String pass = til_pass.getEditText().getText().toString();
        String rpass = til_rpass.getEditText().getText().toString();
        String gender = rdbGender.getText().toString();
        int contador = 0;
        //VALIDAMOS LA FECHA
        if (validate.validarNulo(fechaNac)) {
            til_date.setError("Campo Fecha Requerido");
            contador++;
        }
        else{

            //Toast.makeText(RegisterActivity.this, "date:"+dateFormat, Toast.LENGTH_SHORT).show();
            til_date.setError(null);
            //VALIDAMOS QUE LA FECHA NO SUPERE A LA ACTUAL
            if (validate.validarFechaAlDia(fechaNac)) {
                til_date.setError(null);
            }
            else{
                til_date.setError("La fecha ingresada no debe superar la actual");
                contador++;
            }
        }
        //VALIDAMOS EL RUT
        if (validate.validarNulo(rut)) {
            til_rut.setError("Campo Rut Requerido");
            contador++;
        }
        else{
            til_rut.setError(null);
            //VALIDAMOS QUE EL RUT SEA VALIDO
            if (validate.validarFormatoRut(rut)) {
                til_rut.setError(null);
                if(validate.validarRut(rut)){
                    til_rut.setError(null);
                }
                else{
                    til_rut.setError("Rut invalido");
                    contador++;
                }

            }
            else{
                til_rut.setError("El el rut debe tener el formato 123456789-0");
                contador++;
            }
        }
        //VALIDAMOS EL NOMBRE
        if (validate.validarNulo(nombre)) {
            til_nombre.setError("Campo Nombre Requerido");
            contador++;
        }
        else{
            til_nombre.setError(null);
            //VALIDAMOS QUE EL NOMBRE SEA VALIDO
            if (validate.validarNombre(nombre)) {
                til_nombre.setError(null);
            }
            else{
                til_nombre.setError("El el nombre no debe contener numeros o caracteres especiales");
                contador++;
            }
        }
        //VALIDAMOS EL apellido
        if (validate.validarNulo(apellido)) {
            til_apellido.setError("Campo Apellido Requerido");
            contador++;
        }
        else{
            til_apellido.setError(null);
            //VALIDAMOS QUE EL APELLIDO SEA VALIDO
            if (validate.validarNombre(apellido)) {
                til_apellido.setError(null);
            }
            else{
                til_apellido.setError("El el apellido no debe contener numeros o caracteres especiales");
                contador++;
            }
        }
        //VALIDAMOS EL edad
        if (validate.validarNulo(edad)) {
            til_edad.setError("Campo Edad Requerido");
            contador++;
        }
        else{
            til_edad.setError(null);
        }
        //VALIDAMOS EL pass
        if (validate.validarNulo(pass)) {
            til_pass.setError("Campo Pass Requerido");
            contador++;
        }
        else{
            til_pass.setError(null);
            //VALIDAMOS claves iguales
            if (pass.equals(rpass)) {
                til_pass.setError(null);
                til_rpass.setError(null);
            }
            else{
                til_pass.setError("Las claves deben ser iguales");
                til_rpass.setError("Las claves deben ser iguales");
                contador++;
            }
        }
        //VALIDAMOS EL rpass
        if (validate.validarNulo(rpass)) {
            til_rpass.setError("Campo rpass Requerido");
            contador++;
        }
        else{
            til_rpass.setError(null);
            //VALIDAMOS claves iguales
            if (pass.equals(rpass)) {
                til_pass.setError(null);
                til_rpass.setError(null);
            }
            else{
                til_pass.setError("Las claves deben ser iguales");
                til_rpass.setError("Las claves deben ser iguales");
                contador++;
            }
        }
        return contador;

    }
}