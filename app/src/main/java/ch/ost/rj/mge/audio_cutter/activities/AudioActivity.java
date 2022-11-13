package ch.ost.rj.mge.audio_cutter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ch.ost.rj.mge.audio_cutter.R;

public class AudioActivity extends AppCompatActivity {

    private static final String AUDIO_ID_KEY = "audio_id";

    public static Intent createIntent(Context context, int audioId) {
        Intent intent = new Intent(context, AudioActivity.class);
        intent.putExtra(AUDIO_ID_KEY, audioId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
    }
}