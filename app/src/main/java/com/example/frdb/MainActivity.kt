package com.example.frdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frdb.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val phoneDao = PhoneDao()

        binding.save.setOnClickListener{
            phoneDao.saveData(getPhone())
        }
        binding.get.setOnClickListener{
            phoneDao.getData()
        }
        phoneDao.getDataLiveData.observe(this){
            binding.data.text = it?.toString()
        }
        phoneDao.updateLiveData.observe(this) {
            binding.updateData.text = it?.toString()
        }

    }
    private fun getPhone() = Phone(
        brand = "Apple",
        model = "15 pro",
        battery = Battery(uid = UUID.randomUUID().toString(),power = 100,model = "lithium-ion"),
        googleAccounts = listOf(
            "random1@gmail.com",
            "random2@gmail.com",
            "random3@gmail.com",
        ),
        processor = "Snapdragon"
    )
    data class Phone(
        val brand: String? = null,
        val model: String? = null,
        val battery: Battery? = null,
        val googleAccounts: List<String>? = null,
        val processor: String? = null
    ){
        override fun toString(): String {
            return "brand : $brand, model : $model, battery : $battery, google accounts : $googleAccounts, processor : $processor"
        }
    }
    class PhoneDao: FRDBWrapper<Phone>() {
        override fun getTableName(): String  = "Phone"

        override fun getClassType(): Class<Phone> = Phone::class.java

    }
    data class Battery(
        val uid: String? = null,
        val power : Int? = null,
        val model: String? = null
    )
}
