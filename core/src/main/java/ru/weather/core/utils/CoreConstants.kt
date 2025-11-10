package ru.weather.core.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object CoreConstants {
    val digital: Digital = Digital()
    val dimensions: Dimension = Dimension()
    val fontsize: FontSize = FontSize()
}

data class Digital(
    val i0: Int = 0,
    val i1: Int = 1,
    val i2: Int = 2,
    val i3: Int = 3,
    val i4: Int = 4,
    val i5: Int = 5,
)

data class Dimension(
    val d0: Dp = 0.dp,
    val d1: Dp = 1.dp,
    val d4: Dp = 4.dp,
    val d8: Dp = 8.dp,
    val d12: Dp = 12.dp,
    val d16: Dp = 16.dp,
    val d24: Dp = 24.dp,
    val d40: Dp = 40.dp,
    val d80: Dp = 80.dp,
    val d120: Dp = 150.dp,

)
data class FontSize(
    val f12: TextUnit = 12.sp,
    val f14: TextUnit = 14.sp,
)
