package com.example.aplikasimanajemenkeuangankel3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ProfileFragment : Fragment() {

    // Variabel untuk menyimpan username yang diterima
    private var username: String? = null

    companion object {
        // Key yang digunakan untuk Bundle Arguments
        const val ARG_USERNAME = "arg_username"

        // Fungsi untuk membuat instance Fragment baru dan MENGIRIM data (username)
        // Ini dipanggil dari MainActivity setelah LoginActivity selesai.
        @JvmStatic
        fun newInstance(username: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menerima data username dari arguments yang dikirim saat newInstance dipanggil
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Memastikan username tampil di layout
        displayUsername(view)

        // Menyiapkan aksi untuk tombol-tombol
        setupLogoutButton(view)
        setupMenuButtons(view)
    }

    // --- LOGIKA UTAMA: MENAMPILKAN USERNAME
    /**
     * Menampilkan username pada TextView (ID: tv_user_name) di Profile Fragment.
     */
    private fun displayUsername(view: View) {
        // Mengambil referensi TextView dari layout
        val tvUserName = view.findViewById<TextView>(R.id.tv_user_name)
        val userToDisplay = username

        // Jika username ada (sudah diterima melalui arguments), tampilkan.
        if (!userToDisplay.isNullOrEmpty()) {
            tvUserName.text = userToDisplay
        } else {
            // Jika tidak ada (misalnya ada masalah), tampilkan default.
            tvUserName.text = "Username"
        }
    }

    // --- LOGIKA LOGOUT ---
    /**
     * Menyiapkan aksi untuk tombol Logout (ID: btn_logout).
     */
    private fun setupLogoutButton(view: View) {
        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()

            // Kembali ke LoginActivity
            val intent = Intent(activity, LoginActivity::class.java)
            // Menghapus stack activity sebelumnya (penting untuk logout)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    // --- LOGIKA POP-UP MENU ---
    /**
     * Fungsi umum untuk menampilkan AlertDialog/Pop-up.
     * @param title Judul pop-up.
     * @param message Isi pesan pop-up.
     * @param isHelpAction True jika aksi ini harus menyertakan tombol Telepon.
     */
    private fun showInfoPopup(title: String, message: String, isHelpAction: Boolean = false) {
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
            builder.setMessage(message)

            // Tombol default untuk menutup pop-up
            builder.setPositiveButton("TUTUP", null)

            // Tambahkan tombol TELEPON hanya jika ini adalah aksi Bantuan
            if (isHelpAction) {
                builder.setNegativeButton("TELEPON SEKARANG") { dialog, _ ->
                    // Membuka Dialer dengan nomor kontak
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:+6282233370042") // Nomor kontak Anda
                    startActivity(intent)
                }
            }

            builder.show()
        }
    }

    /**
     * Menyiapkan aksi klik untuk semua TextView menu (btn_help, btn_about, etc.).
     */
    private fun setupMenuButtons(view: View) {
        val btnHelp = view.findViewById<TextView>(R.id.btn_help)
        val btnAbout = view.findViewById<TextView>(R.id.btn_about)
        val btnNotification = view.findViewById<TextView>(R.id.btn_notification)
        val btnVersion = view.findViewById<TextView>(R.id.btn_version)

        // 1. HELP (Pop-up dengan Opsi Telepon)
        btnHelp.setOnClickListener {
            val title = "Help & Contack"
            val message = "Kami siap membantu Anda! Hubungi tim dukungan kami di:\n\nCustomer Service: \ncukurukuk@email.com \n+62-xxx-xxxx-xxxx"
            showInfoPopup(title, message, isHelpAction = true)
        }

        // 2. ABOUT (Pop-up Informasi Aplikasi)
        btnAbout.setOnClickListener {
            val title = "About Aplication"
            val message = "Aplikasi Manajemen Keuangan Kelompok 3. Dirancang untuk membantu Anda melacak pendapatan dan pengeluaran secara efisien. \n\nBuild by: Arif Syafarian, Alfeus Javaneo, Muhammad Ulil"
            showInfoPopup(title, message)
        }

        // 3. NOTIFICATION (Pop-up Pengaturan)
        btnNotification.setOnClickListener {
            val title = "Notifikation Setting"
            val message = "Anda dapat mengaktifkan atau menonaktifkan pengingat aplikasi di Pengaturan perangkat anda."
            showInfoPopup(title, message)
        }

        // 4. VERSION (Pop-up Versi)
        btnVersion.setOnClickListener {
            val title = "Version"
            val message = "Saat ini Anda menggunakan versi: 1.0.0 .\n\nPastikan anda menndapatkan versi terbaru di Play Store untuk Fitur dan update terbaru dari kami. "
            showInfoPopup(title, message)
        }
    }
}