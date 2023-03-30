package com.example.cookingovereasy;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends PreferenceFragment {

    String[] dietList;
    String[] allergyList;
    String[] preferredProteins;

    @Override // hello
    public void onCreate(@Nullable Bundle savedInstanceState){ // hello
        super.onCreate(savedInstanceState); // hello
        addPreferencesFromResource(R.xml.settings); // hello
    }
}