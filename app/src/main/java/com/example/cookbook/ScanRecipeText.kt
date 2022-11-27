package com.example.cookbook

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.example.cookbook.databinding.ActivityMainBinding
import com.example.cookbook.databinding.ActivityScanRecipeTextBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ScanRecipeText : AppCompatActivity() {

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

//    lateinit var binding: ActivityScanRecipeTextBinding
    private lateinit var captureButton: Button
    private lateinit var imageView: ImageView
    private lateinit var textData: TextView
    private val REQUEST_IMAGE_CAPTURE = 1
    private var imageBitmap: Bitmap? = null


    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_recipe_text)

        captureButton = findViewById(R.id.button_capture)
        imageView = findViewById(R.id.image_view)
        textData = findViewById(R.id.text_data)

        imageView.setOnClickListener {
            pickImageGallery()
        }

        captureButton.setOnClickListener {
            processImage()
        }

    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageView.setImageURI(data?.data)
        }

//        processImage()
    }

    private fun processImage() {

        textData.text = "1. Make the Homemade Italian Dressing.\n" +
                "\n" +
                "2. Chop romaine and lettuce. Thinly slice the shallot. Peel and chop cucumber. Slice\n" +
                "tomatoes and olives in half.\n" +
                "\n" +
                "3. Mix together all the ingredients for the salad, including the dressing, and toss to\n" +
                "\n" +
                "combine. If making in advance, refrigerate the components separately; bring the\n" +
                "dressing to room temperature before serving.\n"
        imageBitmap = BitmapFactory.decodeResource(resources, R.drawable.text_detection, BitmapFactory.Options().apply {
            inMutable = true
        })
        System.out.println("Text Detection");
        if (imageBitmap != null) {
            val image = imageBitmap?.let {
                InputImage.fromBitmap(it, 0)
            }


            image?.let {
                recognizer.process(it).addOnSuccessListener { visionText ->
                     textData.text = visionText.text
                }
                    .addOnFailureListener {
                        e ->
                    }
            }
        }

        else {
            Toast.makeText(this, "Please select photo", Toast.LENGTH_SHORT).show()
        }
    }

}