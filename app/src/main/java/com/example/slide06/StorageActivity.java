package com.example.slide06;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StorageActivity extends AppCompatActivity {

    final static String FILE_NAME = "sample.txt";
    final int MY_PERMISSIONS_REQUEST_WRITE_DATA = 132;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        editText = findViewById(R.id.storage_edit_text);
    }

    public void saveCache(View view) {
        if(checkFilePermission()) {
            String text = editText.getText().toString();
            FileOutputStream fos = null;

            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(text.getBytes());
                editText.setText(null);
                Toast.makeText(StorageActivity.this, "Data saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error to save file in internal storage", Toast.LENGTH_SHORT).show();
            } finally {
                if(fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void loadCache(View view) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String text;
            try {
                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            editText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void savePDF(View view) {
        if(checkFilePermission()) {
            stringToPdf(editText.getText().toString());
            editText.setText(null);
        }
    }

    private boolean checkFilePermission () {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(this)
                        .setTitle("Permission required")
                        .setMessage("Give access to files is required in this app to save data!")
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(StorageActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_WRITE_DATA);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_DATA);
            }

            return false;
        } else {
            return true;
        }
    }

    public void stringToPdf(String data) {
        try {
            data = data + ".";
            PdfDocument document = new PdfDocument();
            PdfDocument.Page page = null;
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(100, 100, 1).create();
            // start a page
            page = document.startPage(pageInfo);
            if (page == null) {
                return;
            }

            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            canvas.drawText(data, 10, 10, paint);
            document.finishPage(page);

            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Slide06Files");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
            }

            File file = new File(mediaStorageDir + "/" + "sample" + ".pdf");
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    Log.d("App", "failed to create file");
                }
            }

            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(file);
            } catch (Exception e) {
                Log.e("TAG", e.getMessage());
                return;
            }

            try {
                document.writeTo(fOut);
            } catch (IOException e) {
                Log.e("createPdf()", "Error");
            }

            // close the document
            document.close();
            fOut.close();

            Toast.makeText(this, "PDF containing '" + data + "' has been saved in external Storage!",
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("onActivityResult","Can not make PDF.");
            Toast.makeText(this,"Directory has problem. Can not make PDF!", Toast.LENGTH_LONG).show();
        }
    }
}
