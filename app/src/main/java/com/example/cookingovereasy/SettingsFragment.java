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

//    @Override
//    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//        dietList = getResources().getStringArray(R.array.dietList);
//        allergyList = getResources().getStringArray(R.array.allergyList);
//        preferredProteins = getResources().getStringArray(R.array.preferredProteins);
//        setPreferencesFromResource(R.xml.settings, rootKey);
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_settings, container, false);
//    }
}