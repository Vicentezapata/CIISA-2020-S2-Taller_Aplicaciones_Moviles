package com.example.taller_2_2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class AdaptadorCocteles extends BaseAdapter {
    //CREAMOS ATRIBUTOS
    private Context context;
    private int layout;
    private List<String[]> cocteles;

    public AdaptadorCocteles(Context context, int layout, List<String[]> cocteles) {
        this.context = context;
        this.layout = layout;
        this.cocteles = cocteles;
    }

    @Override
    public int getCount() {
        return this.cocteles.size();
    }

    @Override
    public Object getItem(int position) {
        return this.cocteles.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //CREAMOS UNA VISTA A INFLAR
        //View Holder Pattern
        ViewHolder holder;
        if(convertView == null) {
            //Inflamos la vista que nos ha llegado con nuestro list_item_coctel personalizado
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            //SE TOMA EL VIEW SE INFLA Y SE DEVUELVE
            convertView = layoutInflater.inflate(R.layout.list_item_coctel, null);//DEVUELVE UN VIEW, USA UN RESOURCE, UNA REFERENCIA
            holder = new ViewHolder();
            //REFERENCIAS
            holder.tvCoctel = (TextView) convertView.findViewById(R.id.tvNameCoct);
            holder.tvBase = (TextView) convertView.findViewById(R.id.tvBase);
            holder.tvBase2 = (TextView) convertView.findViewById(R.id.tvBase2);
            holder.tvJugo1 = (TextView) convertView.findViewById(R.id.tvJugo1);
            holder.tvJugo2 = (TextView) convertView.findViewById(R.id.tvJugo2);
            holder.imgCoct = (ImageView) convertView.findViewById(R.id.imgCoct);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //Copiamos la vista
        View v = convertView;
        String[] currentCoctel = cocteles.get(position);
        holder.tvCoctel.setText(currentCoctel[1]);
        holder.tvBase.setText(currentCoctel[2]);
        holder.tvBase2.setText(currentCoctel[3]);
        holder.tvJugo1.setText(currentCoctel[4]);
        holder.tvJugo2.setText(currentCoctel[5]);
        //Devolvemos la vista inflada y modificada con nuestros datos
        return convertView;
    }

    static class ViewHolder{
        private TextView tvCoctel;
        private TextView tvBase;
        private TextView tvBase2;
        private TextView tvJugo1;
        private TextView tvJugo2;
        private ImageView imgCoct;

    }



}
