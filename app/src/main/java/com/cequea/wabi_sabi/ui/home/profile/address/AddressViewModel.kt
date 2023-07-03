package com.cequea.wabi_sabi.ui.home.profile.address

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.data.repository.AddressRepository
import com.cequea.wabi_sabi.ui.model.User
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.ui.model.Address
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repository: AddressRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _registerAddressSuccessfully = MutableLiveData<Boolean>()
    val registerAddressSuccessfully: LiveData<Boolean>
        get() = _registerAddressSuccessfully

    private val _loadError = MutableStateFlow("")
    val loadError = _loadError.asStateFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun addAddress(address: Address){
        viewModelScope.launch {
            _isLoading.value = true
            dataStoreRepository.getUser().collect { user ->
                when (val response = repository.addAddress(address, user.id)) {
                    is Resource.Success -> {
                        _loadError.emit("Direccion Registrada Satisfactoriamente")
                        _registerAddressSuccessfully.value = true
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