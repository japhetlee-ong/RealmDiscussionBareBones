package ph.edu.auf.realmdiscussionbarebones.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.auf.realmdiscussionbarebones.databinding.ContentPetRvBinding
import ph.edu.auf.realmdiscussionbarebones.models.Pet

class PetAdapter(private var petList: ArrayList<Pet>, private var context: Context, private var petAdapterCallback: PetAdapterInterface): RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    interface PetAdapterInterface{
        fun deletePet(id: String)
    }

    inner class PetViewHolder(private val binding: ContentPetRvBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemData : Pet){
            with(binding){
                txtAge.text = String.format("Age: %s", itemData.age.toString())
                txtPetName.text = String.format("Pet name: %s", itemData.name)
                txtPetType.text = String.format("Pet type: %s", itemData.petType)
                if(itemData.ownerName.isNotEmpty()){
                    txtOwnerName.visibility = View.VISIBLE
                    txtOwnerName.text = String.format("Owner name: %s",itemData.ownerName)
                }else{
                    txtOwnerName.visibility = View.GONE
                }
                btnRemove.setOnClickListener{
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("Are you sure you want to delete this pet?")
                    builder.setTitle("Warning!")
                    builder.setPositiveButton("Yes"){dialog, _ ->
                        petList.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        petAdapterCallback.deletePet(itemData.id)
                        dialog.dismiss()
                    }
                    builder.setNegativeButton("No") {dialog, _ ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ContentPetRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petData = petList[position]
        holder.bind(petData)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    fun updatePetList(petList: ArrayList<Pet>){
        this.petList = arrayListOf()
        notifyDataSetChanged()
        this.petList = petList
        this.notifyItemInserted(this.petList.size)
    }

}