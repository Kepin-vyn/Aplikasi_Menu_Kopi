package com.example.aplikasi_menu_kopi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.GridView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {

    private lateinit var adapter: CoffeeAdapter
    private lateinit var tvResultCount: TextView
    private lateinit var tvEmpty: TextView
    
    // Gunakan nullable agar tidak crash saat salah satu view tidak ada (Portrait vs Landscape)
    private var listView: ListView? = null
    private var gridView: GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvResultCount = findViewById(R.id.tvResultCount)
        tvEmpty = findViewById(R.id.tvEmpty)
        val etSearch = findViewById<EditText>(R.id.etSearch)

        // Inisialisasi adapter dengan data awal
        adapter = CoffeeAdapter(coffeeArray.toList())

        // Inisialisasi ListView (ada di Portrait)
        listView = findViewById(R.id.listViewCoffee)
        listView?.adapter = adapter

        // Inisialisasi GridView (ada di Landscape)
        gridView = findViewById(R.id.gridViewCoffee)
        gridView?.adapter = adapter

        // Update tampilan jumlah awal
        updateResultCount(coffeeArray.size)

        // Logika Pencarian (Linear Search)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                val results = if (query.isNotEmpty()) {
                    linearSearch(query)
                } else {
                    coffeeArray.toList()
                }
                updateUI(results)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Modul 6 - Implementasi Linear Search O(n)
    private fun linearSearch(query: String): List<CoffeeItem> {
        val results = mutableListOf<CoffeeItem>()
        val lowerQuery = query.lowercase().trim()

        for (item in coffeeArray) {
            if (item.name.lowercase().contains(lowerQuery) ||
                item.category.lowercase().contains(lowerQuery)) {
                results.add(item)
            }
        }
        return results
    }

    private fun updateUI(results: List<CoffeeItem>) {
        updateResultCount(results.size)
        
        if (results.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            listView?.visibility = View.GONE
            gridView?.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            // Kembalikan visibilitas sesuai orientasi
            listView?.visibility = View.VISIBLE
            gridView?.visibility = View.VISIBLE
            adapter.updateData(results)
        }
    }

    private fun updateResultCount(count: Int) {
        tvResultCount.text = "Menampilkan $count dari ${coffeeArray.size} menu"
    }

    inner class CoffeeAdapter(private var displayList: List<CoffeeItem>) : BaseAdapter() {
        override fun getCount(): Int = displayList.size
        override fun getItem(position: Int): Any = displayList[position]
        override fun getItemId(position: Int): Long = position.toLong()

        fun updateData(newList: List<CoffeeItem>) {
            displayList = newList
            notifyDataSetChanged()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: layoutInflater.inflate(R.layout.item_coffee, parent, false)
            val item = displayList[position]
            
            val nameTextView = view.findViewById<TextView>(R.id.textName)
            val categoryTextView = view.findViewById<TextView>(R.id.textCategory)

            nameTextView.text = item.name
            categoryTextView.text = item.category
            
            return view
        }
    }
}
