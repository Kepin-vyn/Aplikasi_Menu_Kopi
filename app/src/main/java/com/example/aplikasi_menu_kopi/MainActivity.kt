package com.example.aplikasi_menu_kopi

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // MODUL 9 - Menggunakan Tag Logcat (Ganti dengan NIM Anda)
    private val TAG = "NIM_ANDA"

    private lateinit var adapter: CoffeeAdapter
    private var tvResultCount: TextView? = null
    private var tvEmpty: TextView? = null
    private var gridView: GridView? = null

    private var currentSortOrder = "NONE"
    private var currentList: MutableList<CoffeeItem> = coffeeArray.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_main)
            Log.d(TAG, "MainActivity: Memuat layout BrewList")

            // Inisialisasi View
            tvResultCount = findViewById(R.id.tvResultCount)
            tvEmpty = findViewById(R.id.tvEmpty)
            val etSearch = findViewById<EditText>(R.id.etSearch)
            val btnSortAZ = findViewById<Button>(R.id.btnSortAZ)
            val btnSortZA = findViewById<Button>(R.id.btnSortZA)
            gridView = findViewById(R.id.gridViewCoffee)

            // Setup Adapter
            adapter = CoffeeAdapter(currentList)
            gridView?.adapter = adapter

            updateResultCount(currentList.size)

            // Tombol Bubble Sort A-Z
            btnSortAZ?.setOnClickListener {
                currentSortOrder = "ASC"
                val sorted = bubbleSortAscending(currentList.toMutableList())
                updateUI(sorted)
                Log.d(TAG, "Daftar diurutkan A-Z")
            }

            // Tombol Bubble Sort Z-A
            btnSortZA?.setOnClickListener {
                currentSortOrder = "DESC"
                val sorted = bubbleSortDescending(currentList.toMutableList())
                updateUI(sorted)
                Log.d(TAG, "Daftar diurutkan Z-A")
            }

            // Linear Search Real-time
            etSearch?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val query = s.toString()
                    var results = if (query.isNotEmpty()) {
                        linearSearch(query).toMutableList()
                    } else {
                        coffeeArray.toMutableList()
                    }

                    // Terapkan sortir otomatis pada hasil pencarian
                    results = when (currentSortOrder) {
                        "ASC" -> bubbleSortAscending(results).toMutableList()
                        "DESC" -> bubbleSortDescending(results).toMutableList()
                        else -> results
                    }

                    updateUI(results)
                }
                override fun afterTextChanged(s: Editable?) {}
            })

        } catch (e: Exception) {
            Log.e(TAG, "Error di MainActivity: ${e.message}")
        }
    }

    private fun linearSearch(query: String): List<CoffeeItem> {
        val lowerQuery = query.lowercase().trim()
        return coffeeArray.filter {
            it.name.lowercase().contains(lowerQuery) || it.category.lowercase().contains(lowerQuery)
        }
    }

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
            tvEmpty?.visibility = View.VISIBLE
            gridView?.visibility = View.GONE
        } else {
            tvEmpty?.visibility = View.GONE
            gridView?.visibility = View.VISIBLE
            adapter.updateData(results)
        }
    }

    private fun updateResultCount(count: Int) {
        tvResultCount?.text = "Menampilkan $count dari ${coffeeArray.size} menu"
    }

    inner class CoffeeAdapter(private var displayList: List<CoffeeItem>) : BaseAdapter() {
        override fun getCount(): Int = displayList.size
        override fun getItem(position: Int) = displayList[position]
        override fun getItemId(position: Int) = position.toLong()

        fun updateData(newList: List<CoffeeItem>) {
            displayList = newList
            notifyDataSetChanged()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: layoutInflater.inflate(R.layout.item_coffee, parent, false)
            val item = displayList[position]

            val nameText = view.findViewById<TextView>(R.id.textName)
            val categoryText = view.findViewById<TextView>(R.id.textCategory)
            val iconImage = view.findViewById<ImageView>(R.id.imgCoffeeIcon)

            nameText.text = item.name
            categoryText.text = item.category

            // MODUL 9 - Catchy UI: Warna Ikon berdasarkan Kategori
            val colorCode = when (item.category) {
                "Espresso Based" -> "#3E2723" // Dark Brown
                "Milk Based" -> "#8D6E63"     // Light Brown
                "Manual Brew" -> "#BF360C"    // Deep Orange
                else -> "#1B5E20"              // Green for Local
            }
            iconImage.setColorFilter(Color.parseColor(colorCode))

            return view
        }
    }
}
