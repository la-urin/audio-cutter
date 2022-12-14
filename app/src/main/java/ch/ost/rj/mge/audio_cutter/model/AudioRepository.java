package ch.ost.rj.mge.audio_cutter.model;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.Observable;

import ch.ost.rj.mge.audio_cutter.model.storage.AudioDatabase;

public class AudioRepository extends Observable {
    private static AudioRepository instance;
    private final AudioDatabase database;

    public static void initialize(Context context) {
        instance = new AudioRepository(context);
    }

    public static AudioRepository getInstance() {
        return instance;
    }

    private AudioRepository(Context context) {
        database = Room.databaseBuilder(context, AudioDatabase.class, "audio-cutter.db").allowMainThreadQueries().build();
    }

    public List<Audio> getAudios() {
        return database.audioDao().getAudios();
    }

    public void addAudio(Audio audio) {
        database.audioDao().insert(audio);
        setChanged();
        notifyObservers(audio);
    }

    public Audio addAudio(String name, String originalFilePath, String snipFilePath) {
        Audio audio = new Audio();
        audio.name = name;
        audio.originalFilePath = originalFilePath;
        audio.snipFilePath = snipFilePath;

        addAudio(audio);

        return audio;
    }

    public boolean isEmpty() {
        return database.audioDao().getCount() == 0;
    }
}