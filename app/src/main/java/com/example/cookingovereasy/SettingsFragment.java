package com.example.cookingovereasy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends PreferenceFragment {

    String[] dietList;
    String[] allergyList;
    String[] preferredProteins;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        }
        else {
            addPreferencesFromResource(R.xml.settings);
        }

        Preference button = findPreference(getString(R.string.logOutButton));
        button.setOnPreferenceClickListener(preference -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Successfully logged out.",
                    Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}