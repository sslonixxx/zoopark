interface Enclosure {
    fun addAnimal(species: String): Boolean
    fun removeAnimal(species: String): Boolean
    fun hasAnimal(species: String): Boolean
    fun getAnimal(species: String): Animal?
    fun getAnimalCounts(): Int
    val animals: MutableList<Animal>
    val openPartEnclosure: MutableList<Animal>
    val closePartEnclosure: MutableList<Animal>

    fun refillFood()
    fun moveAnimal(animal: Animal)
    fun getEnclosureStatus()
}