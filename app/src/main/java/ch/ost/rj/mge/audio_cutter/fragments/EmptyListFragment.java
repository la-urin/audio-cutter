package ch.ost.rj.mge.audio_cutter.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.ost.rj.mge.audio_cutter.R;

public class EmptyListFragment extends Fragment {

    public static EmptyListFragment create() {
        return new EmptyListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty_list, container, false);
    }
}