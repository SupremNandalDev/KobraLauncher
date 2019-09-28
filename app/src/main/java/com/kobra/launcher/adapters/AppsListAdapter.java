package com.kobra.launcher.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kobra.launcher.R;
import com.kobra.launcher.model.AppInfo;

import java.util.HashMap;
import java.util.List;


public class AppsListAdapter extends RecyclerView.Adapter<AppsListAdapter.MyViewHolder> {

    private Context context;
    private List<AppInfo> appInfoList;
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private HashMap<String, Integer> appIcons;

    public AppsListAdapter(Context context, List<AppInfo> appInfoList, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
        this.context = context;
        this.appInfoList = appInfoList;
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
        putDataIntoHashMap();
    }

    private void putDataIntoHashMap() {
        appIcons = new HashMap<>();
        appIcons.put("amazon shopping", R.drawable.ic_amazon);
        appIcons.put("calculator", R.drawable.ic_calculator);
        appIcons.put("calendar", R.drawable.ic_calendar);
        appIcons.put("camera", R.drawable.ic_camera);
        appIcons.put("chrome", R.drawable.ic_chrome);
        appIcons.put("clock", R.drawable.ic_clock);
        appIcons.put("compass", R.drawable.ic_compass);
        appIcons.put("contacts", R.drawable.ic_contacts);
        appIcons.put("downloads", R.drawable.ic_download);
        appIcons.put("drive", R.drawable.ic_drive);
        appIcons.put("duo", R.drawable.ic_duo);
        appIcons.put("file manager", R.drawable.ic_file_manager);
        appIcons.put("gallery", R.drawable.ic_gallery);
        appIcons.put("gmail", R.drawable.ic_gmail);
        appIcons.put("google", R.drawable.ic_google);
        appIcons.put("instagram", R.drawable.ic_instagram);
        appIcons.put("maps", R.drawable.ic_maps);
        appIcons.put("messaging", R.drawable.ic_messaging);
        appIcons.put("music", R.drawable.ic_music);
        appIcons.put("netflix", R.drawable.ic_netflix);
        appIcons.put("notes", R.drawable.ic_note);
        appIcons.put("phone", R.drawable.ic_phone);
        appIcons.put("photos", R.drawable.ic_photos);
        appIcons.put("play music", R.drawable.ic_music);
        appIcons.put("recorder", R.drawable.ic_voice);
        appIcons.put("scanner", R.drawable.ic_scanner);
        appIcons.put("screen recorder", R.drawable.ic_screen_recorder);
        appIcons.put("settings", R.drawable.ic_settings);
        appIcons.put("whatsapp", R.drawable.ic_whatsapp);
        appIcons.put("youtube", R.drawable.ic_youtube);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_app_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppInfo info = appInfoList.get(position);
        holder.textView.setText(info.getAppTitle());
        try{
            holder.imageView.setImageResource(appIcons.get(info.getIconTitle().toLowerCase().trim()));
        }catch (Exception e){
            e.printStackTrace();
            try {
                holder.imageView.setImageDrawable(context.getPackageManager().getApplicationIcon(context.getPackageManager().getApplicationInfo(info.getAppPackage(), 0)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, AppInfo info);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, AppInfo info);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imageView;
        private TextView textView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemAppListIcon);
            textView = itemView.findViewById(R.id.itemAppListTitle);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, appInfoList.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            itemLongClickListener.onItemLongClick(view, appInfoList.get(getAdapterPosition()));
            return true;
        }
    }
}
