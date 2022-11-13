package ch.ost.rj.mge.audio_cutter.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "audio")
public final class Audio {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
}