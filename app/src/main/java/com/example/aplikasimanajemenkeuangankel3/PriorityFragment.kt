package com.example.aplikasimanajemenkeuangankel3

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PriorityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PriorityFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PriorityAdapter

    private lateinit var priorityItems: MutableList<PriorityItem>

    private val avatarList = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            if (bundle.containsKey("updatedItemId")) {
                val updatedId = bundle.getLong("updatedItemId", -1L)
                if (updatedId != -1L) {

                    val itemToUpdate = priorityItems.find { it.id == updatedId }
                    itemToUpdate?.let { item ->
                        item.name = bundle.getString("updatedItemName", item.name)
                        item.price = bundle.getDouble("updatedItemPrice", item.price)
                        item.description = bundle.getString("updatedItemDesc", item.description)

                        val avatarDrawableId = item.imageUrl
                        item.imageUrl = bundle.getInt("updatedItemImageUrl", avatarDrawableId)

                        val index = priorityItems.indexOf(item)
                        if (index != -1) {
                            adapter.notifyItemChanged(index)
                        }
                    }
                }

            }
            else if (bundle.containsKey("newItemName")) {
                //ambil data
                val newItemName = bundle.getString("newItemName")
                val newItemPrice = bundle.getDouble("newItemPrice")
                val newItemDesc = bundle.getString("newItemDesc")

                val avatarDrawableId = avatarList.random()

                if (newItemName != null && newItemDesc != null) {
                    val newItem = PriorityItem(
                        id = System.currentTimeMillis(),
                        imageUrl = avatarDrawableId,
                        name = newItemName,
                        price = newItemPrice,
                        description = newItemDesc,
                        priorityPercentage = 0
                    )

                    priorityItems.add(newItem)
                    adapter.notifyItemInserted(priorityItems.size - 1)
            }
            }
        }
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_priority, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        priorityItems = mutableListOf(
            PriorityItem(1, R.drawable.avatar1, "Lenovo LOQ", 15000000.0, "Untuk gaming tipis-tipis", 80),
            PriorityItem(2, R.drawable.avatar2,"Kursi Ergonomis", 1000000.0,"Biar tidak pegel-pegel",30),
            PriorityItem(3, R.drawable.avatar3, "Sepatu Super", 30000000.0, "Untuk lari cepat", 50)
        )

        recyclerView = view.findViewById(R.id.priority_recycler_view)
        adapter = PriorityAdapter(
            priorityItems,
            onItemDeleted = { item, position ->
                priorityItems.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, priorityItems.size)
            },
            onItemEdited = { item ->
                val bundle = bundleOf(
                    "itemToEditId" to item.id,
                    "itemToEditName" to item.name,
                    "itemToEditPrice" to item.price,
                    "itemToEditDesc" to item.description
                )
                //navgiasi ke fragment edit sambil membawa bundle
                findNavController().navigate(R.id.action_navigation_priority_to_addEditPriorityFragment2, bundle)
            },
            onSetPriority = { item ->
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Set Priority for ${item.name}")

                val input = EditText(requireContext())
                input.inputType = InputType.TYPE_CLASS_NUMBER
                input.hint = "Enter percentage (0-100)"
                builder.setView(input)

                builder.setPositiveButton("OK") { dialog, _ ->
                    val percentage = input.text.toString().toIntOrNull()
                    if (percentage != null && percentage in 0..100) {
                        item.priorityPercentage = percentage
                        val index = priorityItems.indexOf(item)
                        if (index != -1) {
                            adapter.notifyItemChanged(index)
                        }
                    }
                    dialog.dismiss()
                }
                builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

                builder.show()

            }
        )

        recyclerView.adapter = adapter

        val addFab: FloatingActionButton = view.findViewById(R.id.add_fab)
        addFab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_priority_to_addEditPriorityFragment2)
        }
        //Logika sorting
        val sortButton: Button = view.findViewById(R.id.sort_button)
        sortButton.setOnClickListener { sortView ->
            val popup = PopupMenu(requireContext(), sortView)
            popup.menuInflater.inflate(R.menu.sort_options_menu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.sort_ascending -> {
                        priorityItems.sortBy { it.priorityPercentage }
//                        adapter.updateData(priorityItems)
                        adapter.notifyDataSetChanged()
                        true
                    }

                    R.id.sort_descending -> {
                        priorityItems.sortByDescending { it.priorityPercentage }
                        adapter.notifyDataSetChanged()
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }
    }
}
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PriorityFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PriorityFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}