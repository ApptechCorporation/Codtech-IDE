package com.androidide.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import androidx.fragment.app.Fragment;
import com.androidide.R;
import com.androidide.project.FileTreeAdapter;
import java.io.File;

public class FileExplorerFragment extends Fragment {

    private ExpandableListView fileTreeView;
    private FileTreeAdapter adapter;
    private File projectRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_explorer, container, false);
        
        fileTreeView = view.findViewById(R.id.file_tree);
        
        // Initialize with project root
        projectRoot = new File(requireContext().getExternalFilesDir(null), "CurrentProject");
        
        if (projectRoot.exists()) {
            adapter = new FileTreeAdapter(requireContext(), projectRoot);
            fileTreeView.setAdapter(adapter);
        }
        
        return view;
    }

    public void setProjectRoot(File root) {
        this.projectRoot = root;
        if (projectRoot.exists()) {
            adapter = new FileTreeAdapter(requireContext(), projectRoot);
            fileTreeView.setAdapter(adapter);
        }
    }

    public File getSelectedFile() {
        // TODO: Implement file selection
        return null;
    }
}
