package ch.ost.rj.mge.audio_cutter.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.activities.AudioActivity;
import ch.ost.rj.mge.audio_cutter.adapter.AudioAdapter;
import ch.ost.rj.mge.audio_cutter.model.Audio;
import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class AudioListFragment extends Fragment {

    public static AudioListFragment create() {
        return new AudioListFragment();
    }

    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_list, container, false);

        if (view instanceof RecyclerView) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            AudioAdapter adapter = new AudioAdapter(AudioRepository.getInstance(),
                    this::startAudioActivity,
                    this::playAudio,
                    this::settingAudio,
                    this::shareAudio);

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

    private void settingAudio(Audio audio) {

    }

    private void shareAudio(Audio audio) {
        // use example file in raw folder. delete this shit
        Uri uri = getUriFromRawExampleAudioFileBecauseNothingIsWorking();
//        Uri uri = Uri.parse(audio.path);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("audio/*");
        startActivity(Intent.createChooser(intent, "Share sound"));
    }

    private Uri getUriFromRawExampleAudioFileBecauseNothingIsWorking() {
        InputStream inputStream;
        FileOutputStream fileOutputStream;

        String filename = "example.mp3";
        File destFile = new File(getContext().getExternalFilesDir("Audio"), filename);

        if (!destFile.exists()) {
            try {
                inputStream = getResources().openRawResource(R.raw.example);
                fileOutputStream = new FileOutputStream(destFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }

                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Uri.parse(destFile.getAbsolutePath());
    }

    private void playAudio(Audio audio) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.example);
            mediaPlayer.setOnCompletionListener(this::releaseMediaPlayer);
            mediaPlayer.start();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            releaseMediaPlayer(mediaPlayer);
        }
    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        mediaPlayer.reset();
        mediaPlayer.release();
        this.mediaPlayer = null;
    }
}