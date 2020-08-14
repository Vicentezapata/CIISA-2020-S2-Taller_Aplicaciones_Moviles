package com.example.taller_2_2020;

import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Validate {
    //VALIDA SI ES CORRECTO EL FORMATO DEL NOMBRE, EN CASO DE SERLO RETORNARA TRUE SINO MATCHEA CON EL PATRON RETORNA FALSE
    public boolean validarNombre(String nombre){
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(nombre).matches() || nombre.length() > 30;
    }
    public boolean validarFormatoRut(String rut){
        Pattern patron = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
        return patron.matcher(rut).matches() || rut.length() > 30;
    }
    public boolean validarNulo(String variable){
        if(variable.equals("")){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean validarTelefono(String telefono){
        return Patterns.PHONE.matcher(telefono).matches();
    }

    //VALIDAMOS QUE LA FECHA INGRESADA NO SUPERE LA FECHA ACTUAL
    //dateSystem < dateValidar, devuelve un valor menor que 0
    //dateSystem > dateValidar, devuelve un valor mayor que 0
    //dateSystem = dateValidar, se mostrará un 0 en la consola
    public boolean validarFechaAlDia(String fecha)  {
        int mYear, mMonth, mDay;

        //OBTENEMOS LA FECHA AL DIA
        final Calendar calendar = Calendar.getInstance();
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH)+1;
        mYear = calendar.get(Calendar.YEAR);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dateSystem = null;
        try {
            dateSystem = simpleDateFormat.parse(mDay+"-"+mMonth+"-"+mYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateValidar = null;
        try {
            dateValidar = simpleDateFormat.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int result = dateSystem.compareTo(dateValidar);

        if (result<0){
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }
    //METODOS PROPIOS

}
