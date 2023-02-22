package com.purabmodi.agrihelp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.purabmodi.agrihelp.data.models.Record
import com.purabmodi.agrihelp.data.repository.MarketPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketPlaceViewModel @Inject constructor(private val marketPlaceRepository: MarketPlaceRepository) :
    ViewModel() {

    private val _marketPlaceData = MutableLiveData<PagingData<Record>>()
    val marketPlaceData: LiveData<PagingData<Record>> get() = _marketPlaceData

    fun getMarketPlaceData(map:HashMap<String,String>) = viewModelScope.launch {
        marketPlaceRepository.getMarketPlaceData(map)
            .cachedIn(viewModelScope)
            .onEach {
                _marketPlaceData.value = it
            }
            .launchIn(viewModelScope)
    }


}