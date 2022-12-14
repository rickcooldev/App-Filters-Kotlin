package com.example.appfilterskotlin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appfilterskotlin.repositories.SavedImageRepository
import com.example.appfilterskotlin.utilities.Coroutines
import java.io.File

class SavedImagesViewModel(private val savedImageRepository: SavedImageRepository): ViewModel() {

    private val savedImagesDataState = MutableLiveData<SavedImageDataState>()
    val savedImageUiState: LiveData<SavedImageDataState> get() = savedImagesDataState

    fun loadSavesImage() {
        Coroutines.io {
            runCatching {
                emitSavedImagesUiState(isLoading = true)
                savedImageRepository.loadSavedImages()
            }.onSuccess { savedImages ->
                if(savedImages.isNullOrEmpty()) {
                    emitSavedImagesUiState(error = "No image found")
                } else {
                    emitSavedImagesUiState(savedImages = savedImages)
                }
            }.onFailure {
                emitSavedImagesUiState(error = it.message.toString())
            }
        }
    }

    private fun emitSavedImagesUiState(
        isLoading: Boolean = false,
        savedImages: List<Pair<File, Bitmap>>? = null,
        error: String? = null
    ) {
        val dataState = SavedImageDataState(isLoading, savedImages, error)
        savedImagesDataState.postValue(dataState)
    }

    data class SavedImageDataState(
        val isLoading: Boolean,
        val savedImages: List<Pair<File, Bitmap>>?,
        val error: String?
    )
}