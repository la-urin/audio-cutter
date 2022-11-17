package ch.ost.rj.mge.audio_cutter.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.activities.AudioActivity;
import ch.ost.rj.mge.audio_cutter.adapter.AudioAdapter;
import ch.ost.rj.mge.audio_cutter.model.Audio;
import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class AudioListFragment extends Fragment {

    public static final int WRITE_SETTINGS_REQUEST_CODE = 1;

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

    public void settingAudio(Audio audio) {
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(getContext());
        } else {
            permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            RingtoneManager.setActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_RINGTONE, Uri.parse(audio.originalFilePath));
            Toast.makeText(getContext(), getString(R.string.ringtone_set) + audio.name, Toast.LENGTH_SHORT).show();
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getContext().getPackageName()));
                startActivityForResult(intent, 1);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_SETTINGS}, WRITE_SETTINGS_REQUEST_CODE);
            }
        }
    }

    private void shareAudio(Audio audio) {
        Uri uri = Uri.parse(audio.originalFilePath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("audio/*");
        startActivity(Intent.createChooser(intent, "Share sound"));
    }

    private void playAudio(Audio audio) {
        if (mediaPlayer == null) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this::releaseMediaPlayer);
            mediaPlayer.setAudioAttributes(audioAttributes);
            try {
                mediaPlayer.setDataSource(getContext(), Uri.parse(audio.originalFilePath));
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
                releaseMediaPlayer(mediaPlayer);
                return;
            }

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