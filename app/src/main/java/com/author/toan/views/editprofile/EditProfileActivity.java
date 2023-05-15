package com.author.toan.views.editprofile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.author.toan.R;
import com.author.toan.helper.RealPathUtil;
import com.author.toan.models.Avatar;
import com.author.toan.remote.SharedPrefManager;
import com.author.toan.routes.APIUserService;
import com.author.toan.routes.UserClient;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private Uri mUri;
    private ImageView ivAvatar;
    private TextView tvName;
    public static final int MY_REQUEST_CODE = 100;
    private APIUserService apiUserService;
    public static String[] storge_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        Log.e("TAG", "onActivityResult: " + uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            Glide.with(getApplicationContext())
                                    .load(bitmap)
                                    .circleCrop()
                                    .into(ivAvatar);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(intent.createChooser(intent, "Select Picture"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        ivAvatar = findViewById(R.id.ivAvatar);
        tvName = findViewById(R.id.tvName);

        tvName.setText(SharedPrefManager.getUser().getName());
        Glide.with(getApplicationContext())
                .load(SharedPrefManager.getInstance(getApplicationContext()).getImageUser())
                .circleCrop()
                .into(ivAvatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.dialog_upload_image);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;

                dialog.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.tvUploadImage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckPermission();
                        dialog.dismiss();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Dialog dialogSave = new Dialog(EditProfileActivity.this);
                                dialogSave.setContentView(R.layout.dialog_save_image);
                                dialogSave.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialogSave.getWindow().getAttributes().gravity = Gravity.BOTTOM;

                                dialogSave.findViewById(R.id.tvSaveImage).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (mUri != null) {
                                            UploadImage();
                                            dialogSave.dismiss();
                                        }
                                    }
                                });

                                dialogSave.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogSave.dismiss();
                                        Glide.with(getApplicationContext())
                                                .load(SharedPrefManager.getInstance(getApplicationContext()).getImageUser())
                                                .circleCrop()
                                                .into(ivAvatar);
                                    }
                                });

                                dialogSave.show();
                            }
                        }, 2000);
                    }
                });
                dialog.show();
            }
        });
    }

    public void UploadImage() {
        String token = SharedPrefManager.getUser().getToken();
        String IMAGE_PATH = RealPathUtil.getRealPath(this, mUri);
        File file = new File(IMAGE_PATH);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part partbodyavatar =
                MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        apiUserService = UserClient.getInstance();
        apiUserService.uploadImage(partbodyavatar, "Bearer " + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 201) {
                        JSONObject obj = new JSONObject(response.body().string());
                        JSONObject userJson = obj.getJSONObject("user");
                        JSONObject avatarJson = userJson.getJSONObject("avatar");

                        Avatar avatar = new Avatar(
                                avatarJson.getString("url"),
                                avatarJson.getString("public_id")
                        );

                        Glide.with(getApplicationContext())
                                .load(avatar.getUrl())
                                .circleCrop()
                                .into(ivAvatar);
                    } else {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        Log.e("Error", obj.getString("error"));
                    }
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }
}