package com.mycookbook.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CookbookApp()
        }
    }
}

@Composable
fun CookbookApp() {
    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("cookbook_settings", 0)
    }

    var premium by remember {
        mutableStateOf(prefs.getBoolean("premium", true))
    }

    val background = if (premium)
        Color(0xFF171717) else Color(0xFFFFF8ED)

    val card = if (premium)
        Color(0xFF292929) else Color(0xFFFFFFFF)

    val textColor = if (premium)
        Color(0xFFFFF5E5) else Color(0xFF493527)

    val accent = if (premium)
        Color(0xFFD6AD60) else Color(0xFF98633E)

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = background
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(18.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                item {
                    Text(
                        text = "МОЯ КУЛИНАРИЯ",
                        color = accent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Готовим с удовольствием ❤️",
                        color = textColor,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = card
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "Оформление приложения",
                                color = textColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        premium = true
                                        prefs.edit()
                                            .putBoolean("premium", true)
                                            .apply()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (premium)
                                            accent else background
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("👑 Премиум")
                                }

                                Button(
                                    onClick = {
                                        premium = false
                                        prefs.edit()
                                            .putBoolean("premium", false)
                                            .apply()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (!premium)
                                            accent else background
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("🏡 Домашний")
                                }
                            }

                            Text(
                                text = if (premium)
                                    "Выбрана премиальная тема"
                                else
                                    "Выбрана домашняя тема",
                                color = textColor,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                item {
                    Text(
                        "Моя кулинарная книга",
                        color = textColor,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MenuCard(
                            "📖 Мои рецепты",
                            "Все сохранённые блюда",
                            card, textColor, accent
                        )

                        MenuCard(
                            "📝 Мой блокнот",
                            "Заметки, секреты и записи",
                            card, textColor, accent
                        )

                        MenuCard(
                            "📷 Фото блюд",
                            "Мои кулинарные фотографии",
                            card, textColor, accent
                        )

                        MenuCard(
                            "🎬 Видео приготовления",
                            "Собственные видеоуроки",
                            card, textColor, accent
                        )

                        MenuCard(
                            "❤️ Избранное",
                            "Любимые рецепты",
                            card, textColor, accent
                        )

                        MenuCard(
                            "🤖 ИИ-помощник",
                            "Идеи и помощь в приготовлении",
                            card, textColor, accent
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "Место для твоих лучших рецептов ❤️",
                        color = accent,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MenuCard(
    title: String,
    description: String,
    card: Color,
    textColor: Color,
    accent: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = card
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    title,
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    description,
                    color = accent,
                    fontSize = 13.sp
                )
            }

            Text(
                "›",
                color = accent,
                fontSize = 30.sp
            )
        }
    }
}
