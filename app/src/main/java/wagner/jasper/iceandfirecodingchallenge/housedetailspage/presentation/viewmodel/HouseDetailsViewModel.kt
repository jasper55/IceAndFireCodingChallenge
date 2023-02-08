package wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.usecase.GetHouseDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class HouseDetailsViewModel @Inject constructor(
    private val getHouseDetailsUseCase: GetHouseDetailsUseCase,
) : ViewModel() {
    val hasError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val houseDetails: MutableStateFlow<HouseDetails?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            isLoading.emit(true)
            val result = getHouseDetailsUseCase(id = 1)
            handleResult(result)
        }
    }

    private suspend fun handleResult(result: Either<Exception, HouseDetails>) {
        when (result) {
            is Either.Left -> hasError.emit(true)
            is Either.Right -> {
                hasError.emit(false)
                houseDetails.emit(result.value)
            }
        }
        isLoading.emit(false)
    }

    fun reload() {
    }
}
