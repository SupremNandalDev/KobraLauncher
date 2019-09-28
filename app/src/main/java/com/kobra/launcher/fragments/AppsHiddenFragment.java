package com.kobra.launcher.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kobra.launcher.R;
import com.kobra.launcher.adapters.AppsListAdapter;
import com.kobra.launcher.customUI.RollingLayoutManager;
import com.kobra.launcher.database.AppInfoDB;
import com.kobra.launcher.database.AppInfoDao;
import com.kobra.launcher.model.AppInfo;
import com.kobra.launcher.preferences.UserPreferences;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.kobra.launcher.adapters.AppsListAdapter.ItemClickListener;
import static com.kobra.launcher.adapters.AppsListAdapter.ItemLongClickListener;

public class AppsHiddenFragment extends Fragment implements ItemClickListener, ItemLongClickListener {

    private AppInfoDao dao;
    private RecyclerView recyclerView;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String packageName = intent.getPackage();
            AppInfo info = dao.getAppInfoByPackage(packageName);
            if (info != null)
                dao.removeApp(packageName);
            updateUI();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apps_hidden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = AppInfoDB.getInstance(getContext()).getDataAccessObject();
        recyclerView = view.findViewById(R.id.appsHiddenRecyclerView);

        updateUI();
        startGettingCalls();
    }

    private void startGettingCalls() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
        intentFilter.addDataScheme("package");
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        startGettingCalls();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCachingForRemoved();
    }

    private void updateCachingForRemoved() {
        List<AppInfo> tempList = dao.getAllHidden();
        for (AppInfo info : tempList)
            if (!isPackageInstalled(info.getAppPackage()))
                dao.removeApp(info.getAppPackage());
        updateUI();
    }

    private void updateUI() {
        List<AppInfo> appInfo = dao.getAllHidden();
        Collections.sort(appInfo);
        recyclerView.setLayoutManager(new RollingLayoutManager(getContext(), new UserPreferences(getActivity()).getLayoutSpan(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new AppsListAdapter(getContext(), appInfo, this, this));
    }

    @Override
    public void onItemClick(View view, AppInfo info) {
        startActivity(Objects.requireNonNull(getActivity()).getPackageManager().getLaunchIntentForPackage(info.getAppPackage()));
    }

    @Override
    public void onItemLongClick(View view, final AppInfo info) {
        Toast.makeText(getContext(), "Long Click Callback Received...", Toast.LENGTH_SHORT).show();
        String[] appOptions = {"App Info", "Customize", "Show App", "Uninstall"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setItems(appOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0)
                    startActivity(new Intent()
                            .setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
                            .setData(Uri.parse(info.getAppPackage()))
                            .setComponent(new ComponentName(
                                    "com.android.settings",
                                    "com.android.settings.applications.InstalledAppDetails"
                            )));
                else if (i == 1) {

                } else if (i == 2) {
                    dao.updateHideStatus(info.getAppPackage(), false);
                    updateUI();
                } else if (i == 3)
                    startActivity(new Intent(Intent.ACTION_UNINSTALL_PACKAGE, Uri.parse("package:" + info.getAppPackage())));
            }
        });
        builder.show();
    }

    private boolean isPackageInstalled(String packageName) {
        try {
            Objects.requireNonNull(getActivity()).getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}