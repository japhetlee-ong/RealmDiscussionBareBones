package ph.edu.auf.realmdiscussionbarebones.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ph.edu.auf.realmdiscussionbarebones.R
import ph.edu.auf.realmdiscussionbarebones.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOwnerList.setOnClickListener(this)
        binding.btnPetList.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_owner_list -> {
                val intent = Intent(this,OwnersActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_pet_list -> {
                val intent = Intent(this,PetsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}