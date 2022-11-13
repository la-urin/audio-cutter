package ch.ost.rj.mge.audio_cutter.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ch.ost.rj.mge.audio_cutter.model.Audio;

public class AudioViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView pathView;

    public AudioViewHolder(View parent, TextView nameView, TextView pathView) {
        super(parent);
        this.nameView = nameView;
        this.pathView = pathView;
    }

    public void bind(Audio audio, AudioAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(v -> listener.onItemClick(audio));
    }
}