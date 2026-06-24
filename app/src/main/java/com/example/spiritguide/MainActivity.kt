package com.example.spiritguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.spiritguide.ui.theme.SpiritGuideTempTheme
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spiritguide.db.AppDatabase
import com.example.spiritguide.viewmodel.CocktailViewModel
import com.example.spiritguide.viewmodel.CocktailViewModelFactory
import com.google.firebase.auth.FirebaseAuth
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpiritGuideTempTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    val startDestination = if (auth.currentUser != null) {
        "home"
    } else {
        "login"
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateBackToLogin = { navController.popBackStack() }
            )
        }
        composable("home") {
            val context = LocalContext.current
            val database = androidx.compose.runtime.remember { AppDatabase.getDatabase(context) }
            val cocktailViewModel: CocktailViewModel = viewModel(
                factory = CocktailViewModelFactory(database.cocktailDao())
            )
            HomeScreen(
                viewModel = cocktailViewModel,
                onLogout = {
                    FirebaseAuth.getInstance().signOut()

                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                })
        }
    }
}
