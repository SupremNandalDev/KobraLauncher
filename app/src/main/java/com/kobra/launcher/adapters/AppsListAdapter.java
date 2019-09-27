package com.kobra.launcher.adapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kobra.launcher.KobraApplication;
import com.kobra.launcher.R;
import com.kobra.launcher.model.AppInfo;

import java.util.List;


public class AppsListAdapter extends RecyclerView.Adapter<AppsListAdapter.MyViewHolder>{

    private Context context;
    private List<AppInfo> appInfoList;
    private ItemClickListener itemClickListener;

    public AppsListAdapter(Context context, List<AppInfo> appInfoList, ItemClickListener itemClickListener) {
        this.context = context;
        this.appInfoList = appInfoList;
        this.itemClickListener = itemClickListener;
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
            holder.imageView.setImageDrawable(context.getPackageManager().getApplicationIcon(context.getPackageManager().getApplicationInfo(info.getAppPackage(),0)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, AppInfo info);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView textView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemAppListIcon);
            textView = itemView.findViewById(R.id.itemAppListTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view,appInfoList.get(getAdapterPosition()));
        }
    }
}
