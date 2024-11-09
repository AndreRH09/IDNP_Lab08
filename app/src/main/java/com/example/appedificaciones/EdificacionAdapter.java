package com.example.appedificaciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class EdificacionAdapter extends RecyclerView.Adapter<EdificacionViewHolder> {

    private List<Edificacion> edificaciones;
    private List<Edificacion> edificacionesFiltradas;
    private OnItemClickListener onItemClickListener;

    public EdificacionAdapter(List<Edificacion> edificaciones) {
        this.edificaciones = edificaciones;
        this.edificacionesFiltradas = new ArrayList<>(edificaciones); // Copia para el filtrado
    }

    // Interface para el click listener
    public interface OnItemClickListener {
        void onItemClick(Edificacion edificacion);
    }
    // Método para asignar el listener desde el fragmento
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public EdificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edificacion, parent, false);
        return new EdificacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EdificacionViewHolder holder, int position) {
        Edificacion edificacion = edificacionesFiltradas.get(position);
        holder.bind(edificacion);

         // Configura el clic en el elemento para llamar al listener
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(edificacion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return edificacionesFiltradas.size();
    }


    public void filtrar(String texto, String categoria) {

        edificacionesFiltradas.clear();
        for (Edificacion edificacion : edificaciones) {
            boolean coincideCategoria = categoria.equals("Todas") || edificacion.getCategoria().equalsIgnoreCase(categoria);
            boolean coincideBusqueda = edificacion.getTitulo().toLowerCase().contains(texto.toLowerCase()) ;

            if (coincideCategoria && coincideBusqueda) {
                edificacionesFiltradas.add(edificacion);
            }
        }
        notifyDataSetChanged();
    }

}
