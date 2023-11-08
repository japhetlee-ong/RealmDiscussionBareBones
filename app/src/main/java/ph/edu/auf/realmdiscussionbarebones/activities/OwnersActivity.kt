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
import ph.edu.auf.realmdiscussionbarebones.R
import ph.edu.auf.realmdiscussionbarebones.adapters.OwnerAdapter
import ph.edu.auf.realmdiscussionbarebones.databinding.ActivityOwnersBinding
import ph.edu.auf.realmdiscussionbarebones.models.Owner

class OwnersActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOwnersBinding
    private lateinit var adapter: OwnerAdapter
    private lateinit var ownerList: ArrayList<Owner>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ownerList = arrayListOf()
        adapter = OwnerAdapter(ownerList)

        val layoutManger = LinearLayoutManager(this)
        binding.rvOwner.layoutManager = layoutManger
        binding.rvOwner.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
    }

    //TODO: REALM DISCUSSION HERE

}