package com.example.miguelangel_jimenez_dam


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.miguelangel_jimenez_dam.databinding.ActivityAddItemBinding
import com.example.miguelangel_jimenez_dam.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage





class AddItemActivity : AppCompatActivity() {

    // Food item details
    private lateinit var foodName : String
    private lateinit var foodPrice : String
    private lateinit var foodDescription : String
    private var foodImageUri : Uri? = null
    private lateinit var foodIngredient : String

    private lateinit var autn : FirebaseAuth
    private lateinit var database : FirebaseDatabase

    private lateinit var binding : ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.image.visibility = View.GONE
        // Initialize

        autn = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()

        binding.addItemBtn.setOnClickListener {

            // Get all text form edittext
            foodName = binding.enterFoodName.text.toString().trim()
            foodPrice = binding.enterFoodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient = binding.ingredients.text.toString().trim()

            if (foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank()){

                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            } else{
                dataUpload()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }

        }
        binding.addImage.setOnClickListener {
            picImage.launch("image/*")
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun dataUpload() {
        // Get Reference to the "Menu" node in the database
        val menuRef = database.getReference("Menu")

        // Generate unique key for new menu
        val newItemKey = menuRef.push().key

        if (foodImageUri != null){

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("Menu_Images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                        downloadUerl->

                    //Create a new menu item
                    val menuItem = AllMenu(
                        newItemKey,
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription= foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUerl.toString()
                    )
                    newItemKey?.let {
                            key->
                        menuRef.child(key).setValue(menuItem).addOnSuccessListener {
                            Toast.makeText(this, "Plato añadido correctamente", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error al añadir el plato", Toast.LENGTH_SHORT).show()
                            }
                    }
                } .addOnFailureListener {
                    Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()

                }
            }
        } else{
            Toast.makeText(this, "Elija una imagen", Toast.LENGTH_SHORT).show()
        }

    }

    private  val picImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null){
            binding.image.visibility = View.VISIBLE
            binding.image.setImageURI(it)
            foodImageUri = it
        }
    }
}