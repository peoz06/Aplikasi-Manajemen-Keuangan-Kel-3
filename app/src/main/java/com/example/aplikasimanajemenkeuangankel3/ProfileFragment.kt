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

    // Key untuk menerima username dari Activity/host
    private var username: String? = null

    companion object {
        // Key yang direkomendasikan untuk menerima data username
        const val ARG_USERNAME = "arg_username"
        // Key yang digunakan oleh LoginActivity untuk mengirim username
        // Nilai ini harus sama dengan yang didefinisikan di LoginActivity.kt
        private const val EXTRA_USERNAME_KEY = "extra_username"

        /**
         * Gunakan metode factory ini untuk membuat instance baru dari
         * fragment ini menggunakan parameter username yang diberikan.
         *
         * @param username Nama pengguna yang telah login.
         * @return A new instance of fragment ProfileFragment.
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
        // Mengambil username dari arguments (Bundle) jika ada
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Menampilkan Username (Logika ini akan mengambil username yang diinputkan saat login)
        val tvUserName = view.findViewById<TextView>(R.id.tv_user_name)

        // Mulai dengan username dari arguments
        var userToDisplay = username

        // Fallback: Jika username dari arguments kosong, coba ambil dari Intent Activity host
        // Ini adalah langkah kunci untuk mendapatkan username yang dikirim dari LoginActivity
        if (userToDisplay.isNullOrEmpty()) {
            userToDisplay = activity?.intent?.getStringExtra(EXTRA_USERNAME_KEY)
        }

        if (!userToDisplay.isNullOrEmpty()) {
            // Tampilkan username yang berhasil diambil (yaitu yang diinputkan pengguna)
            tvUserName.text = userToDisplay
        } else {
            // Fallback jika data tidak ditemukan sama sekali
            tvUserName.text = "USER"
        }


        // . Mengatur Aksi Tombol LOG OUT
        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            // TODO: Lakukan proses logout (bersihkan sesi, dll.)
            Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()

            // Contoh navigasi kembali ke LoginActivity setelah logout
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
}