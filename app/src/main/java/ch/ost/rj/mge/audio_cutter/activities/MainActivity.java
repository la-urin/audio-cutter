package ch.ost.rj.mge.audio_cutter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.adapter.AudioAdapter;
import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class MainActivity extends AppCompatActivity {

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
    }

    private void addNewAudio() {
        Intent intent = AudioActivity.createIntent(this, "");
        startActivity(intent);
    }
}