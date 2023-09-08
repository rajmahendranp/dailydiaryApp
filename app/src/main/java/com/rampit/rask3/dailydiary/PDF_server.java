package com.rampit.rask3.dailydiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;


//Updated on 25/01/2022 by RAMPIT
//used to create PDF ( NOT USED )

public class PDF_server extends AppCompatActivity {
    private static final int REQUEST_CODE_DOC = 1;
    ImageButton btnAttach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfserver);

        getSupportActionBar().setTitle("Registeration");

        btnAttach = (ImageButton) findViewById(R.id.attachImageButton);

        // view products click event
        btnAttach.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
//                Intent i = new Intent(getApplicationContext(), PDF_server.class);
//                startActivity(i);
                getDocument();

            }
        });
    }


    public void attachImageButton_OnClick(View view) {
        Intent intent = new Intent(this, PDF_server.class);
        startActivity(intent);
    }


    private void getDocument()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/msword,application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_CODE_DOC);
    }


    @Override
    protected void onActivityResult(int req, int result, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(req, result, data);
        if (result == RESULT_OK)
        {
            Uri fileuri = data.getData();
            String docFilePath = getFileNameByUri(this, fileuri);
            Log.d("docfilepath",docFilePath);
        }
    }

// get file path

    private String getFileNameByUri(Context context, Uri uri)
    {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file;
        if (uri.getScheme().toString().compareTo("content") == 0)
        {
            Cursor cursor = context.getContentResolver().query(uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION }, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        }
        else
        if (uri.getScheme().compareTo("file") == 0)
        {
            try
            {
                file = new File(new URI(uri.toString()));
                if (file.exists())
                    filepath = file.getAbsolutePath();

            }
            catch (URISyntaxException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            filepath = uri.getPath();
        }
        return filepath;
    }
}