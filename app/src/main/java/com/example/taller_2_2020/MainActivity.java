package com.example.taller_2_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout til_rut,til_pass;
    Button btn_register,btn_login;
    private String rut,pass;

    //SHARED PREFERENCES
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;
    private Switch swRememberUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //REFERENCIAMOS WIDGET
        // Referencias WIDGETS es lo mismo que el getElementById de JS para HTML
        til_pass = (TextInputLayout) findViewById(R.id.til_password);
        til_rut = (TextInputLayout) findViewById(R.id.til_rut);
        btn_register = (Button) findViewById(R.id.btn_register_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        swRememberUser = (Switch) findViewById(R.id.swRememberUser);

        //INICIALIZAMOS LAS VARIABLES DE SHARED PREFERENCES
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //SI QUEREMOS GENERAR PREFERENCES ENTRE APLICACIONES
        //sharedPreferences = getSharedPreferences("Taller2020", Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();


        //VERIFICAMOS SI EXISTE UN VALOR ALMACENADO EN LAS SHARED PREFERENCES
        String existRutUser = sharedPreferences.getString("rutUser","");
        if(!existRutUser.equals("")){
            til_rut.getEditText().setText(existRutUser);
        }


        //METODO ONCLICK PARA CAMBIAR DE PANTALLAS
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        //METODO ONCLICK PARA LOGIN
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // POBLAR VALORES
                pass = til_pass.getEditText().getText().toString();
                rut = til_rut.getEditText().getText().toString();

                //MOSTRAR DATOS EN PANTALLA
                Toast.makeText(MainActivity.this, "rut:"+rut+",pass:"+pass, Toast.LENGTH_SHORT).show();


                //VALIDACION CON CLASE
                if(validarDatos() == 0){
                    //ACA COLOCAREMOS CODIGO PARA LEER DATO DE SQLITE
                    //VALIDAMOS EL ESTADO DEL SWITCH DE LA INTERFAZ
                    if(swRememberUser.isChecked()){
                        //ALMACENAR VARIABLES EN SAHRED PREFERENCES
                        sharedEditor.putString("rutUser",rut);
                        //ESTE METODO ESPERA A QUE GUARDE LA LLAVE Y FLUJO SIGUE
                        sharedEditor.commit();
                        //ESTE METODONO ESPERA QUE SE ALLMACENE LA VARIABLE ES ASINCRONO
                        sharedEditor.apply();

                    }
                    else {
                        //LIMPIAR VARIABLES
                        sharedEditor.clear().commit();
                    }

                    if(validarCredenciales(rut,pass)){
                        //EJECUCION PARA CAMBIAR DE LAYOUT
                        Intent intent = new Intent(view.getContext(),UserDashBoard.class);
                        startActivity(intent);
                        //SE GENERA FLAG PARA IMPEDIR QUE EL USUARIO PUEDA RETROCEDER
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Rut o Contraña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    //Toast.makeText(MainActivity.this, "Estimado uno o mas de los campos es invalido favor revisar", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    //LOGIN SQLITE
    private boolean validarCredenciales(String rut,String pass){
        boolean flag = false;
        DBhelper dBhelper = new DBhelper(this,"DataBase_CIISA",null,1);
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        if(db != null){
            Cursor cr = db.rawQuery("SELECT * FROM tbl_user WHERE rut='"+rut+"' AND contrasena='"+pass+"'",null);
            if(cr.moveToFirst()){
                do{
                    //ALMACENAR VARIABLES EN SAHRED PREFERENCES
                    sharedEditor.putString("idUser",cr.getString(0));//ID USER
                    sharedEditor.putString("rutUser",cr.getString(1));//RUT USER
                    sharedEditor.putString("nombreUser",cr.getString(2));//NOMBRE USER
                    sharedEditor.putString("apellidoUser",cr.getString(3));//APELLIDO USER
                    //ESTE METODO ESPERA A QUE GUARDE LA LLAVE Y FLUJO SIGUE
                    sharedEditor.commit();
                    //ESTE METODONO ESPERA QUE SE ALLMACENE LA VARIABLE ES ASINCRONO
                    sharedEditor.apply();
                }while(cr.moveToNext());
            }
            int filas = cr.getCount();
            flag = filas > 0;
        }
        return flag;
    }



    //VALIDACION DE DATOS
    private int validarDatos()  {
        Validate validate = new Validate();
        String rut = til_rut.getEditText().getText().toString();
        String pass = til_pass.getEditText().getText().toString();
        int contador = 0;
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
        //VALIDAMOS EL pass
        if (validate.validarNulo(pass)) {
            til_pass.setError("Campo Pass Requerido");
            contador++;
        }
        else{
            til_pass.setError(null);
        }
        return contador;

    }

    //LIFECYCLE ACTIVITIES
    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(MainActivity.this, "onStart()", Toast.LENGTH_SHORT).show();
        Log.i("MainActivity","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(MainActivity.this, "onResume()", Toast.LENGTH_SHORT).show();
        Log.i("MainActivity","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity","onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity","onRestart()");
    }
}