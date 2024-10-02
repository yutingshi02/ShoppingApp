package com.example.shoppingapp
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


data class Product(val name: String, val price: String, val description: String)

val products = listOf(
    Product("Smudge Rabbit Jellycat", "$38", "Smudge Rabbit is such a sleepy sweetie - always sprawling out, lazing on the bed or rug! Scrumptiously soft in oatmeal fur, this lop-eared lazer just captures the heart. An incredibly lopsy and loving companion, Smudge is a great gift for any bouncing baby. Bonnets and bobtails - we like it! - Jellycat.com"),
    Product("Bashful Beige Bunny", "$28", "Bashful Beige Bunny is a popular fellow with scrummy-soft Jellycat fur and lovely long flopsy ears mean that with just one cuddle, you’ll never want to let go. Irresistibly cute and a perfect gift for boys or girls. Everyone treasures this little beige bunny. - Jellycat.com"),
    Product("Smudge Puppy Jellycat", "$35", "Snoozy, yawny Smudge Puppy loves to flump on the floor and nod right off. A supercute pup in warm grey and cream, Smudge has delightful flopsy ears, a waggy tail and soft, snowy paws. Divinely snuggly and full of love, this pup is the perfect ruffday present! - Jellycat")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Using default Material3 theme
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MultiPaneShoppingApp(products)
                }
            }
        }
    }
}

@Composable
fun MultiPaneShoppingApp(products: List<Product>) {
    val selectedProduct = remember { mutableStateOf<Product?>(null) }
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(modifier = Modifier.fillMaxSize()) {
            ProductList(
                products = products,
                onProductClick = { selectedProduct.value = it },
                modifier = Modifier.weight(1f)
            )
            ProductDetails(
                product = selectedProduct.value,
                onBackClick = { selectedProduct.value = null },
                modifier = Modifier.weight(1f)
            )
        }
    } else {
        if (selectedProduct.value == null) {
            ProductList(
                products = products,
                onProductClick = { selectedProduct.value = it }
            )
        } else {
            ProductDetails(product = selectedProduct.value, onBackClick = { selectedProduct.value = null })
        }
    }
}

@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(products) { product ->
            Text(
                text = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductClick(product) }
                    .padding(16.dp),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun ProductDetails(product: Product?, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (product != null) {
            Text(text = product.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Price: ${product.price}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodyLarge)
        } else {
            Text(
                text = "Select a product to view details.",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        MultiPaneShoppingApp(products)
    }
}
