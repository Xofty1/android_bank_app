package com.xofty.bankapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.xofty.bankapp.datasource.retrofit.model.CardInfoDto
import com.xofty.bankapp.domain.model.CardInfo
import com.xofty.bankapp.domain.viewModel.MainActivityViewModel
import com.xofty.bankapp.ui.theme.BankAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankAppTheme {
                MainScreen(viewModel) { cardInfo, errorMessage ->
                    if (cardInfo?.number != null) {

                        startActivity(Intent(this, CardDetailActivity::class.java).apply {
                            putExtra("CARD_INFO", cardInfo)
                        })
                    } else if (errorMessage != null) {
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Карта не найдена", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainActivityViewModel, onCardResult: (CardInfo?, String?) -> Unit) {
    var bin by remember { mutableStateOf("") }
    var isRequest by remember { mutableStateOf(false) }
    val count = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, -1, 0)

    val currentCard by viewModel.currentCard.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(currentCard, errorMessage) {
        if (isRequest) {
            onCardResult(currentCard, errorMessage)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = bin,
                onValueChange = { bin = it },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .focusable(false),
                readOnly = true
            )
            Button(
                onClick = {
                    isRequest = true
                    viewModel.getCard(bin.toIntOrNull() ?: 0)
                },
                modifier = Modifier
                    .weight(0.25f)
                    .height(56.dp),
                enabled = (bin.length in 6..8) && bin.toIntOrNull() != null,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "→")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка перехода на экран истории
        Button(
            onClick = {
                context.startActivity(Intent(context, HistoryActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp)
        ) {
            Text(text = "История запросов")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally)
        ) {
            items(count) { number ->
                if (number == -1)
                    DeleteCard {
                        if (bin.isNotEmpty()) bin = bin.dropLast(1)
                    }
                else
                    NumberCard(number) {
                        bin += number.toString()
                    }
            }
        }
    }
}

@Composable
fun DeleteCard(onClick: () -> Unit) {
    Card(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .size(80.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.errorContainer)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "⌫",
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
fun NumberCard(num: Int, onClick: (Int) -> Unit) {
    Card(
        onClick = {
            onClick(num)
        },
        modifier = Modifier
            .size(80.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),

            ) {
            Text(
                text = num.toString()
            )
        }
    }
}
