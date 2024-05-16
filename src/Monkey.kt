class Monkey(species: String) : Animal(species) {
    override fun makeSound() {
        println("$species кричит: УУУУаааа")
    }
    companion object {
        const val HUNGER_THRESHOLD = 7
    }
}