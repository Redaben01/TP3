package com.example.gitlist.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gitlist.R;

import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Url;

public class userslistviewmodel extends ArrayAdapter<gituser> {
    private int resource;

    public userslistviewmodel(@NonNull Context context, int ressource, List<gituser> data) {
        super(context, ressource, data);
        this.resource = ressource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listviewitem = convertView;
        if (listviewitem == null)
            listviewitem = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        CircleImageView imageView = listviewitem.findViewById(R.id.imageView2);
        TextView textViewUsername = listviewitem.findViewById(R.id.textViewUsername);
        TextView textViewScore = listviewitem.findViewById(R.id.textViewScore);

        textViewUsername.setText(getItem(position).username);
        textViewScore.setText(String.valueOf(getItem(position).score));
        try {
            URL url = new URL(getItem(position).avatarurl);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return listviewitem;
    }
}
