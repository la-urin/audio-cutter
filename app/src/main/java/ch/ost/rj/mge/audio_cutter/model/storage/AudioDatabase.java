package ch.ost.rj.mge.audio_cutter.model.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ch.ost.rj.mge.audio_cutter.model.Audio;

@Database(entities = { Audio.class }, version = 1, exportSchema = false)
public abstract class AudioDatabase extends RoomDatabase {
    public abstract AudioDao audioDao();
}