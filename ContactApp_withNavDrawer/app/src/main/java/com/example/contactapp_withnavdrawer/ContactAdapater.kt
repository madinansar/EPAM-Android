package com.example.contactapp_withnavdrawer

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.ContactPerson
import com.example.contactapp_withnavdrawer.databinding.ContactItemLayoutBinding

class ContactAdapter(private val contacts : List<ContactPerson>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ContactItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    class ContactViewHolder(private val binding: ContactItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: ContactPerson) {
            binding.name.text = person.firstName
            binding.phone.text = person.phone

            if (person.image != null) {
                binding.image.setImageURI(person.image)
            } else {
                binding.image.setImageResource(R.drawable.default_image)
            }

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${person.phone}")
                }
                itemView.context.startActivity(intent)
            }

        }
    }
}