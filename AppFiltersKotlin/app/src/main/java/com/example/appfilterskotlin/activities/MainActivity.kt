package com.example.appfilterskotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.appfilterskotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1
        const val KEY_IMAGE_URL = "imageUri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()

    }

    private fun setListeners() {
        binding.buttonEditNewImage.setOnClickListener {
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ).also { pickerIntent ->
                pickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(pickerIntent, REQUEST_CODE_PICK_IMAGE)
            }
        }
        binding.buttonSaveImage.setOnClickListener {
            Intent(applicationContext, SavedImageActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            data?.data?.let { imageUri ->
                Intent(applicationContext, EditImageActivity::class.java).also { editImageIntent ->
                    editImageIntent.putExtra(KEY_IMAGE_URL, imageUri)
                    startActivity(editImageIntent)
                }
            }
        }
    }
}