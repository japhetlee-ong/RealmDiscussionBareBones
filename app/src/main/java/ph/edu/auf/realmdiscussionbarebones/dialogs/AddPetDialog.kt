package ph.edu.auf.realmdiscussionbarebones.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ph.edu.auf.realmdiscussionbarebones.databinding.DialogAddPetBinding
import ph.edu.auf.realmdiscussionbarebones.models.Pet
import ph.edu.auf.realmdiscussionbarebones.realm.RealmDatabase

class AddPetDialog : DialogFragment() {

    private lateinit var binding: DialogAddPetBinding
    lateinit var refreshDataCallback: RefreshDataInterface
    private var database = RealmDatabase()


    interface RefreshDataInterface{
        fun refreshData()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAddPetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            cbHasOwner.setOnCheckedChangeListener{ _, isChecked ->
                if(isChecked){
                    edtOwner.isEnabled = true
                }
            }
            btnAdd.setOnClickListener{
                if(edtPetName.text.isNullOrEmpty()){
                    edtPetName.error = "Required"
                    return@setOnClickListener
                }
                if(edtAge.text.isNullOrEmpty()){
                    edtAge.error = "Required"
                    return@setOnClickListener
                }
                if(edtPetType.text.isNullOrEmpty()){
                    edtPetType.error = "Required"
                    return@setOnClickListener
                }
                if(cbHasOwner.isChecked && edtOwner.text.isNullOrEmpty()){
                    edtOwner.error = "Required"
                    return@setOnClickListener
                }

                val petAge = edtAge.text.toString().toInt()
                val ownerName = if(cbHasOwner.isChecked) edtOwner.text.toString() else ""

                //TODO: DISCUSSION FOR REALM HERE
                val coroutineContext = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutineContext + CoroutineName("addPetToReam"))
                scope.launch (Dispatchers.IO) {
                    database.addPet(edtPetName.text.toString(),petAge,edtPetType.text.toString(),ownerName)
                    withContext(Dispatchers.Main){
                        Toast.makeText(activity,"Pet has been added", Toast.LENGTH_LONG).show()
                        refreshDataCallback.refreshData()
                        dialog?.dismiss()
                    }
                }


            }
        }


    }
}