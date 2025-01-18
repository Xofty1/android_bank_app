package com.xofty.bankapp.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xofty.bankapp.datasource.retrofit.model.CardInfoDto
import com.xofty.bankapp.domain.model.CardInfo
import com.xofty.bankapp.ui.theme.BankAppTheme

class CardDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cardInfo = intent.getParcelableExtra<CardInfo>("CARD_INFO")

        setContent {
            BankAppTheme {
                CardDetailScreen(cardInfo)
            }
        }
    }
}

@Composable
fun CardDetailScreen(cardInfo: CardInfo?) {
    val context = LocalContext.current

    if (cardInfo == null) {
        Text(
            text = "Данные карты отсутствуют",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Информация о карте:",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Номер карты: ${cardInfo.number?.length} цифр")
                    Text(text = "Тип карты: ${cardInfo.type}")
                    Text(text = "Бренд: ${cardInfo.brand}")
                    Text(text = "Предоплаченная: ${if (cardInfo.prepaid == true) "Да" else "Нет"}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Страна: ${cardInfo.country?.name}")
                    Text(text = "Валюта: ${cardInfo.country?.currency}")
                    Button(
                        onClick = {
                            val gmmIntentUri = Uri.parse("geo:${cardInfo.country?.latitude},${cardInfo.country?.longitude}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            context.startActivity(mapIntent)
                        }
                    ) {
                        Text(text = "Открыть в картах")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Банк: ${cardInfo.bank?.name}")
                    Text(text = "Город: ${cardInfo.bank?.city}")
                    Button(
                        onClick = {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(cardInfo.bank?.url))
                            context.startActivity(browserIntent)
                        }
                    ) {
                        Text(text = "Открыть сайт банка")
                    }
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${cardInfo.bank?.phone}"))
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = "Позвонить в банк")
                    }
                }
            }
        }
    }
}