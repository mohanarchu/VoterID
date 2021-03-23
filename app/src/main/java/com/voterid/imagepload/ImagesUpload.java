package com.voterid.imagepload;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.Json;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.ImageContext;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.voterid.aashi.voterid.R;
import com.voterid.imagepload.Interface.MainInterface;
import com.voterid.imagepload.Interface.NetworkClient;
import com.voterid.imagepload.activity.presenter.UserPresenter;
import com.voterid.imagepload.pojo.UserPojo;
import com.voterid.imagepload.sesssion.Sessions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ImagesUpload extends AppCompatActivity implements UserPresenter.UserView {
    private static final String CLOUD_VISION_API_KEY = "AIzaSyCZZNBatP1OgUCyR1TEb0NUHjABl3ZhrFk";
    public  static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;
    private static final String TAG = ImagesUpload.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public  static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public  static final int CAMERA_IMAGE_REQUEST = 3;
    private TextView mImageDetails;
    private ImageView mMainImage;
    Button raed,names;
    static RecyclerView recyclerView,recyclerView1;
    static RecyclerView imageRecycle;
    int counter=0;
    Adapters adapters;
    ArrayList<String> memberIds;
    ArrayList<String> candidateNames;
    ArrayList<String> fathername;
    ArrayList<String> allTexts;
    ArrayList<String> alldatas;
    final Handler handler = new Handler();
    ArrayList<String> gendre;
    ArrayList<String>  age;
    String boothid;
    Sessions sessions;
    ProgressDialog progressDialog;
    ArrayList<Bitmap> chunkedImages;
    ArrayList<Bitmap> newChunkImage;
    ArrayList<MainArray> mainArrays;
    FloatingActionButton upload;
    ArrayList<File> filearrays;
    JsonObject jsonObject;
    ArrayList<UserPojo.Result> mainArrayss;
    static AsyncTask<Object, Void, String> labelDetectionTask;
    int adapterPosoition;
    UserPresenter userPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_upload);
        userPresenter = new UserPresenter(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressDialog = new ProgressDialog(ImagesUpload.this);
        Intent intent =getIntent();
        if (intent != null)
        {
            boothid = intent.getStringExtra("boothId");
        }
        mainArrays = new ArrayList<>();
        sessions = new Sessions(getApplicationContext());
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        names =findViewById(R.id.readNames);
        upload =(FloatingActionButton)findViewById(R.id.uploads);
        raed =(Button)findViewById(R.id.read);
        memberIds = new ArrayList<>();
        candidateNames = new ArrayList<>();
        fathername = new ArrayList<>();
        allTexts = new ArrayList<>();
        alldatas = new ArrayList<>();
        gendre = new ArrayList<>();
        age = new ArrayList<>();
        mainArrayss = new ArrayList<>();
        filearrays = new ArrayList<>();
        imageRecycle =(RecyclerView)findViewById(R.id.allImages);
        final LinearLayoutManager layoutManagerss = new LinearLayoutManager(getApplicationContext());
        layoutManagerss.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycle.setLayoutManager(layoutManagerss);
        recyclerView1= (RecyclerView)findViewById(R.id.nameRecycle);
        raed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String example =  getString(R.string.jsons);
                ArrayList<String> ids = new ArrayList<>();
                Pattern p = Pattern.compile("[A-Z]{3}[0-9]{7}");
                Matcher m = p.matcher(example);
                while (m.find()) {
                    Log.i("Find", "Values " +  m.group()  );
                    ids.add(m.group());
                }
                //recyclerView.setAdapter(new IdAdapter(ids));
            }
        });
        names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String examples = getString(R.string.jsons).trim();
                String finals = examples.replaceAll("\\s","");
                String finalss = finals.replaceAll("\\d","");
                Pattern ps = Pattern.compile("பெயர்:\\w+");
                Matcher ms = ps.matcher(finalss);
                ArrayList<String > arrayList = new ArrayList<>();
                ArrayList<String > myList = new ArrayList<>();
                while (ms.find() ) {
                    arrayList.add(ms.group());
                    Log.i("Find", "Values " +  ms.group() + finals  );
                }
                for (int i=0;i<arrayList.size();i =i+2)
                {
                    myList.add(arrayList.get(i));
                }
            }
        });
        upload.setOnClickListener(v -> {
            RequestBody requestBody;
            JsonArray jsonArray = new JsonArray();
            RequestBody booth,voter,candidate,father,age,sex,door,userid,alldata;
            HashMap<String,RequestBody> mp = new  HashMap<>();
            HashMap<String,RequestBody> maps = new  HashMap<>();
            List< JsonObject> list = new ArrayList<>();
           for (int i=0;i<mainArrayss.size();i++) {
               booth=RequestBody.create(MediaType.parse("multipart/form-data"),"5c94ea6ad8edb13578370e11"  );
               voter =RequestBody.create(MediaType.parse("multipart/form-data"),"id");
               candidate =RequestBody.create(MediaType.parse("multipart/form-data"),"name");
               father =RequestBody.create(MediaType.parse("multipart/form-data"),"father");
               age =RequestBody.create(MediaType.parse("multipart/form-data"),"age");
               sex =RequestBody.create(MediaType.parse("multipart/form-data"),"sex");
               door =RequestBody.create(MediaType.parse("multipart/form-data"),"door");
               userid =RequestBody.create(MediaType.parse("multipart/form-data"),sessions.getId());
               alldata =RequestBody.create(MediaType.parse("multipart/form-data"),"data");
               mp.put("Booth_Id", booth);
               mp.put("VoterIdNumber",voter);
               mp.put("CandidateName",candidate);
               mp.put("FatherName",father);
               mp.put("CandidateAge",age);
               mp.put("CandidateSex",sex);
               mp.put("DoorNumber",door);
               mp.put("User_Id",userid);
               mp.put("ALL_DATA",alldata);
               //list.add(jsonObject);
               jsonArray.add(jsonObject);
           }
            HashMap<String, JsonArray> listHashMap = new HashMap<>();
            listHashMap.put("Info",jsonArray);
           //  Log.i("TAG" ,"Uploaded strings"+ jsonArray.size()+ jsonArray);
            MultipartBody.Part body = null;
            HashMap<String, List<MultipartBody.Part>> listHashMaps = new HashMap<>();
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
            body =  MultipartBody.Part.createFormData("file", savebitmap("image",chunkedImages.get(0)).getName(),requestFile);
            Log.i("TAG" ,"Uploaded strings"+savebitmap("image",chunkedImages.get(0)).length());
//            getObservable(listHashMap,body).subscribeWith(getCoomplaint());
        });
        fab.setOnClickListener(view ->
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(ImagesUpload.this);
            builder. setMessage(R.string.dialog_select_prompt)
                    .setPositiveButton(R.string.dialog_select_gallery,
                            (dialog, which) -> startGalleryChooser())
                    .setNegativeButton(R.string.dialog_select_camera, (dialog, which) -> startCamera());
            builder.create().show();
        });
        mImageDetails = findViewById(R.id.image_details);
        mMainImage = findViewById(R.id.main_image);
    }
    private Map<String,RequestBody> createPartFromArray(String[] skills)
    {
        Map<String, RequestBody> skill = new HashMap<String, RequestBody>();
        RequestBody requestFile ;
        for(int i=0 ;i<skills.length;i++) {
            requestFile = RequestBody.create(MultipartBody.FORM,skills[i]);
            skill.put("skill["+i+"]", requestFile);
        }
        return skill;

    }
