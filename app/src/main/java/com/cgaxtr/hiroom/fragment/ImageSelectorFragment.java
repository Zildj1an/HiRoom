package com.cgaxtr.hiroom.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgaxtr.hiroom.R;

public class ImageSelectorFragment extends BottomSheetDialogFragment {

    private Callback listener;
    private TextView camera, gallery;

    public interface Callback{
        void onClickCamera();
        void onClickGallery();
    }

    public ImageSelectorFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet, container, false);

        camera = v.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCamera();
                dismiss();
            }
        });

        gallery = v.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickGallery();
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Callback");
        }
    }
}
