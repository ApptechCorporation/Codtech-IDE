package com.androidide.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidide.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileTreeAdapter extends BaseExpandableListAdapter {

    private Context context;
    private File rootFile;
    private List<File> rootChildren;

    public FileTreeAdapter(Context context, File rootFile) {
        this.context = context;
        this.rootFile = rootFile;
        this.rootChildren = getChildren(rootFile);
    }

    private List<File> getChildren(File file) {
        if (file == null || !file.isDirectory()) {
            return new ArrayList<>();
        }
        
        File[] files = file.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        
        List<File> children = new ArrayList<>(Arrays.asList(files));
        // Sort: directories first, then files
        children.sort((a, b) -> {
            if (a.isDirectory() && !b.isDirectory()) return -1;
            if (!a.isDirectory() && b.isDirectory()) return 1;
            return a.getName().compareTo(b.getName());
        });
        
        return children;
    }

    @Override
    public int getGroupCount() {
        return rootChildren.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        File file = rootChildren.get(groupPosition);
        if (file.isDirectory()) {
            return getChildren(file).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return rootChildren.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        File parent = rootChildren.get(groupPosition);
        return getChildren(parent).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }
        
        File file = rootChildren.get(groupPosition);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(file.getName());
        textView.setTextColor(context.getColor(R.color.text_primary));
        
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }
        
        File file = getChildren(rootChildren.get(groupPosition)).get(childPosition);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText("  " + file.getName());
        textView.setTextColor(context.getColor(R.color.text_secondary));
        
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
