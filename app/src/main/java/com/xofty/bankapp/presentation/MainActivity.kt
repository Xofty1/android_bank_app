package com.xofty.bankapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xofty.bankapp.datasource.retrofit.NetworkService
import com.xofty.bankapp.domain.viewModel.MainActivityViewModel
import com.xofty.bankapp.ui.theme.BankAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest


class MainActivity : ComponentActivity() {
    val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.currentCard.collectLatest {
                Log.d("UIUpdate", this.toString())
            }
        }
        setContent {
            BankAppTheme {
                MainScreen(viewModel)
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    var bin by remember { mutableStateOf("") }
    val count = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
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
                    .weight(1f) // TextField займет 75% ширины
                    .height(56.dp) // Устанавливаем высоту для гармоничного вида
            )
            Button(
                onClick = { viewModel.getCard(bin.toIntOrNull() ?: 0) }, // Проверяем корректность значения
                modifier = Modifier
                    .weight(0.25f) // Кнопка займет 25% ширины
                    .height(56.dp), // Высота совпадает с TextField
                enabled = bin.isNotBlank() && bin.toIntOrNull() != null, // Кнопка активна только при валидном значении
                shape = MaterialTheme.shapes.small, // Стандартная форма кнопки
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "Fetch") // Короткий текст для кнопки
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.wrapContentSize()
                .align(Alignment.CenterHorizontally)

        ) {
            items(count) { number ->
                NumberCard(number) { bin += number }
            }
        }
        Spacer(modifier = Modifier.height(100.dp))


    }
}

@Composable
fun NumberCard(num: Int, onClick: (Int) -> Unit) {
    Card(
        onClick = {
            onClick(num)
        },
        modifier = Modifier
            .size(80.dp),
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BankAppTheme {
        Greeting("Android")
    }
}