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

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.model.Audio;
import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class AudioAdapter extends RecyclerView.Adapter<AudioViewHolder> implements Observer {

    public interface OnItemClickListener {
        void onItemClick(Audio audio);
    }

    private final List<Audio> audios;
    private final OnItemClickListener listener;

    public AudioAdapter(AudioRepository repository, OnItemClickListener listener) {
        this.audios = new ArrayList(repository.getAudios());
        this.listener = listener;
        repository.addObserver(this);
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                R.layout.card_view_template,
                parent,
                false);

        TextView nameView = view.findViewById(R.id.card_view_title);
        TextView pathView = view.findViewById(R.id.card_view_info);
        return new AudioViewHolder(view, nameView, pathView);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        Audio audio = this.audios.get(position);
        holder.nameView.setText(audio.name);
        holder.pathView.setText(audio.path);
        holder.bind(audio, listener);
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