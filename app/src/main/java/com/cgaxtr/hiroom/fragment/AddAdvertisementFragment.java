package com.cgaxtr.hiroom.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.network.ImageUploader;
import com.cgaxtr.hiroom.utils.Permissions;
import com.cgaxtr.hiroom.utils.SessionManager;
import com.cgaxtr.hiroom.model.Advertisement;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.cgaxtr.hiroom.utils.UrlsAPI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddAdvertisementFragment extends Fragment implements View.OnClickListener{

    private static final int REQUEST_IMAGE_GALLERY = 1;
    private static final int PERMISSION = 2;

    private EditText title, city, address, number, floor, size, price, description;
    private Spinner type;
    private Button button;
    private View view;
    private ImageView img1, img2, img3, img4, img5, img6;
    private List<String> paths;
    private int indexImg;

    public AddAdvertisementFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_advertisement, container, false);

        view = v;
        paths = new ArrayList<>();
        title = v.findViewById(R.id.addTitle);
        city = v.findViewById(R.id.addCity);
        address = v.findViewById(R.id.addAdress);
        number = v.findViewById(R.id.addNumber);
        floor = v.findViewById(R.id.addFloor);
        size = v.findViewById(R.id.addSize);
        price = v.findViewById(R.id.addPrice);
        description = v.findViewById(R.id.addDescription);
        type = v.findViewById(R.id.addType);

        button = v.findViewById(R.id.addSubmit);
        button.setOnClickListener(this);

        img1 = v.findViewById(R.id.addImg1);
        img1.setOnClickListener(this);

        img2 = v.findViewById(R.id.addImg2);
        img2.setOnClickListener(this);

        img3 = v.findViewById(R.id.addImg3);
        img3.setOnClickListener(this);

        img4 = v.findViewById(R.id.addImg4);
        img4.setOnClickListener(this);

        img5 = v.findViewById(R.id.addImg5);
        img5.setOnClickListener(this);

        img6 = v.findViewById(R.id.addImg6);
        img6.setOnClickListener(this);

        return v;
    }

    private boolean validate(){
        ArrayList<EditText> toValidate = new ArrayList<>();
        toValidate.add(title);
        toValidate.add(city);
        toValidate.add(address);
        toValidate.add(number);
        toValidate.add(floor);
        toValidate.add(size);
        toValidate.add(price);
        toValidate.add(description);

        for (EditText editText : toValidate){
            if(editText.getText().toString().isEmpty()){
                //TODO
                editText.setError("prueba");
                return false;
            }
        }

        return true;
    }

    private void sendToServer() throws JSONException {
        Advertisement ad = populateAdvertisement();
        JSONObject jsonAd = new JSONObject(new Gson().toJson(ad));

        JsonObjectRequest rq = new JsonObjectRequest(Request.Method.PUT, UrlsAPI.ADVERTISEMENT, jsonAd, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int id;

                Log.d("VOLLEY", "response");

                try {
                    id = response.getInt("idAdvertisement");
                    Log.d("ID", Integer.toString(id));
                    ImageUploader uploader = new ImageUploader(UrlsAPI.UPLOAD_IMAGE_ROOM, id);
                    Log.d("LIST", paths.toString());
                    for (int i = 0; i < paths.size(); i++)
                        uploader.execute(paths.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO
                Log.d("VOLLEY", "error");
            }
        });

        VolleySingleton.getInstance(getContext()).getRequestQueue().add(rq);
    }

    private Advertisement populateAdvertisement(){
        Advertisement ad = new Advertisement();

        ad.setOwnerId(new SessionManager(getContext()).getId());
        ad.setTitle(title.getText().toString());
        ad.setCity(city.getText().toString());
        ad.setAddress(address.getText().toString());
        ad.setNumber(Integer.parseInt(number.getText().toString()));
        ad.setFloor(Integer.parseInt(floor.getText().toString()));
        ad.setSize(Integer.parseInt(size.getText().toString()));
        ad.setPrice(Integer.parseInt(price.getText().toString()));
        ad.setDescription(description.getText().toString());
        ad.setType(type.getSelectedItem().toString());

        return ad;
    }

    private void setPic(String path, int id){

        ImageView img = view.findViewById(id);
        img.setImageBitmap(BitmapFactory.decodeFile(path));

    }

    @Override
    public void onClick(View v) {
        indexImg = v.getId();

        switch (indexImg){
            case R.id.addSubmit:
                if (validate()){
                    try {
                        sendToServer();
                    } catch (JSONException e) {
                        //TODO
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.addImg1:
            case R.id.addImg2:
            case R.id.addImg3:
            case R.id.addImg4:
            case R.id.addImg5:
            case R.id.addImg6:

                if(!Permissions.checkPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Permissions.requestPermissions(this, PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE);
                }else{
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
                } else {
                    //TODO
                    //Snackbar.make(this, "", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContext().getContentResolver().query(imageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            paths.add(picturePath);
            setPic(picturePath, indexImg);
        }
    }
}
