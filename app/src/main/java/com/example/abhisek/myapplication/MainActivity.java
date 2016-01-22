package com.example.abhisek.myapplication;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends ActionBarActivity implements TopActivity.TopListener {

    EditText topText;
    EditText bottomText;
    BottomActivity bottom;
    Uri uri;
    Drawable myDrawable;
    File f;
    final int SET_BACKGROUND = 1;
    final int CROP_IMAGE = 3;

    Uri mImageCaptureUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = ((WindowManager)
                getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getHeight();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_share) {
            savePicture();
            doShare();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void doShare() {
        Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND);
        picMessageIntent.setType("image/*");
        picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        startActivity(Intent.createChooser(picMessageIntent, "Send your picture"));
    }

    public void makeMeme (String s, String b) {
        topText = (EditText) findViewById(R.id.topText);
        bottomText = (EditText) findViewById(R.id.bottomText);

        bottom = (BottomActivity) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        bottom.setMemeText(topText.getText().toString(), bottomText.getText().toString());

    }

    public void selectPicture() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {
                    choosePhoto();
                } else if (options[item].equals("Take Photo")) {
                    takePhoto();
                }
            }

        });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public void savePicture() {
        bottom = (BottomActivity) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        bottom.getView().setDrawingCacheEnabled(true);
        Bitmap bitmap = bottom.getView().getDrawingCache();
        File file;
        long time = System.currentTimeMillis();

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            file = new File(android.os.Environment.getExternalStorageDirectory(), "Memes");
            if (!file.exists()) {
                file.mkdirs();
            }
            f = new File(file.getAbsolutePath(), "filename" + time + ".png");
            MediaScannerConnection.scanFile(this, new String[] { f.getPath() }, new String[] { "image/jpeg" }, null);
        }
        try {
            FileOutputStream ostream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
            ostream.close();
            Toast.makeText(this, "Meme was saved to gallery", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CROP_IMAGE);
    }

    public void choosePhoto() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), CROP_IMAGE);
    }


    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK && data != null){
           if (reqCode == CROP_IMAGE){
                Uri imageUri = data.getData();
                try {
                    Intent cropIntent = new Intent("com.android.camera.action.CROP");
                    // indicate image type and Uri
                    cropIntent.setDataAndType(imageUri, "image/*");
                    // set crop properties
                    cropIntent.putExtra("crop", "true");
                    // indicate aspect of desired crop
                    cropIntent.putExtra("aspectX", 4);
                    cropIntent.putExtra("aspectY", 3);
                    // indicate output X and Y
                    cropIntent.putExtra("outputX", 1280);
                    cropIntent.putExtra("outputY", 1280);
                    // retrieve data on return
                    cropIntent.putExtra("scaleUpIfNeeded", true);

                    cropIntent.putExtra("return-data", true);
                    // start the activity - we handle returning in onActivityResult
                    startActivityForResult(cropIntent, SET_BACKGROUND);
                }
                // respond to users whose devices do not support the crop action
                catch (ActivityNotFoundException anfe) {
                    // display an error message
                    String errorMessage = "Whoops - your device doesn't support the crop action!";
                    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }

           }
           else if (reqCode == SET_BACKGROUND) {
               uri = data.getData();
               bottom = (BottomActivity) getSupportFragmentManager().findFragmentById(R.id.fragment2);
               try {
                   InputStream inputStream = getContentResolver().openInputStream(uri);
                   myDrawable = Drawable.createFromStream(inputStream, uri.toString());
                   bottom.getView().setBackground(myDrawable);
               } catch (FileNotFoundException e) {}
           }
        }
        else if (resCode == RESULT_CANCELED) {
            Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
        }
    }



}
