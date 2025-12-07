package com.example.aplikasimanajemenkeuangankel3

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ProfileFragment : Fragment() {

    private var username: String? = null

    companion object {
        const val ARG_USERNAME = "arg_username"
        private const val EXTRA_USERNAME_KEY = "extra_username" // Pastikan ini sesuai di LoginActivity

        /**
         * Metode factory yang disarankan untuk membuat instance Fragment
         * dengan melewatkan data melalui Arguments Bundle.
         */
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
        // Mengambil username dari arguments (jika menggunakan newInstance)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Panggil fungsi penyiapan
        displayUsername(view)
        setupLogoutButton(view)
        setupMenuButtons(view) // Menghubungkan tombol menu dengan ID unik
    }

    /**
     * Mengambil username dari Arguments atau Activity Intent dan menampilkannya.
     */
    private fun displayUsername(view: View) {
        val tvUserName = view.findViewById<TextView>(R.id.tv_user_name)

        var userToDisplay = username

        // Fallback: Coba ambil dari Activity Intent
        if (userToDisplay.isNullOrEmpty()) {
            userToDisplay = activity?.intent?.getStringExtra(EXTRA_USERNAME_KEY)
        }

        if (!userToDisplay.isNullOrEmpty()) {
            tvUserName.text = userToDisplay
        } else {
            tvUserName.text = "USER"
        }
    }

    /**
     * Menyiapkan aksi untuk tombol LOG OUT.
     */
    private fun setupLogoutButton(view: View) {
        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            // TODO: Bersihkan sesi login (Shared Preferences/Database) di sini
            Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()

            // Navigasi ke LoginActivity dan hapus back stack
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    /**
     * Menyiapkan aksi untuk tombol-tombol menu.
     */
    private fun setupMenuButtons(view: View) {
        // Menghubungkan dengan ID unik yang sudah diperbaiki di XML
        val btnHelp = view.findViewById<TextView>(R.id.btn_help)
        val btnAbout = view.findViewById<TextView>(R.id.btn_about)
        val btnNotification = view.findViewById<TextView>(R.id.btn_notification)
        val btnVersion = view.findViewById<TextView>(R.id.btn_version)

        btnHelp.setOnClickListener {
            Toast.makeText(context, "Membuka halaman Bantuan", Toast.LENGTH_SHORT).show()
        }

        btnAbout.setOnClickListener {
            Toast.makeText(context, "Membuka halaman Tentang Kami", Toast.LENGTH_SHORT).show()
        }

        btnNotification.setOnClickListener {
            Toast.makeText(context, "Membuka Pengaturan Notifikasi", Toast.LENGTH_SHORT).show()
        }

        btnVersion.setOnClickListener {
            Toast.makeText(context, "Versi Aplikasi: 1.0.0", Toast.LENGTH_SHORT).show()
        }
    }
}