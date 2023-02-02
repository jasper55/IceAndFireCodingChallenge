package wagner.jasper.iceandfirecodingchallenge.housespage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.usecase.GetHousesUseCase
import javax.inject.Inject

@HiltViewModel
class HousesViewModel @Inject constructor(
    private val getHousesUseCase: GetHousesUseCase,
) : ViewModel() {

    val houses: Flow<PagingData<House>> = getHousesUseCase().cachedIn(viewModelScope)
    val hasError = MutableStateFlow<Boolean>(false)

    fun onHouseClicked(id: Int) {

    }
}