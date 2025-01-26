package com.example.shopping.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopping.domain.models.CartDataModels
import com.example.shopping.presentation.navigation.Routes
import com.example.shopping.presentation.viewModels.ShoppingAppViewModel
import com.example.shopping.ui.theme.SweetPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachProductDetailsScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController,
    productID: String,
) {
    val getProductById = viewModel.getProductByIDState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    var selectedSize by remember { mutableStateOf("") }
    var quantity by remember { mutableIntStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getProductByID(productID)
    }

    Scaffold(
        Modifier.fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },

                        scrollBehavior = scrollBehavior

            )
        }
    ) { innerPadding ->
        when {
            getProductById.value.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            getProductById.value.errorMessage != null -> {
                Text(text = getProductById.value.errorMessage!!)
            }

            getProductById.value.userData != null -> {
                val product = getProductById.value.userData!!.copy(productId = productID)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(modifier = Modifier.height(300.dp)) {
                        AsyncImage(
                            model = product.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Rs ${product.price}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Text(
                            text = "Size",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("S", "M", "L", "XL").forEach { size ->
                                OutlinedButton(
                                    onClick = { selectedSize = size },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (selectedSize == size) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        contentColor = if (selectedSize == size) Color.White else MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(size)
                                }
                            }
                        }

                        Text(
                            text = "Quantity",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Text("-", style = MaterialTheme.typography.headlineSmall)
                            }
                            Text(quantity.toString(), style = MaterialTheme.typography.bodyLarge)
                            IconButton(onClick = { quantity++ }) {
                                Text("+", style = MaterialTheme.typography.headlineSmall)
                            }
                        }

                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        Text(text = product.description)


                        Button(
                            onClick = {

                                val cartDataModels = CartDataModels(
                                    name = product.name,
                                    image = product.image,
                                    price = product.price,
                                    quantity = quantity.toString(),
                                    size = selectedSize,
                                    productId = product.productId,
                                    description = product.description,
                                    category = product.category

                                )
                                viewModel.addToCart(cartDataModels = cartDataModels)
                                      },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(SweetPink)

                        ) {
                            Text("Add to Cart")
                        }
                        Button(
                            onClick = {
                                navController.navigate(Routes.CheckoutScreen(productID))
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(SweetPink)

                        ) {
                            Text("Buy Now")
                        }

                        OutlinedIconButton(
                            onClick = { isFavorite = !isFavorite
                                viewModel.addToFav(product)

                                      },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row {


                            Icon(
                                if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                              //  tint = if (isFavorite) Color.Red else Color.White
                            )
                            Text("Add to Wishlist")
                            }


                        }
                    }
                }
            }
        }
    }
}