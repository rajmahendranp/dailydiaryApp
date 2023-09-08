package com.rampit.rask3.dailydiary;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//Updated on 25/01/2022 by RAMPIT
//createe file , folder , upload file and delete file from google drive
//createFile() - Created File in google drive
//createFolder() - Created Folder in google drive
//downloadFile() - Download file from google drive
//deleteFolderFile() - Delete file from google drive
//listDriveImageFiles() - List Image files from drive
//uploadFile() - upload file to drive

public class DriveServiceHelper {

    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;
    private final String TAG = "DRIVE_TAG";


    public DriveServiceHelper(Drive driveService) {

        mDriveService = driveService;
    }

    /**
     * Creates a text file in the user's My Drive folder and returns its file ID.
     */
    //createFile() - Created File in google drive
    //Params - folder id and filename
    //Created on 25/01/2022
    //Return - success or not
    public Task<GoogleDriveFileHolder> createFile(final String folderId, final String filename) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();

                List<String> root;
                if (folderId == null) {

                    root = Collections.singletonList("root");

                } else {

                    root = Collections.singletonList(folderId);
                }
                File metadata = new File()
                        .setParents(root)
                        .setMimeType("text/plain")
                        .setName(filename);

                File googleFile = mDriveService.files().create(metadata).execute();
                if (googleFile == null) {

                    throw new IOException("Null result when requesting file creation.");
                }
                googleDriveFileHolder.setId(googleFile.getId());
                return googleDriveFileHolder;
            }
        });
    }


// TO CREATE A FOLDER
//createFolder() - Created Folder in google drive
//Params - folder id and folder name
//Created on 25/01/2022
//Return - success or not
    public Task<GoogleDriveFileHolder> createFolder(final String folderName, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {

                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();

                List<String> root;
                if (folderId == null) {

                    root = Collections.singletonList("root");

                } else {
                    root = Collections.singletonList("root");
//                    root = Collections.singletonList(folderId);
                }
                File metadata = new File()
                        .setParents(root)
                        .setMimeType("application/vnd.google-apps.folder")
                        .setName(folderName);

                File googleFile = mDriveService.files().create(metadata).execute();
                if (googleFile == null) {
                    throw new IOException("Null result when requesting file creation.");
                }
                Log.d("file_id",String.valueOf(googleFile.getId()));
                googleDriveFileHolder.setId(googleFile.getId());
                return googleDriveFileHolder;
            }
        });
    }

    //downloadFile() - Download file from google drive
//Params - file location and file id
//Created on 25/01/2022
//Return - success or not
    public Task<Void> downloadFile(final java.io.File targetFile, final String fileId) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                // Retrieve the metadata as a File object.
                OutputStream outputStream = new FileOutputStream(targetFile);
                mDriveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
                return null;
            }
        });
    }

    //deleteFolderFile() - Delete file from google drive
//Params - file id
//Created on 25/01/2022
//Return - success or not
    public Task<Void> deleteFolderFile(final String fileId) {

        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                // Retrieve the metadata as a File object.
                if (fileId != null) {
                    mDriveService.files().delete(fileId).execute();
                }

                return null;

            }
        });
    }

// TO LIST FILES
//listDriveImageFiles() - List Image files from drive
//Params - NULL
//Created on 25/01/2022
//Return - success or not
    public List<File> listDriveImageFiles() throws IOException{

        FileList result;
        String pageToken = null;
        do {
            result = mDriveService.files().list()
/*.setQ("mimeType='image/png' or mimeType='text/plain'")This si to list both image and text files. Mind the type of image(png or jpeg).setQ("mimeType='image/png' or mimeType='text/plain'") */
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();

            pageToken = result.getNextPageToken();
        } while (pageToken != null);

        return result.getFiles();
    }

// TO UPLOAD A FILE ONTO DRIVE
//uploadFile() - upload file to drive
//Params - local location , file type and folder id
//Created on 25/01/2022
//Return - success or not
    public Task<GoogleDriveFileHolder> uploadFile(final java.io.File localFile,
                                                  final String mimeType, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                // Retrieve the metadata as a File object.

                List<String> root;
                if (folderId == null || folderId.equalsIgnoreCase("") || folderId.equalsIgnoreCase("null")) {
                    root = Collections.singletonList("root");
                } else {
                    root = Collections.singletonList(folderId);
//                    root = Collections.singletonList(folderId);
                }
                Log.d("rroooott",String.valueOf(root));
                File metadata = new File()
                        .setParents(root)
                        .setMimeType(mimeType)
                        .setName(localFile.getName());

                FileContent fileContent = new FileContent(mimeType, localFile);

                File fileMeta = mDriveService.files().create(metadata,
                        fileContent).execute();
                GoogleDriveFileHolder googleDriveFileHolder = new
                        GoogleDriveFileHolder();
                googleDriveFileHolder.setId(fileMeta.getId());
                Log.d("file_id11",String.valueOf(fileMeta.getId()));
                googleDriveFileHolder.setName(fileMeta.getName());
                return googleDriveFileHolder;
            }
        });
    }
}
