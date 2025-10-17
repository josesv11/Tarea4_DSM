package com.example.cityguide.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cityguide.data.Category
import com.example.cityguide.data.Place
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place

private object Routes {
    const val HOME = "home"
    const val CATEGORY = "category/{categoryId}"
    const val DETAIL = "detail/{categoryId}/{placeId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityGuideApp(
    windowSizeClass: WindowSizeClass,
    vm: CityViewModel = viewModel()
) {
    val nav = rememberNavController()
    val state by vm.state.collectAsState()

    val useRail = windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium

    Scaffold(
        topBar = { TopAppBar(title = { Text("City Guide") }) },
        bottomBar = {
            if (!useRail) {
                NavigationBar {
                    state.categories.take(4).forEach { c ->
                        NavigationBarItem(
                            selected = state.selectedCategory?.id == c.id,
                            onClick = {
                                vm.selectCategory(c.id)
                                nav.navigate("category/${c.id}")
                            },
                            icon = { Icon(imageVector = Icons.Outlined.Place, contentDescription = null) },
                            label = { Text(c.title) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        RowScaffold(
            showRail = useRail,
            railContent = {
                NavigationRail {
                    state.categories.forEach { c ->
                        NavigationRailItem(
                            selected = state.selectedCategory?.id == c.id,
                            onClick = {
                                vm.selectCategory(c.id)
                                nav.navigate("category/${c.id}")
                            },
                            icon = { Icon(imageVector = Icons.Outlined.Place, contentDescription = null) },
                            label = { Text(c.title) }
                        )
                    }
                }
            }
        ) {
            NavHost(
                navController = nav,
                startDestination = Routes.HOME,
                modifier = Modifier.padding(padding)
            ) {
                composable(Routes.HOME) {
                    HomeScreen(
                        categories = state.categories,
                        onOpen = { id ->
                            vm.selectCategory(id)
                            nav.navigate("category/$id")
                        }
                    )
                }

                composable(
                    route = Routes.CATEGORY,
                    arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
                ) { backStack ->
                    val categoryId = backStack.arguments?.getString("categoryId")!!
                    CategoryScreen(
                        category = state.categories.first { it.id == categoryId },
                        onOpenPlace = { placeId ->
                            vm.selectPlace(categoryId, placeId)
                            nav.navigate("detail/$categoryId/$placeId")
                        }
                    )
                }

                composable(
                    route = Routes.DETAIL,
                    arguments = listOf(
                        navArgument("categoryId") { type = NavType.StringType },
                        navArgument("placeId") { type = NavType.StringType }
                    )
                ) { args ->
                    val catId = args.arguments?.getString("categoryId")!!
                    val placeId = args.arguments?.getString("placeId")!!
                    val place = state.categories.first { it.id == catId }.places.first { it.id == placeId }
                    DetailScreen(place = place, onBack = { nav.popBackStack() })
                }
            }
        }
    }
}

/* ---------- Layout adaptable ---------- */

@Composable
private fun RowScaffold(
    showRail: Boolean,
    railContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    if (showRail) {
        Row(Modifier.fillMaxSize()) {
            railContent()
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Box(Modifier.weight(1f)) { content() }
        }
    } else {
        content()
    }
}

/* ---------------- Pantallas ---------------- */

@Composable
fun HomeScreen(categories: List<Category>, onOpen: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { cat ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpen(cat.id) }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(cat.title, style = MaterialTheme.typography.titleLarge)
                    Text("${cat.places.size} recomendaciones", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun CategoryScreen(category: Category, onOpenPlace: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                category.title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(category.places) { place ->
            ListItem(
                headlineContent = { Text(place.name) },
                supportingContent = { Text(place.description) },
                trailingContent = { Text("Ver") },
                modifier = Modifier.clickable { onOpenPlace(place.id) }
            )
            Divider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(place: Place, onBack: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(place.name, style = MaterialTheme.typography.headlineMedium)
        Text(place.address, style = MaterialTheme.typography.bodyMedium)
        ElevatedCard(Modifier.padding(top = 12.dp)) {
            Text(
                text = place.description,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Button(onClick = onBack, modifier = Modifier.padding(top = 20.dp)) {
            Text("Volver")
        }
    }
}
