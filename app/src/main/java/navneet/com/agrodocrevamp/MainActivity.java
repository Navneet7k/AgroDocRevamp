package navneet.com.agrodocrevamp;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import navneet.com.agrodocrevamp.camera_tf.CameraActivity;
import navneet.com.agrodocrevamp.camera_tf.ImageClassifier;

public class MainActivity extends AppCompatActivity implements onDeleteIssue,TimelineInterface {

    private IssueViewModel mWordViewModel;
    private RecyclerView issue_recycler,timeline_rec;
    private IssueListAdapter adapter;
    private LinearLayout add_layout,add_img;
    private RelativeLayout img_layout;
    private IssueModel issue_model;
    private ImageView issue_image,extra_image;
    private android.support.v7.app.ActionBar actionBar;
    private ImageButton run_bt,image_edit;

    private PostAdapter mTimeLineAdapter;
    private ArrayList<TimeLineModel> mDataList;

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "SelectImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWordViewModel = ViewModelProviders.of(this).get(IssueViewModel.class);

//        populateSample();

        handlePermission();
//        try {
//            new ImageClassifier(this).classifyFrame()
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        actionBar=getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setTitle("AgroDoc - Plant Doctor");
        }


        issue_image=(ImageView) findViewById(R.id.issue_image);
        extra_image=(ImageView) findViewById(R.id.extra_img);
        add_layout=(LinearLayout)findViewById(R.id.add_layout);
        add_img=(LinearLayout)findViewById(R.id.add_img);
        img_layout=(RelativeLayout)findViewById(R.id.img_layout);

        run_bt=(ImageButton) findViewById(R.id.run);
        image_edit=(ImageButton) findViewById(R.id.edit_img);

        issue_recycler=(RecyclerView)findViewById(R.id.issue_list);
        timeline_rec=(RecyclerView)findViewById(R.id.timeline_rec);
        timeline_rec.setLayoutManager(new LinearLayoutManager(this));
        timeline_rec.setHasFixedSize(true);
//        issue_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        issue_recycler.setLayoutManager(mLayoutManager);

        mTimeLineAdapter = new PostAdapter(mDataList, this,this);
        timeline_rec.setAdapter(mTimeLineAdapter);

        adapter = new IssueListAdapter(this, this);
        issue_recycler.setAdapter(adapter);


        mWordViewModel.getAllWords().observe(this, new Observer<List<IssueModel>>() {
            @Override
            public void onChanged(@Nullable final List<IssueModel> issueModels) {
                // Update the cached copy of the words in the adapter.
                adapter.setIssues(issueModels);
            }
        });

        if (issue_model!=null) {
            if (issue_model.getExtraImage()!=null) {
                run_bt.setVisibility(View.VISIBLE);
                image_edit.setVisibility(View.VISIBLE);
            }
        }

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (issue_model!=null) {
                    if (issue_model.getExtraImage() == null) {
                        openImageChooser();
                    }
                } else {
                    Toast.makeText(MainActivity.this,"Please Choose a test case first!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (issue_model!=null) {
                    openImageChooser();
                }
            }
        });

        run_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm=((BitmapDrawable)extra_image.getDrawable()).getBitmap();
                bm=getResizedBitmap(bm,224);
                try {
                    String sample=new ImageClassifier(MainActivity.this).classifyFrame(bm);
                    Toast.makeText(MainActivity.this,sample,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPosterDetails();
            }
        });

    }

    private void populateSample() {
        IssueModel issueModel = new IssueModel("sample string", "sample string", "sample string");
        mWordViewModel.insert(issueModel);
    }

    @Override
    public void onDeleteIssueClicked(IssueModel issueModel) {
        deleteConfirm(issueModel);
    }

    @Override
    public void onIssueSelected(int adapterPos,IssueModel issueModel) {
        img_layout.setVisibility(View.VISIBLE);
        timeline_rec.setVisibility(View.VISIBLE);
        issue_model=issueModel;
            if (issue_model!=null&&issue_model.getExtraImage()!=null) {
                run_bt.setVisibility(View.VISIBLE);
                image_edit.setVisibility(View.VISIBLE);
            } else {
                run_bt.setVisibility(View.GONE);
                image_edit.setVisibility(View.GONE);
            }
        issue_recycler.getLayoutManager().smoothScrollToPosition(issue_recycler,new RecyclerView.State(), adapterPos);
        mDataList=new ArrayList<>();

        if (actionBar!=null) {
            actionBar.setSubtitle(issueModel.getIssueDate());
        }

        if (issueModel.getImage()!=null) {
            byte[] byteArray = issueModel.getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            issue_image.setImageBitmap(bmp);
        } else {
            issue_image.setImageDrawable(null);
        }

        if (issueModel.getExtraImage()!=null) {
            byte[] byteArray = issueModel.getExtraImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            extra_image.setImageBitmap(bmp);
        } else {
            extra_image.setImageDrawable(getResources().getDrawable(R.drawable.plus));
        }

        String result_st=issueModel.getIssueResults()!=null?issueModel.getIssueResults():"";
        if (!TextUtils.isEmpty(result_st)) {
            String[] messageSplit=result_st.split("\n");
            String row1=messageSplit[1];
            String row2=messageSplit[2];
            String row3=messageSplit[3];
            mDataList.add(new TimeLineModel(row1, "2017-02-12 08:00", OrderStatus.ACTIVE,""));
            mDataList.add(new TimeLineModel(row2, "2017-02-11 21:00", OrderStatus.ACTIVE,""));
            mDataList.add(new TimeLineModel(row3, "2017-02-11 18:00", OrderStatus.ACTIVE,""));
        } else {
            mDataList.add(new TimeLineModel(issueModel.getIssueName(), "2017-02-12 08:00", OrderStatus.INACTIVE, ""));
            mDataList.add(new TimeLineModel(issueModel.getIssueDesc(), "2017-02-11 21:00", OrderStatus.INACTIVE, ""));
            mDataList.add(new TimeLineModel(issueModel.getIssueSymptoms(), "2017-02-11 18:00", OrderStatus.INACTIVE, ""));
        }
        mTimeLineAdapter.setRecords(mDataList);
        mTimeLineAdapter.notifyDataSetChanged();
    }

    public void loadPosterDetails() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View resetDialog = getLayoutInflater().inflate(R.layout.details_dialog, null);

        TextView title = (TextView) resetDialog.findViewById(R.id.title);
        Button ok = (Button) resetDialog.findViewById(R.id.ok_button);
