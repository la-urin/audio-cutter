package ch.ost.rj.mge.audio_cutter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ch.ost.rj.mge.audio_cutter.model.Audio;
import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class AudioAdapter extends RecyclerView.Adapter<AudioViewHolder> implements Observer {
    private final List<Audio> audios;

    public AudioAdapter(AudioRepository repository) {
        this.audios = new ArrayList(repository.getAudios());
        repository.addObserver(this);
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false);

        TextView nameView = view.findViewById(android.R.id.text1);
        return new AudioViewHolder(view, nameView);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        Audio audio = this.audios.get(position);
        holder.nameView.setText(audio.name);
    }

    @Override
    public int getItemCount() {
        return this.audios.size();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.audios.add((Audio)arg);
        notifyItemInserted(this.audios.size());
    }
}