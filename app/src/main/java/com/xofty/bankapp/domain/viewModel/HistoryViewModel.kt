package com.xofty.bankapp.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xofty.bankapp.datasource.room.DatabaseService
import com.xofty.bankapp.domain.mapper.databaseDomain.toDomain
import com.xofty.bankapp.domain.model.CardInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val databaseService: DatabaseService
) : ViewModel() {

    private val _history = MutableStateFlow<List<CardInfo>>(emptyList())
    val history: StateFlow<List<CardInfo>> = _history

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _history.value = databaseService.getAllCardInfo().map { cardInfo -> cardInfo.toDomain(databaseService.cardNumberDao, databaseService.countryDao, databaseService.bankDao) }
        }
    }
}