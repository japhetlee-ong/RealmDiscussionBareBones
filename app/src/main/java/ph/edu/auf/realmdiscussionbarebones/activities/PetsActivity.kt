package ph.edu.auf.realmdiscussionbarebones.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.BsonObjectId
import ph.edu.auf.realmdiscussionbarebones.R
import ph.edu.auf.realmdiscussionbarebones.adapters.PetAdapter
import ph.edu.auf.realmdiscussionbarebones.databinding.ActivityPetsBinding
import ph.edu.auf.realmdiscussionbarebones.dialogs.AddPetDialog
import ph.edu.auf.realmdiscussionbarebones.models.Pet
import ph.edu.auf.realmdiscussionbarebones.realm.realmmodels.PetRealm

class PetsActivity : AppCompatActivity() , AddPetDialog.RefreshDataInterface, PetAdapter.PetAdapterInterface {

    private lateinit var binding: ActivityPetsBinding
    private lateinit var petList: ArrayList<Pet>
    private lateinit var adapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        petList = arrayListOf()
        adapter = PetAdapter(petList,this,this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvPets.layoutManager = layoutManager
        binding.rvPets.adapter = adapter

        binding.fab.setOnClickListener{
            val addPetDialog = AddPetDialog()
            addPetDialog.refreshDataCallback = this
            addPetDialog.show(supportFragmentManager,null)
        }

        binding.btnSearch.setOnClickListener{
            if(binding.edtSearch.text.toString().isEmpty()){
                binding.edtSearch.error = "Required"
                return@setOnClickListener
            }

            //TODO: REALM DISCUSSION HERE

        }

    }

    override fun onResume() {
        super.onResume()
        //TODO: REALM DISCUSSION HERE
    }

    override fun refreshData() {
        //TODO: REALM DISCUSSION HERE
    }


    override fun deletePet(id: String) {
        //TODO: REALM DISCUSSION HERE
    }


}