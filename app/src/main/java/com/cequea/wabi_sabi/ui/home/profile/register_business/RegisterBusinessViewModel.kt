package com.cequea.wabi_sabi.ui.home.profile.register_business

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.data.repository.RegisterBusinessRepository
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.ui.model.RegisterBusiness
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterBusinessViewModel @Inject constructor(
    private val repository: RegisterBusinessRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _registerSuccessfully = MutableLiveData<Boolean>()
    val registerSuccessfully: LiveData<Boolean>
        get() = _registerSuccessfully

    private val _loadError = MutableStateFlow("")
    val loadError = _loadError.asStateFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun registerBusiness(business: RegisterBusiness){
        viewModelScope.launch {
            _isLoading.value = true
            dataStoreRepository.getUser().collect { user ->
                when (val response = repository.registerBusiness(business, user.id)) {
                    is Resource.Success -> {
                        _loadError.emit("Solicitud enviada Satisfactoriamente")
                        _registerSuccessfully.value = true
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