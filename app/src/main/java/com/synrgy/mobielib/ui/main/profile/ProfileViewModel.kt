package com.synrgy.mobielib.ui.main.profile

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.synrgy.common.IMAGE_MANIPULATION_WORK_NAME
import com.synrgy.common.KEY_IMAGE_URI
import com.synrgy.common.Resource
import com.synrgy.common.TAG_OUTPUT
import com.synrgy.domain.model.User
import com.synrgy.domain.usecase.user.ClearSessionUseCase
import com.synrgy.domain.usecase.user.GetProfileUseCase
import com.synrgy.domain.usecase.user.UpdateProfileImageUseCase
import com.synrgy.domain.usecase.user.UpdateProfileDataUseCase
import com.synrgy.mobielib.utils.workers.BlurWorker
import com.synrgy.mobielib.utils.workers.CleanUpWorker
import com.synrgy.mobielib.utils.workers.SaveImageToFileWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val updateProfileDataUseCase: UpdateProfileDataUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
) : ViewModel() {

    private val workManager = WorkManager.getInstance(Application())

    private val _user = MediatorLiveData<User>()
    val user: LiveData<User> = _user

    private val _updateDataResponse = MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val updateDataResponse = _updateDataResponse.asStateFlow()

    fun getUser(email: String, password: String) = viewModelScope.launch {
        val source = getProfileUseCase(email, password)
        _user.addSource(source) { result ->
            _user.value = result
            _user.removeSource(source)
            Log.d("AuthViewModel", "getUser: $result")
        }
    }

    fun clearSession() = viewModelScope.launch {
        clearSessionUseCase.invoke()
    }

    fun updateUserData(
        name: String, phone: String, dob: String, address: String, email: String
    ) = viewModelScope.launch {
        updateProfileDataUseCase.invoke(
            name = name,
            phone = phone,
            dob = dob,
            address = address,
            email = email
        ).collect{
            when(it){
                is Resource.Success -> {
                    _updateDataResponse.value = Resource.Success(Unit)
                }
                is Resource.Error -> {
                    _updateDataResponse.value = Resource.Error(it.exception)
                }
                is Resource.Loading -> {
                    _updateDataResponse.value = Resource.Loading
                }
            }
        }
    }

    fun updateProfileImage(uri: String, email:String) = viewModelScope.launch {
        updateProfileImageUseCase.invoke(uri = uri, userEmail = email )
    }

//    internal fun setOutputUri(outputImageUri: String?) {
//        val uri = uriOrNull(outputImageUri)
//        if (uri != null) {
//            outputUri.value = uri
//        }
//    }

    internal fun applyBlur(imageUri: Uri) {
        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanUpWorker::class.java)
            )

        val blurWorkRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(
                workDataOf(KEY_IMAGE_URI to imageUri.toString()))
        continuation = continuation.then(blurWorkRequest.build())

        val saveImageOutput = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .addTag(TAG_OUTPUT)
        continuation = continuation.then(saveImageOutput.build())

        continuation.enqueue()
    }
    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

}

