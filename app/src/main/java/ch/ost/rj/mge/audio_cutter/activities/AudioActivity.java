package ch.ost.rj.mge.audio_cutter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Objects;

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.model.Audio;

public class AudioActivity extends AppCompatActivity {

    private static final String AUDIO_KEY = "audio";

    public static Intent createIntent(Context context, Audio audio) {
        Intent intent = new Intent(context, AudioActivity.class);
        intent.putExtra(AUDIO_KEY, audio);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Audio audio = (Audio) getIntent().getSerializableExtra(AUDIO_KEY);
        Objects.requireNonNull(getSupportActionBar()).setTitle(audio.name);
    }
}