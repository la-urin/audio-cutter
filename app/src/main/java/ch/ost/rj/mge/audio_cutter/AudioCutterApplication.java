package ch.ost.rj.mge.audio_cutter;

import android.app.Application;
import android.content.Context;

import ch.ost.rj.mge.audio_cutter.model.AudioRepository;

public class AudioCutterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        AudioRepository.initialize(context);
    }
}