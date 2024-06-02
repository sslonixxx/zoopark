//class EnclosureImpl : Enclosure {
//    override val animals: MutableList<Animal> = mutableListOf()
//    override val openPartEnclosure: MutableList<Animal> = mutableListOf()
//    override val closePartEnclosure: MutableList<Animal> = mutableListOf()
//    override var foodQuantity: Int =0
//
//
//    override fun addAnimal(species: String): Boolean {
//        if ((!animals.isEmpty() && animals.size<MAX_CAPACITY && animals[0].species==species) || animals.isEmpty()) {
//            animals.add(createAnimal(species))
//            val randomNumber = (1..2).random()
//            if (randomNumber==1) {
//                openPartEnclosure.add(createAnimal(species))
//            }
//            else {
//                closePartEnclosure.add(createAnimal(species))
//            }
//            return true
//        }
//        else {
//            return false
//        }
//    }
//
//    override fun getEnclosureStatus() {
//        if (!animals.isEmpty()) {
//            println("В вольере ${animals.size} животных ${animals[0].species}")
//            println("Запас еды: $foodQuantity")
//        }
//        else {
//            println("Вольер пуст")
//        }
//    }
//    override fun moveAnimal(animal: Animal) {
//        if (openPartEnclosure.contains(animal)) {
//            closePartEnclosure.add(animal)
//            openPartEnclosure.remove(animal)
//        }
//        else {
//            openPartEnclosure.add(animal)
//            closePartEnclosure.remove(animal)
//        }
//    }
//
//    private fun createAnimal(species: String): Animal {
//        return when (species.toLowerCase()) {
//            "волк" -> Wolf(species)
//            "попугай" -> Parrot(species)
//            "обезьяна" -> Monkey(species)
//            else -> throw IllegalArgumentException("Неподдерживаемый вид животного: $species")
//        }
//    }
//
//    override fun removeAnimal(species: String): Boolean {
//        val removed = animals.removeIf { it.species == species }
//        return removed
//    }
//    override fun hasAnimal(species: String): Boolean {
//        return animals.any { it.species == species }
//    }
//
//    override fun getAnimal(species: String): Animal? {
//        return animals.find { it.species == species }
//    }
//    override fun getAnimalCounts(): Int {
//        var count = 0
//        for (animal in animals) {
//            count++
//        }
//        return count
//    }
//
//    override fun refillFood(quantity: Int) {
//        foodQuantity += quantity
//        animals.forEach { it.decreaseHungerLevel(-quantity) }
//    }
//
//}