//        Button cancel=(Button)resetDialog.findViewById(R.id.cancel_button);
        final EditText issue_name = (EditText) resetDialog.findViewById(R.id.issue_name);
        final EditText issue_desc = (EditText) resetDialog.findViewById(R.id.issue_desc);
        final TextView issue_symptoms = (TextView) resetDialog.findViewById(R.id.issue_symptoms);


        issue_name.setText("tomato early blight");
        issue_desc.setText("leaf round circles");
        issue_symptoms.setText("yellow leaf, round marks");

        mBuilder.setView(resetDialog);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

        img_layout.setVisibility(View.GONE);
        timeline_rec.setVisibility(View.GONE);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(issue_name.getText())) {
                    issue_name.setError("Issue name can't be empty!");
                } else {
                    Date currentTime = Calendar.getInstance().getTime();
                    IssueModel issueModel = new IssueModel(issue_name.getText().toString(), issue_desc.getText().toString(), issue_symptoms.getText().toString());
                    issueModel.setIssueDate(currentTime.toString());
                    mWordViewModel.insert(issueModel);
                    issue_recycler.smoothScrollToPosition(issue_recycler.getAdapter().getItemCount());

                    alertDialog.dismiss();
                }
            }
        });

    }

    public void deleteConfirm(IssueModel issueModel) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View resetDialog = getLayoutInflater().inflate(R.layout.delete_dialog, null);

        TextView title = (TextView) resetDialog.findViewById(R.id.title);
        Button ok = (Button) resetDialog.findViewById(R.id.ok_button);
        Button cancel=(Button)resetDialog.findViewById(R.id.cancel_button);


        mBuilder.setView(resetDialog);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWordViewModel.delete(issueModel);
                alertDialog.dismiss();
                img_layout.setVisibility(View.GONE);
                timeline_rec.setVisibility(View.GONE);
                issue_recycler.getLayoutManager().smoothScrollToPosition(issue_recycler,new RecyclerView.State(), issue_recycler.getAdapter().getItemCount());
//                onIssueSelected(issue_recycler.getAdapter().getItemCount(),issueModel);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    @Override
    public void onScanClicked() {
        Intent intent=new Intent(MainActivity.this,CameraActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            if (data!=null) {
                Date currentTime = Calendar.getInstance().getTime();
                byte[] byteArray = data.getByteArrayExtra("image");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                issue_image.setImageBitmap(bmp);
                String message = data.getStringExtra("MESSAGE");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                String[] messageSplit = message.split("\n");
                String row1 = messageSplit[1];
                String row2 = messageSplit[2];
                String row3 = messageSplit[3];
                issue_model.setIssueResults(message);
                issue_model.setIssueDate(currentTime.toString());
                issue_model.setImage(byteArray);
                mWordViewModel.update(issue_model);
//            issue_recycler.getLayoutManager().smoothScrollToPosition(issue_recycler,new RecyclerView.State(),issue_recycler.getAdapter().getItemCount() );
                mDataList = new ArrayList<>();
                mDataList.add(new TimeLineModel(row1, "2017-02-12 08:00", OrderStatus.ACTIVE, ""));
                mDataList.add(new TimeLineModel(row2, "2017-02-11 21:00", OrderStatus.ACTIVE, ""));
                mDataList.add(new TimeLineModel(row3, "2017-02-11 18:00", OrderStatus.ACTIVE, ""));
                mTimeLineAdapter.setRecords(mDataList);
                mTimeLineAdapter.notifyDataSetChanged();
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Get the url from data
                        final Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            String path = getPathFromURI(selectedImageUri);
                            Log.i(TAG, "Image Path : " + path);
                            // Set the image in ImageView
                            findViewById(R.id.extra_img).post(new Runnable() {
                                @Override
                                public void run() {
                                    extra_image.setImageURI(selectedImageUri);
                                    run_bt.setVisibility(View.VISIBLE);
                                    image_edit.setVisibility(View.VISIBLE);
                                    Bitmap bm=((BitmapDrawable)extra_image.getDrawable()).getBitmap();
                                    bm=getResizedBitmap(bm,224);
                                    try {
                                       String sample=new ImageClassifier(MainActivity.this).classifyFrame(bm);
                                       Toast.makeText(MainActivity.this,sample,Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    InputStream iStream = null;
                                    try {
                                        iStream = getContentResolver().openInputStream(selectedImageUri);
                                        byte[] inputData = getBytes(iStream);
                                        issue_model.setExtraImage(inputData);
                                        mWordViewModel.update(issue_model);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    }
                            });

                        }
                    }
                }).start();
            }}
    }

    private void handlePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    SELECT_PICTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECT_PICTURE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            //  Show your own message here
                        } else {
                            showSettingsAlert();
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    private void showSettingsAlert() {
        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openAppSettings(MainActivity.this);
                    }
                });
        alertDialog.show();
    }

    public static void openAppSettings(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
