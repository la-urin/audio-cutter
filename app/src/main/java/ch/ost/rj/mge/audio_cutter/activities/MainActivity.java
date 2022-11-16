package ch.ost.rj.mge.audio_cutter.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import ch.ost.rj.mge.audio_cutter.R;
import ch.ost.rj.mge.audio_cutter.fragments.AudioListFragment;
import ch.ost.rj.mge.audio_cutter.fragments.EmptyListFragment;
import ch.ost.rj.mge.audio_cutter.model.Audio;
import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> selectAudioResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = AudioRepository.getInstance().isEmpty() ? EmptyListFragment.create() : AudioListFragment.create();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.audio_list_container, fragment)
                .commit();

        ExtendedFloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> addNewAudio());

        // callback for file selector
        selectAudioResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri uri = result.getData().getData();
                        onAudioSelected(uri);
                    }
                });
    }

    private void startAudioActivity(Audio audio) {
        Intent intent = AudioActivity.createIntent(this, audio);
        startActivity(intent);
    }

    private void addNewAudio() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*");

        if (intent.resolveActivity(getPackageManager()) != null) {
            selectAudioResultLauncher.launch(intent);
        }
    }

    private void onAudioSelected(Uri uri) {
        AudioRepository repository = AudioRepository.getInstance();
        System.out.println("uri: " + uri.toString());
        File originalFile = new File(uri.getPath());
        File localCopy = copyFileToLocalStorage(originalFile);
        Audio audio = repository.addAudio(originalFile.getName(), localCopy.getAbsolutePath());
        startAudioActivity(audio);
    }

    private File copyFileToLocalStorage(File original) {
        // todo: remove .mp3 ending
        String path = String.format(Locale.GERMAN, "%d/%s.mp3", System.currentTimeMillis(), original.getName());

        File destFile = new File(getApplicationContext().getFilesDir(), path);

        System.out.println("dest: " + destFile);
        System.out.println("parent: " + destFile.getParentFile());
        if (!destFile.exists()) {
            try {
                if (!destFile.getParentFile().exists()){
                    destFile.getParentFile().mkdirs();
                }

                // todo: replace with actual audio source file
                InputStream inputStream = getResources().openRawResource(R.raw.example);
                FileOutputStream fileOutputStream = new FileOutputStream(destFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }

                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("fail: " + e.getMessage());
            }
        }

        return destFile;
    }
}