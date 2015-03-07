package com.themind.vishnu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.themind.vishnu.Bean.VisionBoard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class ImageControllerActivity extends ActionBarActivity {


    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    File f;
    ProgressDialog mProgressDialog;
    String tempPath;
    CheckNetworkState internetState;
    Boolean isInternetPresent;
    //private String selectedImagePath;
    //(ParseImageView) v.findViewById(R.id.icon);
    private ParseImageView parseImageView;
    private Uri selectedImageUri = null;
    private ImageView noPicture;

    private ParseFile photoFile;


    public ImageControllerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_controller_fragment);

        // get action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();


        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffb107")));


        parseImageView = (ParseImageView) findViewById(R.id.picture);
        noPicture = (ImageView) findViewById(R.id.no_picture);

        if (parseImageView.getDrawable() == null) {
            noPicture.setImageResource(R.drawable.no_photo_icon);
        }

        //parseImageView.setSaveEnabled(true);
        //noPicture.setImageDrawable(null);


    }


    private void selectImage() {
        final CharSequence[] items = {"Open camera", "Choose from gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ImageControllerActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Open camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        Bitmap bm;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                        bm = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

                        // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
                        parseImageView.setImageBitmap(bm);
                        noPicture.setImageDrawable(null);

                    /*

                        WAIT FOR USER TO PRESS ON SAVE BUTTON IN MENU>> IF PRESSED SAVE THE IMAGE
                    */

                        String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                        f.delete();


                        OutputStream fOut;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        try {
                            fOut = new FileOutputStream(file);
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

                            //bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            // byte[] scaledData = bos.toByteArray();

                            //saveImage(scaledData);

                            fOut.flush();
                            fOut.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
                }

                break;
            case SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = data.getData();
                    Bitmap bm;
                    tempPath = getPath(selectedImageUri, ImageControllerActivity.this);


                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bm = BitmapFactory.decodeFile(tempPath, bitmapOptions);
                    parseImageView.setImageBitmap(bm);
                    noPicture.setImageDrawable(null);

                /*

                        WAIT FOR USER TO PRESS ON SAVE BUTTON IN MENU>> IF PRESSED SAVE THE IMAGE
                 */

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
                }
                break;
        }// end of switch

    }// end of onActivityResult


    public String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_add_image) {
            selectImage();

            return true;
        }

        if (id == R.id.action_save_image) {

            if (parseImageView.getDrawable() != null) {
                internetState = new CheckNetworkState(getApplicationContext());
                isInternetPresent = internetState.isConnectingToInternet();
                if (isInternetPresent) {
                    new SaveImage().execute();
                } else {
                    // TODO: make a dialog box instead.
                    Toast.makeText(getApplicationContext(), "uh-oh! Cannot save... Seems you are offline. Please check your internet settings and try again", Toast.LENGTH_LONG).show();
                }
            } else if (parseImageView.getDrawable() == null) {
                Toast.makeText(getApplicationContext(), "No image to save. Please add an image by pressing the plus button.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void imageSavedToast() {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "Image Saved to your Vision Board!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void imageSaveError() {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "Could not upload the image. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class SaveImage extends AsyncTask<Void, Void, Void> {
        byte[] scaledData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            BitmapDrawable drawable = (BitmapDrawable) parseImageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            scaledData = bos.toByteArray();


            // Create a progress dialog
            mProgressDialog = new ProgressDialog(ImageControllerActivity.this);
            // Set progress dialog title
            mProgressDialog.setTitle("Uploading to Vision Board");
            // Set progress dialog message
            mProgressDialog.setMessage("Please wait while the image is uploaded. This might take several moments depending on the image size and network speed.");
            mProgressDialog.setIndeterminate(false);
            // Show progress dialog

            mProgressDialog.show();


        }

       /* @Override
        protected void onProgressUpdate(Progress... values) {
            super.onProgressUpdate();
            // Executes whenever publishProgress is called from doInBackground
            // Used to update the progress indicator
            progressBar.setProgress();
        }*/


        @Override
        protected Void doInBackground(Void... bm) {

            photoFile = new ParseFile("vision_board_photo.jpg", scaledData);
            VisionBoard visionBoardPictures = new VisionBoard();
            visionBoardPictures.setACL(new ParseACL(ParseUser.getCurrentUser()));
            visionBoardPictures.setUser(ParseUser.getCurrentUser());

            try {
                visionBoardPictures.put("pictures", photoFile);
                visionBoardPictures.save();
                imageSavedToast();

            } catch (ParseException e) {
                // e.printStackTrace();

                imageSaveError();
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;

                }


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

        }
    }
}