import java.util.*

class EnclosureImpl(override val id: UUID = UUID.randomUUID()) : BaseEntity(), Enclosure {
    override val animals: MutableList<Animal> = mutableListOf()
    override val openPartEnclosure: MutableList<Animal> = mutableListOf()
    override val closePartEnclosure: MutableList<Animal> = mutableListOf()
    private val hashMap: MutableMap<Food, Int> = mutableMapOf()

    fun <T : Food, G : Food> addEnclosureFoodType(firstFoodType: T, secondFoodType: G) {
        hashMap[firstFoodType] = 0
        hashMap[secondFoodType] = 0
    }


    override fun addAnimal(species: String): Boolean {
        if ((!animals.isEmpty() && animals.size < MAX_CAPACITY && animals[0].species == species) || animals.isEmpty()) {
            val newAnimal = createAnimal(species)
            animals.add(newAnimal)
            if (animals.size == 1) { // First animal added
                hashMap[newAnimal.firstFoodType] = 0
                hashMap[newAnimal.secondFoodType] = 0
            }
            val randomNumber = (1..2).random()
            if (randomNumber == 1) {
                openPartEnclosure.add(newAnimal)
            } else {
                closePartEnclosure.add(newAnimal)
            }
            return true
        } else {
            return false
        }
    }

    override fun getEnclosureStatus() {
        if (animals.isNotEmpty()) {
            println("В вольере ${animals.size} животных ${animals[0].species}")
            hashMap.forEach { (food, quantity) ->
                println("Запас еды ${food.type}: $quantity")
            }
        } else {
            println("Вольер пуст")
        }
    }

    override fun moveAnimal(animal: Animal) {
        if (openPartEnclosure.contains(animal)) {
            closePartEnclosure.add(animal)
            openPartEnclosure.remove(animal)
        }
        else {
            openPartEnclosure.add(animal)
            closePartEnclosure.remove(animal)
        }
    }

    private fun createAnimal(species: String): Animal {
        return when (species.toLowerCase()) {
            "волк" -> Wolf(species)
            "попугай" -> Parrot(species)
            "обезьяна" -> Monkey(species)
            else -> throw IllegalArgumentException("Неподдерживаемый вид животного: $species")
        }
    }

    override fun removeAnimal(species: String): Boolean {
        val removed = animals.removeIf { it.species == species }
        if (removed && animals.isEmpty()) { // Last animal removed
            hashMap.clear()
        }
        return removed
    }
    override fun hasAnimal(species: String): Boolean {
        return animals.any { it.species == species }
    }

    override fun getAnimal(species: String): Animal? {
        return animals.find { it.species == species }
    }
    override fun getAnimalCounts(): Int {
        var count = 0
        for (animal in animals) {
            count++
        }
        return count
    }

    override fun refillFood() {
        if (animals.isNotEmpty()) {
            val firstFoodType = animals[0].firstFoodType
            val secondFoodType = animals[0].secondFoodType
            hashMap[firstFoodType] = hashMap[firstFoodType]!! + firstFoodType.value*animals.size
            hashMap[secondFoodType] = hashMap[secondFoodType]!! + secondFoodType.value*animals.size

            animals.forEach{
                if (it.hungerLevel<it.maxHungry) {
                    val randomNumber = (1..2).random()
                    if (randomNumber == 1) {
                        it.decreaseHungerLevel(-firstFoodType.value)
                        hashMap[firstFoodType] = hashMap[firstFoodType]!! - firstFoodType.value
                        println("животное ${it.species} поело еды вида ${firstFoodType.type}")
                    }
                    if (randomNumber == 2) {
                        it.decreaseHungerLevel(-secondFoodType.value)
                        hashMap[secondFoodType] = hashMap[secondFoodType]!! - secondFoodType.value
                        println("животное ${it.species} поело еды вида ${secondFoodType.type}")
                    }
                }
            }
        }
    }

}