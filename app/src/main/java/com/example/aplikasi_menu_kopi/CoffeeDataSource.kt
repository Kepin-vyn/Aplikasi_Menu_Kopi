package com.example.aplikasi_menu_kopi

// Modul 6 - Struktur Data Array (bukan database)
// Total data: 20 item menu kopi
val coffeeArray: Array<CoffeeItem> = arrayOf(

    // Espresso Based
    CoffeeItem("Espresso",   "Espresso Based"),
    CoffeeItem("Americano",  "Espresso Based"),
    CoffeeItem("Ristretto",  "Espresso Based"),
    CoffeeItem("Lungo",      "Espresso Based"),

    // Milk Based
    CoffeeItem("Cappuccino",    "Milk Based"),
    CoffeeItem("Latte",         "Milk Based"),
    CoffeeItem("Flat White",    "Milk Based"),
    CoffeeItem("Mocha",         "Milk Based"),
    CoffeeItem("Caramel Latte", "Milk Based"),
    CoffeeItem("Vanilla Latte", "Milk Based"),

    // Manual Brew
    CoffeeItem("V60",                  "Manual Brew"),
    CoffeeItem("French Press",         "Manual Brew"),
    CoffeeItem("Aeropress",            "Manual Brew"),
    CoffeeItem("Cold Brew",            "Manual Brew"),
    CoffeeItem("Japanese Iced Coffee", "Manual Brew"),

    // Kopi Lokal Indonesia
    CoffeeItem("Kopi Bali Kintamani", "Kopi Lokal Indonesia"),
    CoffeeItem("Kopi Toraja",         "Kopi Lokal Indonesia"),
    CoffeeItem("Kopi Gayo",           "Kopi Lokal Indonesia"),
    CoffeeItem("Kopi Mandailing",     "Kopi Lokal Indonesia"),
    CoffeeItem("Kopi Flores Bajawa",  "Kopi Lokal Indonesia")
)
