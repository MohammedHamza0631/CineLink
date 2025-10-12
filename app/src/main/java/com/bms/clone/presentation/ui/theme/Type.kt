package com.bms.clone.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bms.clone.R

val PoppinsFamily =
        FontFamily(
                Font(R.font.poppins_light, FontWeight.Light),
                Font(R.font.poppins_regular, FontWeight.Normal),
                Font(R.font.poppins_medium, FontWeight.Medium),
                Font(R.font.poppins_semibold, FontWeight.SemiBold),
                Font(R.font.poppins_bold, FontWeight.Bold)
        )

val Typography =
        Typography(
                displayLarge =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp,
                                lineHeight = 40.sp
                        ),
                headlineLarge =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                lineHeight = 32.sp
                        ),
                titleLarge =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                lineHeight = 28.sp
                        ),
                bodyLarge =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                letterSpacing = 0.5.sp
                        ),
                bodyMedium =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                        ),
                labelMedium =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                letterSpacing = 0.5.sp
                        ),
                labelSmall =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 10.sp,
                                lineHeight = 14.sp,
                                letterSpacing = 0.5.sp
                        ),
                titleMedium =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                lineHeight = 24.sp
                        ),
                titleSmall =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                        ),
                bodySmall =
                        TextStyle(
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                        )
        )
