package wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter
import wagner.jasper.iceandfirecodingchallenge.character.domain.usecase.GetCharacterUseCase
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.usecase.GetHouseDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class HouseDetailsViewModel @Inject constructor(
    private val getHouseDetailsUseCase: GetHouseDetailsUseCase,
    private val getCharacterUseCase: GetCharacterUseCase,
) : ViewModel() {

    private val _hasError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasError = _hasError.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _houseDetails: MutableStateFlow<HouseDetails?> = MutableStateFlow(null)
    val houseDetails = _houseDetails.asStateFlow()

    private val _members: MutableStateFlow<List<GoTCharacter>> = MutableStateFlow(emptyList())
    val members = _members.asStateFlow()

    private val _founder: MutableStateFlow<GoTCharacter?> = MutableStateFlow(null)
    val founder = _founder.asStateFlow()

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
                loadCharacterData(result.value.swornMembers)
                loadFounderName(result.value.founder)
            }
        }
        _isLoading.emit(false)
    }

    private suspend fun loadFounderName(characterUrl: String) {
        if (characterUrl.isNotBlank()) {
            val founder = (getCharacterUseCase(characterUrl) as? Either.Right)?.value ?: return
            _founder.update { founder }
        }
    }

    private suspend fun loadCharacterData(memberUrls: List<String>) {
        memberUrls.forEach { url ->
            val member = (getCharacterUseCase(url) as? Either.Right)?.value ?: return@forEach
            _members.update { list ->
                list.toMutableList().apply {
                    add(member)
                }
            }
        }
    }

    fun reload() {}
}
