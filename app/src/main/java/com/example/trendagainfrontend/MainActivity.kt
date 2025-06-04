package com.example.trendagainfrontend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendagainfrontend.data.data.api.RetrofitClient
import com.example.trendagainfrontend.data.data.adapters.ProductAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvProducts) // Cambia el ID a rvProducts para más claridad
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadProducts() // Cambiado el nombre del método
    }

    private fun loadProducts() {
        lifecycleScope.launch {
            try {
                // Llamada a la API para obtener productos
                val response = RetrofitClient.apiService.getProducts() // <-- Cambia aquí si tu método es distinto

                if (response.isSuccessful) {
                    val products = response.body() ?: emptyList()
                    recyclerView.adapter = ProductAdapter(this@MainActivity, products)
                } else {
                    Toast.makeText(this@MainActivity, "Error al cargar los productos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