//    public Observable<ResponseBody> getObservable( HashMap<String, JsonArray>   listHashMap,
//                                                    MultipartBody.Part file)
//    {
////        return NetworkClient.getRetrofit().create(MainInterface.class)
////                .citizenRegister(file)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread());
//    }
//    public DisposableObserver<ResponseBody> getCoomplaint()
//    {
//        return new DisposableObserver<ResponseBody>() {
//            @Override
//            public void onNext(ResponseBody responseBody) {
//
//
//
//            }
//            @Override
//            public void onError(Throwable ee) {
//
//
//            }
//            @Override
//            public void onComplete() {
//
//            }
//        };
//    }
    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            @SuppressLint("IntentReset") Intent i =
                    new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, GALLERY_IMAGE_REQUEST);
        }
    }

    private File savebitmap(String filename,Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }

    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplication().getFilesDir();
        File imageFile = new File(filesDir, name + ".png");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile;
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA))
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }
    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            //    uploadImage(data.getData());
            //performCrop(data.getData());
            startCropImageActivity( data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK &&  data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri selectedImage = getImageUri(getApplicationContext(), photo);
//            Uri imageUri = data.getData();
            startCropImageActivity( selectedImage);

        }
        else   if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();
                uploadImage(uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    @Override

    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }
    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                Bitmap originalBmp= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver() , uri);
                mMainImage.setImageBitmap(originalBmp);
                splitImage(originalBmp,30);
            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void splitImageSingle(Bitmap image,int position) {
        int rows,cols;
        int chunkHeight,chunkWidth;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, image.getWidth(), image.getHeight(), true);
        rows =  1;
        cols = 3;
        chunkHeight = scaledBitmap.getHeight()/rows;
        chunkWidth = scaledBitmap.getWidth()/cols;
        Bitmap newBitmap;
        newChunkImage = new ArrayList<>();
        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){
                if (y != 0) {
                    if (y == 1) {
                        int coor = 0;
                        newChunkImage.add(Bitmap.createBitmap(scaledBitmap, coor , yCoord, (chunkWidth * 2) + 50, chunkHeight));
                    } else {
                        newChunkImage.add(Bitmap.createBitmap(scaledBitmap, xCoord , yCoord, chunkWidth, chunkHeight));
                    }
                }
                xCoord = xCoord + chunkWidth;
            }
            yCoord += chunkHeight;
        }
        BitmapFactory.Options optionss = new BitmapFactory.Options();
        optionss.inScaled = false;
        callCloudVision(newChunkImage.get(1), position);

    }


    private void splitImage(Bitmap image, int chunkNumbers)
    {


        int rows,cols;
        int chunkHeight,chunkWidth;
        //To store all the small image chunks in bitmap format in this list
        chunkedImages = new ArrayList<Bitmap>(chunkNumbers);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, image.getWidth(), image.getHeight(), true);
        rows =  3;
        cols = 3;
        chunkHeight = scaledBitmap.getHeight()/rows;
        chunkWidth = scaledBitmap.getWidth()/cols;
        Bitmap[] bitmaps = new Bitmap[30];
        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                mainArrayss.add(new UserPojo.Result());
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        BitmapFactory.Options optionss = new BitmapFactory.Options();
        optionss.inScaled = false;

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(imageRecycle);
        adapters = new Adapters();
        adapters.setList(chunkedImages,mainArrayss);
        adapters.setInterface(new Adapters.OnItemReadInterface() {
            @Override
            public void selectedImage(int position, Bitmap bitmap) {
                splitImageSingle(bitmap,position);
            }

            @Override
            public void uploadData(UserPojo.Result result, int position) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("party_affiliation",result.getParty_affiliation());
                jsonObject.addProperty("canvass_type",result.getCanvass_type());
                jsonObject.addProperty("area_id",boothid);
                jsonObject.addProperty("id",result.getEpic_number());
                adapterPosoition = position;
                userPresenter.update(jsonObject);

            }
        });
        adapters.notifyDataSetChanged();
        imageRecycle.setAdapter(adapters);
    }
    public static Bitmap scaleBitmaps(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
    private Vision.Images.Annotate prepareAnnotationRequest(Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);
                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);
                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);
                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);
        Vision vision = builder.build();
        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
            com.google.api.services.vision.v1.model.Image base64EncodedImage
                    = new com.google.api.services.vision.v1.model.Image();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);
            ImageContext imageContext = new ImageContext();
            ArrayList<String> strings = new ArrayList<>();
            strings.add("ta");
            strings.add("en");
            imageContext.setLanguageHints(strings);
            annotateImageRequest.setImageContext(imageContext);
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("TEXT_DETECTION");
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});
            // Add the list of one thing to the request
            add(annotateImageRequest);

        }});
        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");
        return annotateRequest;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void updated() {
        adapters.removeItem(adapterPosoition);
    }

    @Override
    public void showDetails(UserPojo.Result[] results, int position,String epicIdNew) {
        UserPojo.Result result  = new UserPojo.Result();
        result.setFm_name_tamil(results[0].getFm_name_tamil());
        result.setFm_name_english(results[0].getFm_name_english());
        result.setGendre(results[0].getGendre());
        result.setAge(results[0].getAge());
        result.setEpicId(epicIdNew);
        result.setEpic_number(results[0].getEpic_number());
        result.setRelation_type(results[0].getRelation_type());
        result.setRelation_firstname_english(results[0].getRelation_firstname_english());
        result.setRelation_lastname_english(results[0].getRelation_firstname_english());
        adapters.setItem(result,position);

    }

    private  class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<ImagesUpload> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;
        int postion;

        LableDetectionTask(ImagesUpload activity, Vision.Images.Annotate annotate,int postion) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
            this.postion = postion;
        }
        @Override
        protected String doInBackground(Object... params)
        {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);
            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }
        protected void onPostExecute(String result) {
            ImagesUpload activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                userPresenter.getUser("IBU0520122",postion,getNames(result));
                Log.i("TAG","Readed Result "+ getNames(result));
            }
        }
    }

   String getNames(String result) {
        String[] lines = result.trim().split("\n");
        return  lines != null && lines.length > 1 ? lines[0] : result;
    }



    private void callCloudVision(final Bitmap bitmap,int position) {
        mImageDetails.setText(R.string.loading_message);
        // Do the real work in an async task, because we need to use the network anyway
        try {
            labelDetectionTask = new
                    LableDetectionTask(this, prepareAnnotationRequest(bitmap),position);
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }


    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;
        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }
    private static String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("I found these things:\n\n");
        String strings = null;
        Locale loc = new Locale("ta");
        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();
        if (labels != null) {
            for (int i=0;i<labels.size();i++)
            {
                strings = labels.get(0).getDescription();
            }
        }
        else
        {
            message.append("nothing");
        }
        return strings;
    }
}
