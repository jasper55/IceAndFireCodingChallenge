package wagner.jasper.iceandfirecodingchallenge.character.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter
import wagner.jasper.iceandfirecodingchallenge.character.domain.usecase.GetCharacterUseCase
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
) : ViewModel() {

    private val _hasError: MutableStateFlow<Map<String, Boolean>> = MutableStateFlow(mapOf())
    val hasError = _hasError.asStateFlow()

    private val _isLoading: MutableStateFlow<Map<String, Boolean>> = MutableStateFlow(mapOf())
    val isLoading = _isLoading.asStateFlow()
    private val _character: MutableStateFlow<GoTCharacter?> = MutableStateFlow(null)
    val character = _character.asStateFlow()
    private val _characters: MutableStateFlow<Map<String, GoTCharacter>> = MutableStateFlow(mapOf())
    var characters = _characters.asStateFlow()
    fun loadCharacter(url: String) = viewModelScope.launch {
        _isLoading.update(url, true)
        delay(2000)
        val result = getCharacterUseCase(url)
        handleResult(url, result)
    }

    private suspend fun handleResult(url: String, result: Either<Exception, GoTCharacter>) {
        when (result) {
            is Either.Left -> _hasError.update(url, true)
            is Either.Right -> {
                _character.emit(result.value)
                _hasError.update(url, false)
                updateLoadedCharacters(url, result.value)
            }
        }
        _isLoading.update(url, false)
    }

    private suspend fun updateLoadedCharacters(url: String, character: GoTCharacter) {
        if (_characters.value.containsKey(url) || url.isBlank()) {
            return
        } else {
            _characters.update(url, character)
        }
    }

    fun reload() {
    }
}

private suspend fun <T> MutableStateFlow<Map<String, T>>.update(
    url: String,
    newValue: T,
) {
    val oldState = value.toMutableMap()
    oldState[url] = newValue
    this.emit(oldState.toMap())
}
