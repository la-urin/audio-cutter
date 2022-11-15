package ch.ost.rj.mge.audio_cutter.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AudioViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView pathView;
    public ImageButton playButton;

    public AudioViewHolder(View parent, TextView nameView, TextView pathView, ImageButton playButton) {
        super(parent);
        this.nameView = nameView;
        this.pathView = pathView;
        this.playButton = playButton;
    }
}
