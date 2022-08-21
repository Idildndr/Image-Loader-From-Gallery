package android.example.imageloader

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import java.io.IOException


class MainActivity : AppCompatActivity() {
    val REQUEST_CODE_READ_EXT_STRG = 2
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // val image = findViewById<ImageView>(R.id.image)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener { }

        button  .setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                showImagePickerGallery(this)


            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_READ_EXT_STRG)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_EXT_STRG) {
                if (data != null) {
                    try {
                        val image = findViewById<ImageView>(R.id.image)
                        val selectedImgUri = data.data!!
                        loadUserPicture(selectedImgUri, image)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this,
                            "image selection failed",
                            Toast.LENGTH_LONG).show()
                    }


                }
            }
        }
    }

    fun showImagePickerGallery(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        activity.startActivityForResult(galleryIntent, REQUEST_CODE_READ_EXT_STRG)
    }


    fun loadUserPicture(imageURI: Uri, imageView: ImageView) {
        try {
            Glide.with(this).load(imageURI).centerCrop().placeholder(R.drawable.image_place_holder)
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }



}