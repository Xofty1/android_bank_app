package com.xofty.bankapp.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xofty.bankapp.datasource.retrofit.NetworkService
import com.xofty.bankapp.datasource.retrofit.model.CardInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _currentCard = MutableStateFlow<CardInfo?>(null)
    val currentCard: StateFlow<CardInfo?> = _currentCard

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val networkService = NetworkService()

    fun getCard(bin: Int) {
        viewModelScope.launch {
            val result = networkService.getCardInfo(bin)
            result.onSuccess { cardInfo ->
                _currentCard.value = cardInfo
                _errorMessage.value = null // Очищаем сообщение об ошибке при успешном запросе
            }.onFailure { error ->
                _currentCard.value = null // Очищаем данные карты при ошибке
                _errorMessage.value = error.message ?: "Неизвестная ошибка"
            }
        }
    }
}