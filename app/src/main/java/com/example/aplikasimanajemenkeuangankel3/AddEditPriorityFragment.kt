package com.example.aplikasimanajemenkeuangankel3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddEditPriorityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddEditPriorityFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_priority, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ambil reverensi ke semua view di form
        val nameEditText = view.findViewById<TextInputEditText>(R.id.name_edit_text)
        val priceEditText = view.findViewById<TextInputEditText>(R.id.price_edit_text)
        val descriptionEditText = view.findViewById<TextInputEditText>(R.id.description_edit_text)
        val saveButton = view.findViewById<Button>(R.id.save_button)
        val titleTextView = view.findViewById<TextView>(R.id.add_edit_title)

        val itemId = arguments?.getLong("itemToEditId")
        if (itemId != null) {
            titleTextView.text = "Edit Item Priority"
            nameEditText.setText(arguments?.getString("itemToEditName"))
            priceEditText.setText(arguments?.getDouble("itemToEditPrice").toString())
            descriptionEditText.setText(arguments?.getString("itemToEditDesc"))

            saveButton.setOnClickListener {
                //TODO: Logika untuk mengirim kembali data yang sudah di update
                val updatedName = nameEditText.text.toString()
                val updatedPrice = priceEditText.text.toString().toDoubleOrNull() ?: 0.0
                val updatedDescription = descriptionEditText.text.toString()

                if (updatedName.isBlank()) {
                    nameEditText.error = "Nama produk tidak boleh kosong"
                    return@setOnClickListener
                }
                setFragmentResult("requestKey", bundleOf(
                    "updatedItemId" to itemId,
                    "updatedItemName" to updatedName,
                    "updatedItemPrice" to updatedPrice,
                    "updatedItemDesc" to updatedDescription
                ))
                findNavController().popBackStack()
            }
        } else {
            titleTextView.text = "Tambah Item Priority"
            saveButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val price = priceEditText.text.toString().toDoubleOrNull() ?: 0.0
                val description = descriptionEditText.text.toString()

                if (name.isBlank()) {
                    nameEditText.error = "Nama produk tidak boleh kosong"
                    return@setOnClickListener
                }
                setFragmentResult("requestKey", bundleOf(
                    "newItemName" to name,
                    "newItemPrice" to price,
                    "newItemDesc" to description
                ))
                findNavController().popBackStack()
            }
        }
//        saveButton.setOnClickListener {
//            //ambil data dari setiap input field
//            val name = nameEditText.text.toString()
//            //konversi ke double lalu jika gagal nilai 0.0
//            val price = priceEditText.text.toString().toDoubleOrNull() ?: 0.0
//            val description = descriptionEditText.text.toString()
//
//            if (name.isBlank()) {
//                nameEditText.error = "Nama produk tidak boleh kosong"
//                return@setOnClickListener
//            }
//
//            //kirim kembali data ke PriorityFragment menggunakan requestKey sama
//            setFragmentResult("requestKey", bundleOf(
//                "newItemName" to name,
//                "newItemPrice" to price,
//                "newItemDesc" to description
//            ))
//
//            findNavController().popBackStack()
//
//        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddEditPriorityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddEditPriorityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}