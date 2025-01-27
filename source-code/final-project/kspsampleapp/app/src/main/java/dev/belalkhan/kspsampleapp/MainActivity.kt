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

        val user = User(
            id = 1,
            name = "John Doe",
            active = true,
            book = Book(
                id = 101,
                name = "The Great Adventure",
                price = 19.99,
                authors = listOf("Alice Smith", "Bob Johnson")
            ),
            friends = listOf("Alice", "Bob", "Charlie"),
            attributes = mapOf(
                "height" to "6ft",
                "weight" to "180lbs",
                "eyeColor" to "blue"
            ),
            tags = setOf("adventurer", "reader", "traveler"),
            address = Address(
                street = "123 Elm Street",
                city = "Springfield",
                postalCode = 98765
            ),
            phoneNumbers = listOf(
                PhoneNumber(type = "home", number = "555-1234"),
                PhoneNumber(type = "mobile", number = "555-5678")
            )
        )

        println(user.toJson())


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
