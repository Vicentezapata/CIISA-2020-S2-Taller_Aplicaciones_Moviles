package com.example.taller_2_2020;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validate {
    public boolean validarNombre(String nombre){
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(nombre).matches() || nombre.length() > 30;
    }
    public boolean validarNulo(String variable){
        if(variable.equals("")){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean validadTelefono(String telefono){
        return Patterns.PHONE.matcher(telefono).matches();
    }


}
