package com.xofty.bankapp.domain.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xofty.bankapp.datasource.retrofit.NetworkService
import com.xofty.bankapp.datasource.retrofit.model.CardNumberDto
import com.xofty.bankapp.datasource.room.DatabaseService
import com.xofty.bankapp.datasource.room.entity.BankEntity
import com.xofty.bankapp.domain.mapper.databaseDomain.toEntity
import com.xofty.bankapp.domain.mapper.dtoDomain.toDomain
import com.xofty.bankapp.domain.model.CardInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel@Inject constructor(
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) : ViewModel() {

    private val _currentCard = MutableStateFlow<CardInfo?>(null)
    val currentCard: StateFlow<CardInfo?> = _currentCard

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun getCard(bin: Int) {
        viewModelScope.launch {
            val result = networkService.getCardInfo(bin)
            result.onSuccess { cardInfo ->
                // Преобразуем DTO в доменные объекты
                val bankDomain = cardInfo.bank?.toDomain()
                val cardNumberDomain = cardInfo.number?.toDomain()
                val countryDomain = cardInfo.country?.toDomain()
                val cardInfoDomain = cardInfo.toDomain()

                Log.d("InsertInDB", "bankDomain = $bankDomain, cardNumberDomain = $cardNumberDomain, countryDomain = $countryDomain, cardInfoDomain = $cardInfoDomain")

                // Вставляем данные в транзакции
                try {
                    databaseService.cardInfoDao.insertCardInfoWithDependencies(
                        bank = bankDomain?.toEntity(),
                        cardNumber = cardNumberDomain?.toEntity(),
                        country = countryDomain?.toEntity(),
                        cardInfo = cardInfoDomain.toEntity(
                            cardNumberId = 0,
                            countryId = 0,
                            bankId = 0
                        ),
                        databaseService = databaseService
                    )

                    _currentCard.value = cardInfoDomain
                    _errorMessage.value = null
                } catch (e: Exception) {
                    _errorMessage.value = e.message ?: "Ошибка при вставке данных"
                }
            }.onFailure { error ->
                _currentCard.value = null
                _errorMessage.value = error.message ?: "Неизвестная ошибка"
            }
        }
    }
}