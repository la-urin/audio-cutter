package ch.ost.rj.mge.audio_cutter.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AudioViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;

    public AudioViewHolder(View parent, TextView nameView) {
        super(parent);
        this.nameView = nameView;
    }
}