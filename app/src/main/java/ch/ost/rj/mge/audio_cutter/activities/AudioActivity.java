package ch.ost.rj.mge.audio_cutter.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.model.Audio;

public class AudioActivity extends AppCompatActivity {

    private static final String AUDIO_KEY = "audio";

    private Audio audio;

    public static Intent createIntent(Context context, Audio audio) {
        Intent intent = new Intent(context, AudioActivity.class);
        intent.putExtra(AUDIO_KEY, audio);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        audio = (Audio) getIntent().getSerializableExtra(AUDIO_KEY);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(audio.name);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                return true;

            case R.id.action_edit:
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}