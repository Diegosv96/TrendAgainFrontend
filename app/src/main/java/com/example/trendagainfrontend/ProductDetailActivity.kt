// ProductDetailActivity.kt
package com.example.trendagainfrontend

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class ProductDetailActivity : AppCompatActivity() {

    // Cambia esto por la URL base de tu servidor, por ejemplo "http://192.168.1.10:8080"
    private val BASE_URL = "http://10.0.2.2:8080/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // 1. Obtener vistas del layout
        val ivImagen: ImageView = findViewById(R.id.ivThumbnail)
        val tvNombre: TextView = findViewById(R.id.tvName)
        val tvPrecio: TextView = findViewById(R.id.tvPrice)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val btnComprar: Button = findViewById(R.id.btnComprar)
        val btnFavorito: Button = findViewById(R.id.btnFavorito)

        // 2. Leer datos que envió el Adapter
        val productId = intent.getIntExtra("product_id", -1)
        val productName = intent.getStringExtra("product_name") ?: ""
        val productPrice = intent.getDoubleExtra("product_price", 0.0)
        val productDescription = intent.getStringExtra("product_description") ?: ""
        val productImageUrl = intent.getStringExtra("product_image_url") ?: ""

        // 3. Mostrar datos en pantalla
        tvNombre.text = productName
        tvPrecio.text = String.format("$%.2f", productPrice)
        tvDescripcion.text = productDescription

        // 4. Cargar imagen con Glide
        Glide.with(this)
            .load(productImageUrl)
            .centerCrop()
            .into(ivImagen)

        // 5. Configurar botón Comprar
        btnComprar.setOnClickListener {
            if (productId != -1) {
                registrarCompra(productId)
            } else {
                Toast.makeText(this, "ID de producto inválido", Toast.LENGTH_SHORT).show()
            }
        }

        // 6. Configurar botón Favorito
        btnFavorito.setOnClickListener {
            if (productId != -1) {
                marcarComoFavorito(productId)
            } else {
                Toast.makeText(this, "ID de producto inválido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para realizar la petición HTTP POST que crea una transacción de compra
    private fun registrarCompra(productId: Int) {
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val jsonBody = """
            {
              "productId": $productId
            }
        """.trimIndent()

        val requestBody = RequestBody.create(mediaType, jsonBody)
        val request = Request.Builder()
            .url("$BASE_URL/transactions/add")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Error de red o servidor inaccesible
                runOnUiThread {
                    Toast.makeText(this@ProductDetailActivity,
                        "Error al registrar compra", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@ProductDetailActivity,
                            "Compra registrada con éxito", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@ProductDetailActivity,
                            "Fallo al registrar compra", Toast.LENGTH_SHORT).show()
                    }
                }
                response.close()
            }
        })
    }

    // Función para realizar la petición HTTP POST que marca el producto como favorito
    private fun marcarComoFavorito(productId: Int) {
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val jsonBody = """
            {
              "productId": $productId
            }
        """.trimIndent()

        val requestBody = RequestBody.create(mediaType, jsonBody)
        val request = Request.Builder()
            .url("$BASE_URL/favorites/add")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@ProductDetailActivity,
                        "Error al marcar favorito", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@ProductDetailActivity,
                            "Producto marcado como favorito", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@ProductDetailActivity,
                            "Fallo al marcar favorito", Toast.LENGTH_SHORT).show()
                    }
                }
                response.close()
            }
        })
    }
}
