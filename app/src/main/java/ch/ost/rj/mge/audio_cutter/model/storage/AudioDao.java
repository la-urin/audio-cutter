package ch.ost.rj.mge.audio_cutter.model.storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ch.ost.rj.mge.audio_cutter.model.Audio;

@Dao
public interface AudioDao {
    @Query("SELECT * FROM audio")
    List<Audio> getAudios();

    @Insert
    void insert(Audio audio);

    @Query("SELECT count(*) FROM audio")
    int getCount();
}