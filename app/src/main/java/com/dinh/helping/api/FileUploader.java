package com.dinh.helping.api;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.PhotoModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class FileUploader {
    public FileUploaderCallback fileUploaderCallback;
    private File[] files;
    public int uploadIndex = -1;
    private long totalFileLength = 0;
    private long totalFileUploaded = 0;
    private UploadInterface uploadInterface;
    private BaseResponseModel responses;
    private PhotoModel itemBookingModel;

    private interface UploadInterface {
//        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<PhotoModel>> uploadFile(@Body RequestBody params);
    }

    public interface FileUploaderCallback {
        void onError();

        void onFinish(BaseResponseModel responses);

        void onProgressUpdate(int currentpercent, int totalpercent, int filenumber);
    }

    public class PRRequestBody extends RequestBody {
        private File mFile;

        private static final int DEFAULT_BUFFER_SIZE = 2048;

        public PRRequestBody(final File file) {
            mFile = file;

        }

        @Override
        public MediaType contentType() {
            // i want to upload only images
            return MediaType.parse("image/*");
        }

        @Override
        public long contentLength() throws IOException {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            long fileLength = mFile.length();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            FileInputStream in = new FileInputStream(mFile);
            long uploaded = 0;

            try {
                int read;
                Handler handler = new Handler(Looper.getMainLooper());
                while ((read = in.read(buffer)) != -1) {

                    // update progress on UI thread
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                    uploaded += read;
                    sink.write(buffer, 0, read);
                }
            } finally {
                in.close();
            }
        }
    }

    public FileUploader() {
        uploadInterface = ApiClient.getClient().create(UploadInterface.class);
    }

    public void uploadFiles(PhotoModel itemBookingModel, File[] files, FileUploaderCallback fileUploaderCallback) {
        this.fileUploaderCallback = fileUploaderCallback;
        this.itemBookingModel = itemBookingModel;
        this.files = files;
        this.uploadIndex = -1;
        totalFileUploaded = 0;
        totalFileLength = 0;
        uploadIndex = -1;
        for (int i = 0; i < files.length; i++) {
            totalFileLength = totalFileLength + files[i].length();
        }
        uploadNext();
    }

    private void uploadNext() {
        if (files.length > 0) {
            if (uploadIndex != -1 && uploadIndex < files.length)
                totalFileUploaded = totalFileUploaded + files[uploadIndex].length();
            uploadIndex++;
            if (uploadIndex < files.length) {
                uploadSingleFile(uploadIndex);
            } else {
                fileUploaderCallback.onFinish(responses);
            }
        } else {
            fileUploaderCallback.onFinish(responses);
        }
    }

    private void uploadSingleFile(final int index) {
        PRRequestBody fileBody = new PRRequestBody(files[index]);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("product_photo", files[index].getName(), fileBody);

        builder.addFormDataPart("product_id", itemBookingModel.getProduct_id());
        //builder.addFormDataPart("type_manager", "update_result_booking");
        builder.addFormDataPart("detect", "create_image_product").setType(MultipartBody.FORM);

        RequestBody requestBody = builder.build();

        Call<BaseResponseModel<PhotoModel>> call;
        call = uploadInterface.uploadFile(requestBody);

        call.enqueue(new Callback<BaseResponseModel<PhotoModel>>() {
            @Override
            public void onResponse
                    (Call<BaseResponseModel<PhotoModel>> call, retrofit2.Response<BaseResponseModel<PhotoModel>> result) {
                if (!TextUtils.isEmpty(result.body().getSuccess()) && result.body().getSuccess().equalsIgnoreCase("true")) {
                    responses = result.body();
                    uploadNext();
                }
                uploadNext();
            }

            @Override
            public void onFailure(Call<BaseResponseModel<PhotoModel>> call, Throwable t) {
                fileUploaderCallback.onError();
            }
        });
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            int current_percent = (int) (100 * mUploaded / mTotal);
            int total_percent = (int) (100 * (totalFileUploaded + mUploaded) / totalFileLength);
            fileUploaderCallback.onProgressUpdate(current_percent, total_percent, uploadIndex + 1);
        }
    }
}
