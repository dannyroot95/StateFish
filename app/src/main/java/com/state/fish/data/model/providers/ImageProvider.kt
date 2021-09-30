package com.state.fish.data.model.providers

import android.content.Context
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.state.fish.utils.CompressorBitmapImage
import java.io.File

class ImageProvider(ref: String) {

    private var mStorage: StorageReference = FirebaseStorage.getInstance().reference.child(ref)

    fun saveImage(context: Context?, image: File, idUser: String): UploadTask {
        val imageByte: ByteArray = CompressorBitmapImage().getImage(context, image.path, 500, 500)!!
        val storage = mStorage.child("$idUser.jpg")
        mStorage = storage
        return storage.putBytes(imageByte)
    }

    fun getStorage(): StorageReference {
        return mStorage
    }

}