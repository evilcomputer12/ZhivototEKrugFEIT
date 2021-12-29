package com.martin.proektnazadaca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.martin.proektnazadaca.PostaroLiceActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ImageManager {

    // Start Image Cropper
    public static Intent startImageCropper(Context context) {

        // Crop Image
        Intent intent = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Title")
                .setCropMenuCropButtonTitle("Save")
                .setAutoZoomEnabled(true)
                .setAspectRatio(1, 1)
                .getIntent(context);

        return intent;

    }


    public static Uri activityResult(int requestCode, int resultCode, Intent data, Context context) {

        // Handle Cropped Image

        Uri resultUri = null;

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                resultUri = result.getUri();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(context, (CharSequence) error, Toast.LENGTH_SHORT).show();
            }else if(resultCode == Activity.RESULT_CANCELED){
                Intent myIntent = new Intent(context, PostaroLiceActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myIntent);

            }

        }
        return resultUri;
    }
}