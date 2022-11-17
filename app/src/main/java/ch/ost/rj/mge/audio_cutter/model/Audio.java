package ch.ost.rj.mge.audio_cutter.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "audio")
public final class Audio implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String originalFilePath;

    @ColumnInfo
    public String snipFilePath;
}