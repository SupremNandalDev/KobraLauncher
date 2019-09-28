package com.kobra.launcher.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.kobra.launcher.R;
import com.kobra.launcher.database.AppInfoDB;
import com.kobra.launcher.preferences.UserPreferences;

import java.io.File;

public class SettingFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private UserPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.settingSetDefaultLauncher).setOnClickListener(this);
        view.findViewById(R.id.settingSystemSettings).setOnClickListener(this);
        view.findViewById(R.id.settingCustomiseLayout).setOnClickListener(this);
        view.findViewById(R.id.settingResetLauncherSettings).setOnClickListener(this);
        view.findViewById(R.id.settingAbout).setOnClickListener(this);
        view.findViewById(R.id.settingShowHiddenApps).setOnClickListener(this);

        Switch s = view.findViewById(R.id.settingHideIconSwitch);
        if (AppInfoDB.getInstance(getContext()).getDataAccessObject().isAppHidden("com.kobra.launcher"))
            s.setChecked(true);
        s.setOnCheckedChangeListener(this);

        preferences = new UserPreferences(getActivity());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.settingResetLauncherSettings)
            clearApplicationData();
        else if (view.getId() == R.id.settingShowHiddenApps)
            Navigation.findNavController(view).navigate(SettingFragmentDirections.actionSettingFragmentToAppsHiddenFragment());
        else if (view.getId() == R.id.settingSetDefaultLauncher)
            startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        else if (view.getId() == R.id.settingSystemSettings)
            startActivity(new Intent(Settings.ACTION_SETTINGS));
        else if (view.getId() == R.id.settingCustomiseLayout)
            launchSpanChangeDialog();
    }

    private void launchSpanChangeDialog() {
        final String[] array = {"3,4,5"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preferences.setLayoutSpan(Integer.parseInt(array[i]));
            }
        });
        builder.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        AppInfoDB.getInstance(getContext()).getDataAccessObject().updateHideStatus("com.kobra.launcher", b);
    }

    private void clearApplicationData() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setMessage("Reset Settings?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppInfoDB.getInstance(getContext()).getDataAccessObject().removeAppsData();
                File cacheDirectory = getActivity().getCacheDir();
                File applicationDirectory = new File(cacheDirectory.getParent());
                if (applicationDirectory.exists()) {
                    String[] fileNames = applicationDirectory.list();
                    for (String fileName : fileNames) {
                        if (!fileName.equals("lib")) {
                            deleteFile(new File(applicationDirectory, fileName));
                        }
                    }
                }
            }
        });
        builder.show();
    }

    private boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }
        return deletedAll;
    }

}
