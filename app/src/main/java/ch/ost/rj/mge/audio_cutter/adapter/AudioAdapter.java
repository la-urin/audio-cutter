package ch.ost.rj.mge.audio_cutter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    public interface OnAudioClickListener {
        void onClick(Audio audio);
    }

    private final List<Audio> audios;
    private final OnAudioClickListener onClickListener;
    private final OnAudioClickListener onPlayListener;
    private final OnAudioClickListener onSettingListener;
    private final OnAudioClickListener onShareListener;

    public AudioAdapter(AudioRepository repository,
                        OnAudioClickListener onClickListener,
                        OnAudioClickListener onPlayListener,
                        OnAudioClickListener onSettingListener,
                        OnAudioClickListener onShareListener) {
        this.audios = new ArrayList<>(repository.getAudios());
        this.onClickListener = onClickListener;
        this.onPlayListener = onPlayListener;
        this.onSettingListener = onSettingListener;
        this.onShareListener = onShareListener;

        repository.addObserver(this);
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                R.layout.fragment_audio_card,
                parent,
                false);

        TextView nameView = view.findViewById(R.id.card_view_title);
        TextView pathView = view.findViewById(R.id.card_view_info);
        ImageButton playButton = view.findViewById(R.id.card_view_play);
        ImageButton settingButton = view.findViewById(R.id.card_view_setting);
        ImageButton shareButton = view.findViewById(R.id.card_view_share);
        return new AudioViewHolder(view, nameView, pathView, playButton, settingButton, shareButton);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        Audio audio = this.audios.get(position);
        holder.nameView.setText(audio.name);
        holder.pathView.setText(audio.originalFilePath);

        holder.itemView.setOnClickListener(v -> this.onClickListener.onClick(audio));
        holder.playButton.setOnClickListener(v -> this.onPlayListener.onClick(audio));
        holder.settingButton.setOnClickListener(v -> this.onSettingListener.onClick(audio));
        holder.shareButton.setOnClickListener(v -> this.onShareListener.onClick(audio));
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