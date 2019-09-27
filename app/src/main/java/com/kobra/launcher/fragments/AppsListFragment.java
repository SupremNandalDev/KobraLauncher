package com.kobra.launcher.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kobra.launcher.R;
import com.kobra.launcher.Receiver;
import com.kobra.launcher.adapters.AppsListAdapter;
import com.kobra.launcher.callbacks.WelcomeCallbacks;
import com.kobra.launcher.model.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AppsListFragment extends Fragment {

    private WelcomeCallbacks callbacks;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callbacks = (WelcomeCallbacks) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apps_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateList(view);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateList(view);
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
        intentFilter.addDataScheme("package");
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, intentFilter);
    }

    private void updateList(View view){
        RecyclerView recyclerView = view.findViewById(R.id.appsListRecyclerView);

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableApps = Objects.requireNonNull(getActivity()).getPackageManager().queryIntentActivities(i, 0);
        List<AppInfo> apps = new ArrayList<>();
        for (ResolveInfo ri : availableApps)
            if (!ri.loadLabel(getActivity().getPackageManager()).toString().contentEquals("kobraLauncher"))
                apps.add(new AppInfo(ri.loadLabel(getActivity().getPackageManager()).toString(), ri.activityInfo.packageName, ""));


        Collections.sort(apps);

        AppsListAdapter appsListAdapter = new AppsListAdapter(getContext(), apps, new AppsListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, AppInfo info) {
                startActivity(Objects.requireNonNull(getActivity()).getPackageManager().getLaunchIntentForPackage(info.getAppPackage()));
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),5,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(appsListAdapter);
    }
}