package com.bms.clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bms.clone.presentation.ui.navigation.BMSNavigation
import com.bms.clone.presentation.ui.theme.BookMyShowCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { BookMyShowCloneTheme { BMSNavigation() } }
    }
}

@Preview(showBackground = true)
@Composable
fun BMSAppPreview() {
    BookMyShowCloneTheme { BMSNavigation() }
}
