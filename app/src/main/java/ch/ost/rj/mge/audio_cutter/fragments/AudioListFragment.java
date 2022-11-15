package ch.ost.rj.mge.audio_cutter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.activities.AudioActivity;
import ch.ost.rj.mge.audio_cutter.adapter.AudioAdapter;
import ch.ost.rj.mge.audio_cutter.model.Audio;
import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class AudioListFragment extends Fragment {

    public static AudioListFragment create() {
        return new AudioListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_list, container, false);

        if (view instanceof RecyclerView) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            AudioAdapter adapter = new AudioAdapter(AudioRepository.getInstance(),
                    this::startAudioActivity,
                    this::playAudio);

            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    private void startAudioActivity(Audio audio) {
        Intent intent = AudioActivity.createIntent(getActivity(), audio);
        startActivity(intent);
    }

    private void playAudio(Audio audio) {
        System.out.println("play audio: " + audio.path);
    }
}