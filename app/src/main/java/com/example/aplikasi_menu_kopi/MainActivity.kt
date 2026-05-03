package com.example.aplikasi_menu_kopi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
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
    private var listView: ListView? = null
    private var gridView: GridView? = null
    
    // Modul 7 - State untuk menyimpan urutan sortir terakhir
    private var currentSortOrder = "NONE"
    private var currentList: MutableList<CoffeeItem> = coffeeArray.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvResultCount = findViewById(R.id.tvResultCount)
        tvEmpty = findViewById(R.id.tvEmpty)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        val btnSortAZ = findViewById<Button>(R.id.btnSortAZ)
        val btnSortZA = findViewById<Button>(R.id.btnSortZA)

        adapter = CoffeeAdapter(currentList)

        listView = findViewById(R.id.listViewCoffee)
        listView?.adapter = adapter

        gridView = findViewById(R.id.gridViewCoffee)
        gridView?.adapter = adapter

        updateResultCount(currentList.size)

        // Tombol Sortir A-Z
        btnSortAZ.setOnClickListener {
            currentSortOrder = "ASC"
            val sortedList = bubbleSortAscending(currentList.toMutableList())
            updateUI(sortedList)
        }

        // Tombol Sortir Z-A
        btnSortZA.setOnClickListener {
            currentSortOrder = "DESC"
            val sortedList = bubbleSortDescending(currentList.toMutableList())
            updateUI(sortedList)
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                var filteredResults = if (query.isNotEmpty()) {
                    linearSearch(query).toMutableList()
                } else {
                    coffeeArray.toMutableList()
                }

                // Modul 7 - Re-apply sort order secara otomatis saat hasil pencarian berubah
                filteredResults = when (currentSortOrder) {
                    "ASC" -> bubbleSortAscending(filteredResults).toMutableList()
                    "DESC" -> bubbleSortDescending(filteredResults).toMutableList()
                    else -> filteredResults
                }

                currentList = filteredResults
                updateUI(currentList)
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

    // Modul 7 - Bubble Sort A-Z berdasarkan nama kopi
    private fun bubbleSortAscending(list: MutableList<CoffeeItem>): List<CoffeeItem> {
        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j].name.lowercase() > list[j + 1].name.lowercase()) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        return list
    }

    // Modul 7 - Bubble Sort Z-A berdasarkan nama kopi
    private fun bubbleSortDescending(list: MutableList<CoffeeItem>): List<CoffeeItem> {
        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j].name.lowercase() < list[j + 1].name.lowercase()) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        return list
    }

    private fun updateUI(results: List<CoffeeItem>) {
        currentList = results.toMutableList()
        updateResultCount(results.size)
        
        if (results.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            listView?.visibility = View.GONE
            gridView?.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
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
            
            view.findViewById<TextView>(R.id.textName).text = item.name
            view.findViewById<TextView>(R.id.textCategory).text = item.category
            
            return view
        }
    }
}
