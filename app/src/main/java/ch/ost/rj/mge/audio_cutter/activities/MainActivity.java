package ch.ost.rj.mge.audio_cutter.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.File;

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.adapter.AudioAdapter;
import ch.ost.rj.mge.audio_cutter.model.Audio;
import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> selectAudioResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        AudioAdapter adapter = new AudioAdapter(AudioRepository.getInstance());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        RecyclerView recyclerView = findViewById(R.id.audio_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ExtendedFloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            addNewAudio();
        });

        selectAudioResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) onAudioSelected(data);
                    }
                });
    }

    private void addNewAudio() {
        Intent selectAudioIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        if (selectAudioIntent.resolveActivity(getPackageManager()) != null) {
            selectAudioResultLauncher.launch(selectAudioIntent);
        }
    }

    private void onAudioSelected(Intent data) {
        File file = new File(data.getDataString());
        Audio audio = AudioRepository.getInstance().addAudio(file.getName(), file.getAbsolutePath());
        Intent intent = AudioActivity.createIntent(this, audio.id);
        startActivity(intent);
    }
}