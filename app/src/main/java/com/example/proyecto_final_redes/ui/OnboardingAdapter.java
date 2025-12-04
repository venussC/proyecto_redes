package com.example.proyecto_final_redes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_redes.R;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private int[] images;
    private String[] titulos;
    private String[] descripciones;

    public OnboardingAdapter(int[] images, String[] titulos, String[] descripciones) {
        this.images = images;
        this.titulos = titulos;
        this.descripciones = descripciones;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboarding, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.imgOnboarding.setImageResource(images[position]);
        holder.txtTitle.setText(titulos[position]);
        holder.txtDescription.setText(descripciones[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOnboarding;
        TextView txtTitle, txtDescription;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOnboarding = itemView.findViewById(R.id.imgOnboarding);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}