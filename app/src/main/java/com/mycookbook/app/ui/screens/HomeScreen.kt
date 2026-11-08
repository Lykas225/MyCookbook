
package com.mycookbook.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    premium: Boolean,
    background: Color,
    card: Color,
    textColor: Color,
    accent: Color,
    onPremiumChange: (Boolean) -> Unit,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Text(
            "МОЯ КУЛИНАРИЯ",
            color = accent,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Готовим с удовольствием ❤️",
            color = textColor,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = card),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "Оформление приложения",
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onPremiumChange(true) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (premium) accent else background
                        )
                    ) {
                        Text("👑 Премиум")
                    }

                    Button(
                        onClick = { onPremiumChange(false) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!premium) accent else background
                        )
                    ) {
                        Text("🏡 Домашний")
                    }
                }

                Text(
                    if (premium) "Выбрана премиальная тема"
                    else "Выбрана домашняя тема",
                    color = textColor,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(Modifier.height(22.dp))

        Text(
            "Моя кулинарная книга",
            color = textColor,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        val menu = listOf(
            Triple("📖 Мои рецепты", "Все сохранённые блюда", "recipes"),
            Triple("📝 Мой блокнот", "Заметки, секреты и записи", "notebook"),
            Triple("📷 Фото блюд", "Мои кулинарные фотографии", "photos"),
            Triple("🎬 Видео приготовления", "Собственные видеоуроки", "videos"),
            Triple("❤️ Избранное", "Любимые рецепты", "favorites"),
            Triple("🤖 ИИ-помощник", "Идеи и помощь в приготовлении", "assistant")
        )

        menu.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 11.dp)
                    .clickable {
                        onNavigate(item.third)
                    },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = card)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(17.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            item.first,
                            color = textColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(5.dp))

                        Text(
                            item.second,
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

        Spacer(Modifier.height(8.dp))

        Text(
            "Место для твоих лучших рецептов ❤️",
            color = accent,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(12.dp))
    }
}
