package com.appdate.marvelcomicsinfo.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color


val Red700 = Color(0xFFD32F2F)
val Red900 = Color(0xFF9A0007)
val BlueGray600 = Color(0xFF546E7A)
val BlueGray900 = Color(0xFF29434E)

val Colors.topAppBarContentColor: Color
    get() = if (isLight) Color.White else Color.LightGray

