package com.cequea.wabi_sabi.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.ui.model.User
import com.cequea.wabi_sabi.data.repository.UserRepository
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _loadError = MutableStateFlow("")
    val loadError = _loadError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getUser(){
        viewModelScope.launch {
            _isLoading.value = true
            dataStoreRepository.getUser().collect { user ->
                when (val response = repository.getUserByEmail(user.email)) {
                    is Resource.Success -> {
                        _user.value = response.data!!
                    }

                    is Resource.Error -> {
                        _loadError.emit(response.message!!)
                    }

                    is Resource.Loading -> TODO()
                }
                _isLoading.value = false
            }
        }
    }

}