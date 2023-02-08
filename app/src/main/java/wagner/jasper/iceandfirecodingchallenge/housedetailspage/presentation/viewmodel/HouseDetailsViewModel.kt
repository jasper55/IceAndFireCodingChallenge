package wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import kotlinx.coroutines.launch
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.usecase.GetHouseDetailsUseCase
import javax.inject.Inject

class HouseDetailsViewModel @Inject constructor(
    private val getHouseDetailsUseCase: GetHouseDetailsUseCase,
) : ViewModel() {
    val hasError = MutableLiveData(false)
    val isLoading = MutableLiveData(true)
    val houseDetails = MutableLiveData<HouseDetails?>(null)

    init {
        viewModelScope.launch {
            isLoading.value = true
            val result = getHouseDetailsUseCase(id = 1)
            handleResult(result)
        }
    }

    private fun handleResult(result: Either<Exception, HouseDetails>) {
        when (result) {
            is Either.Left -> hasError.value = true
            is Either.Right -> {
                hasError.value = false
                houseDetails.value = result.value
            }
        }
        isLoading.value = false
    }
}
