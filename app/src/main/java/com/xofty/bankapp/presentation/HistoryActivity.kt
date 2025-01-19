package com.xofty.bankapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.xofty.bankapp.R
import com.xofty.bankapp.domain.model.CardInfo
import com.xofty.bankapp.domain.viewModel.HistoryViewModel
import com.xofty.bankapp.ui.theme.BankAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : ComponentActivity() {

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankAppTheme {
                HistoryScreen(viewModel){ cardInfo ->
                    startActivity(Intent(this, CardDetailActivity::class.java).apply {
                        putExtra("CARD_INFO", cardInfo)
                    })
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(viewModel: HistoryViewModel, onCardResult: (CardInfo) -> Unit) {
    val history by viewModel.history.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "История запросов",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (history.isEmpty()) {
            Text(
                text = "История запросов пуста",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(history) { cardInfo ->
                    CardInfoItem(cardInfo){
                        onCardResult(cardInfo)
                    }
                }
            }
        }
    }
}

@Composable
fun CardInfoItem(cardInfo: CardInfo, onClick: (CardInfo) -> Unit) {
    Card(
        onClick = { onClick(cardInfo) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Схема: ${cardInfo.scheme ?: "Неизвестно"}")
            Text(text = "Тип: ${cardInfo.type ?: "Неизвестно"}")
            Text(text = "Бренд: ${cardInfo.brand ?: "Неизвестно"}")
            Text(text = "Предоплата: ${if (cardInfo.prepaid == true) "Да" else "Нет"}")
        }
    }
}