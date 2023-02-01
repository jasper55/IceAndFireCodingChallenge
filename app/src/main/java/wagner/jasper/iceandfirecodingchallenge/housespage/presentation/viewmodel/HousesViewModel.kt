package wagner.jasper.iceandfirecodingchallenge.housespage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.getOrHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import wagner.jasper.iceandfirecodingchallenge.common.di.annotation.IO
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.usecase.GetHousesUseCase
import javax.inject.Inject

@HiltViewModel
class HousesViewModel @Inject constructor(
    private val getHousesUseCase: GetHousesUseCase,
    @IO private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val currentPage = 1
    val houses = MutableStateFlow<List<House>>(emptyList())

    init {
        viewModelScope.launch(ioDispatcher) {
            houses.value = getHousesUseCase(currentPage).getOrHandle {
                emptyList()
            }
        }
    }

    fun onHouseClicked(id: Int) {

    }
}