package ph.edu.auf.realmdiscussionbarebones.models

data class Pet (
    val id: String,
    val name: String,
    val age: Int,
    val petType: String = "",
    val ownerName: String = ""
)