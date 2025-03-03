package com.example.contactapp

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp_withnavdrawer.ContactAdapter
import com.example.contactapp_withnavdrawer.R
import com.example.contactapp_withnavdrawer.databinding.FragmentContactsBinding


class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    private lateinit var binding: FragmentContactsBinding

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentContactsBinding.bind(view)

        binding.contactRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.contactRecyclerView.adapter = ContactAdapter(getContacts())
    }

    private fun getContacts(): List<ContactPerson> {
        val contactsList = mutableListOf<ContactPerson>()
        val contentResolver: ContentResolver = requireContext().contentResolver // reads from cont prov

        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            ),
            null, null, null
        )

        cursor?.use {  // auto close
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
// [name, phone, image]
            while (it.moveToNext()) {
                val name = it.getString(nameIndex) ?: "No Name"
                val number = it.getString(numberIndex) ?: "No Number"
                Log.d("Contacts", "Name: $name, Number: $number")
                val photoUri = it.getString(photoIndex)?.let { Uri.parse(it) }

                contactsList.add(ContactPerson(name, number, photoUri))
            }
        }

        return contactsList
    }
}
