package com.jayant.pocketlibrary.ebooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jayant.pocketlibrary.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> subList;

    public SubjectAdapter(@NonNull @NotNull Context context, int resource, ArrayList<String> subList) {
        super(context, resource, subList);
        this.context = context;
        this.subList = subList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_subject, parent,false);

        TextView subName = convertView.findViewById(R.id.subName);
        subName.setText(getItem(position));

        return convertView;

    }

    @Nullable
    @Override
    public String getItem(int position) {
        return subList.get(position);
    }
}
