package ru.weather.cities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.weather.cities.models.StateEventEffectModel
import ru.weather.core.base.ScreenRoute

internal val localStateEventEffectModel = compositionLocalOf<StateEventEffectModel> {
    error("No StateEventEffectModel class found!")
}

@Composable
internal fun CitiesScreen() {
    val viewModel = hiltViewModel<CitiesViewModel>()

    ScreenRoute(viewModel = viewModel) { state, onEvent ->
        val model = StateEventEffectModel(state = state, event = onEvent)

        CompositionLocalProvider(localStateEventEffectModel provides model) {
            CitiesContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesContent() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Города",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

//        Spacer(modifier = Modifier.height(24.dp))
//

//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Button(
//                onClick = {  },
//                modifier = Modifier.weight(1f)
//            ) {
//                Text("Search")
//            }
//
//            Button(
//                onClick = {  },
//                modifier = Modifier.weight(1f)
//            ) {
//                Text("Add City")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//

//        if (state.isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//        }
//

//        state.error?.let { error ->
//            Text(
//                text = error,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//        }
//

//        LazyColumn {
//            items(10) { city ->
//                CityListItem(
//                    city = City(),
//                    onCityClick = {  },
//                    onDeleteClick = {  }
//                )
//            }
//        }

            // Empty state
//        if (state.isLoading) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(32.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    Icons.Default.DateRange,
//                    contentDescription = "No cities",
//                    modifier = Modifier.size(64.dp),
//                    tint = Color.Gray
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    "No cities added yet",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = Color.Gray
//                )
//                Text(
//                    "Add your first city to see weather information",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color.Gray
//                )
//            }
//        }
        }
    }
}

@Composable
fun CityListItem(
    city: City,
    onCityClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        onClick = onCityClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(city.name, style = MaterialTheme.typography.bodyLarge)
                Text(city.country, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
            }
        }
    }
}

data class City(
    val name: String = "Москва",
    val country: String = "Россия",
)