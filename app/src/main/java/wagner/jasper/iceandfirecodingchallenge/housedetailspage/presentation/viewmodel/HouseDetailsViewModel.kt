package wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.usecase.GetHouseDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class HouseDetailsViewModel @Inject constructor(
    private val getHouseDetailsUseCase: GetHouseDetailsUseCase,
) : ViewModel() {

    private val _hasError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasError = _hasError.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _houseDetails: MutableStateFlow<HouseDetails?> = MutableStateFlow(null)
    val houseDetails = _houseDetails.asStateFlow()

    fun loadHouseDetails(id: Int) = viewModelScope.launch {
        _isLoading.emit(true)
        val result = getHouseDetailsUseCase(id)
        handleResult(result)
    }

    private suspend fun handleResult(result: Either<Exception, HouseDetails>) {
        when (result) {
            is Either.Left -> _hasError.emit(true)
            is Either.Right -> {
                _hasError.emit(false)
                _houseDetails.emit(result.value)
            }
        }
        _isLoading.emit(false)
    }

    fun reload() {
    }
}
