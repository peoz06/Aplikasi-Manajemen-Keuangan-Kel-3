package com.example.aplikasimanajemenkeuangankel3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

// import androidx.core.content.ContextCompat // Tidak diperlukan lagi

/**
 * Fragment untuk menampilkan Analisis Keuangan.
 */
class SettingFragment : Fragment() {

    // Deklarasi Variabel untuk komponen UI
    private lateinit var btnInputData: Button
    private lateinit var chartPlaceholder: TextView // Placeholder untuk Grafik
    private lateinit var tvTotalIncome: TextView
    private lateinit var tvTotalExpense: TextView
    private lateinit var tvDifference: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        // Inisialisasi komponen UI
        btnInputData = view.findViewById(R.id.btnInputData)
        chartPlaceholder = view.findViewById(R.id.chartPlaceholder)
        tvTotalIncome = view.findViewById(R.id.tvTotalIncome)
        tvTotalExpense = view.findViewById(R.id.tvTotalExpense)
        tvDifference = view.findViewById(R.id.tvDifference)

        // Menetapkan Listener Klik untuk Tombol Input Data
        btnInputData.setOnClickListener {
            // Logika untuk menampilkan dialog atau membuka Activity/Fragment input data
            showInputDialog()
        }

        // Contoh memuat data awal (Anda bisa memanggil fungsi di sini)
        loadInitialData()

        return view
    }

    /**
     * Menampilkan dialog atau membuka layar baru untuk memasukkan Income dan Pengeluaran.
     */
    private fun showInputDialog() {
        // TODO: Ganti dengan implementasi DialogFragment atau navigasi Activity/Fragment
        chartPlaceholder.text = "Dialog Input Data ditampilkan..."

        // Setelah data dimasukkan dan "OK", panggil fungsi untuk memperbarui UI, contoh:
        // updateFinancialAnalysis(2500000, 1800000)
    }

    /**
     * Memuat data keuangan awal untuk ditampilkan di grafik dan ringkasan.
     */
    private fun loadInitialData() {
        // TODO: Implementasi nyata: Ambil data dari database/API untuk periode default.

        // Data dummy untuk demonstrasi:
        val income = 5500000L // Rp 5.500.000
        val expense = 3200000L // Rp 3.200.000

        updateFinancialAnalysis(income, expense)
        // loadChart(income, expense) // Panggil fungsi untuk me-render grafik
    }

    /**
     * Memperbarui TextView Ringkasan Keuangan.
     */
    private fun updateFinancialAnalysis(income: Long, expense: Long) {
        val difference = income - expense

        // Menggunakan format mata uang (Anda mungkin perlu fungsi format Rupiah yang lebih baik)
        val formatRupiah = { amount: Long -> "Rp ${String.format("%,d", amount).replace(",", ".")}" }

        tvTotalIncome.text = formatRupiah(income)
        tvTotalExpense.text = formatRupiah(expense)
        tvDifference.text = formatRupiah(difference)

        // Opsional: Perbarui warna Selisih
        val colorResource = if (difference >= 0) {
            android.R.color.holo_green_dark
        } else {
            android.R.color.holo_red_dark
        }
        tvDifference.setTextColor(context?.let { ContextCompat.getColor(it, colorResource) } ?: tvDifference.currentTextColor)
    }

    // TODO: Tambahkan fungsi 'loadChart' di sini untuk mengintegrasikan library grafik Anda (misalnya MPAndroidChart)
}