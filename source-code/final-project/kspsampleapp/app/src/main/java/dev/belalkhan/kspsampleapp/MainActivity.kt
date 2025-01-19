package dev.belalkhan.kspsampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.belalkhan.kspsampleapp.ui.signup.Signup
import dev.belalkhan.kspsampleapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val user = User(1, "Belal")
        val book = Book(1, "The Grand Design")
        val song = Song(1, "Bye Bye")

        println(user.toJson())
        println(book.toJson())
        println(song.toJson())

        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Signup(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel(),
                    )
                }
            }
        }
    }
}
