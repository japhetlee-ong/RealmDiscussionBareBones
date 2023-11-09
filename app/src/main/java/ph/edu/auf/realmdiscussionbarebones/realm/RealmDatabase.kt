package ph.edu.auf.realmdiscussionbarebones.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import ph.edu.auf.realmdiscussionbarebones.realm.realmmodels.OwnerRealm
import ph.edu.auf.realmdiscussionbarebones.realm.realmmodels.PetRealm
import java.lang.IllegalStateException

class RealmDatabase {

    private val realm: Realm by lazy {
        val config = RealmConfiguration.Builder(
            schema = setOf(PetRealm::class, OwnerRealm::class)
        ).schemaVersion(1)
            .build()
        Realm.open(config)
    }

    fun getAllPets() : List<PetRealm> {
        return realm.query<PetRealm>().find()
    }

    fun getAllPetsAsFlow(): Flow<List<PetRealm>>{
        return realm.query<PetRealm>().asFlow().map { it.list }
    }

    fun getPetsByName(name: String) : List<PetRealm>{
        return realm.query<PetRealm>("name CONTAINS $0",name).find()
    }

    suspend fun addPet(name: String, age: Int, type: String, ownerName: String = ""){
        realm.write {
            val pet = PetRealm().apply {
                this.name = name
                this.age = age
                this.petType = type
            }
            val managePet = copyToRealm(pet)
            if(ownerName.isNotEmpty()){
                //THERE IS AN OWNER
                val ownerResult : OwnerRealm? = realm.query<OwnerRealm>("name == $0",ownerName).first().find()
                if(ownerResult == null){
                    val owner = OwnerRealm().apply {
                        this.name = ownerName
                        this.pets.add(managePet)
                    }
                    val manageOwner = copyToRealm(owner)
                    managePet.owner = manageOwner
                }else{
                    findLatest(ownerResult)?.pets?.add(managePet)
                    findLatest(managePet)?.owner = findLatest(ownerResult)
                }
            }
        }
    }

    suspend fun deletePet(id: ObjectId){
        realm.write {
            query<PetRealm>("id = $0", id)
                .first()
                .find()
                ?.let { delete(it) }
                ?: throw IllegalStateException("Pet not found")
        }
    }

    fun getAllOwners(): List<OwnerRealm>{
        return realm.query<OwnerRealm>().find()
    }

}