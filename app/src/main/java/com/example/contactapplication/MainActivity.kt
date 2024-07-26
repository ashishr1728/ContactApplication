package com.example.contactapplication

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var fab : FloatingActionButton

    private lateinit var edtName : EditText
    private lateinit var edtNumber : EditText
    private lateinit var contactImage : ImageView
    private lateinit var btnChooseImage : Button
    private lateinit var btnAddContact : Button
    private lateinit var dialog : Dialog

    private lateinit var contactAdapter: ContactAdapter
    val listOfContacts = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter(listOfContacts)
        rv.adapter = contactAdapter
        fab = findViewById(R.id.fab)

        fab.setOnClickListener{
            showDialog()
        }

    }

    private fun showDialog(){
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog)

        edtName = dialog.findViewById(R.id.edit_name)
        edtNumber = dialog.findViewById(R.id.edit_number)
        contactImage = dialog.findViewById(R.id.image_preview)
        btnChooseImage = dialog.findViewById(R.id.choose_image_btn)
        btnAddContact = dialog.findViewById(R.id.add_contact_btn)
//
        btnChooseImage.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == RESULT_OK){
            contactImage.visibility = View.VISIBLE
            contactImage.setImageURI(data?.data)

            btnAddContact.setOnClickListener{
                val nameFromEdt = edtName.text.toString()
                val phoneFromEdt = edtNumber.text.toString()
                val imageFromEdt = data?.data

                val contact = Contact(
                    profile = imageFromEdt!!,
                    nameText = nameFromEdt,
                    numberText = phoneFromEdt
                )

                listOfContacts.add(contact)
                contactAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
    }
}