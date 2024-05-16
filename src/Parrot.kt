class Parrot(species: String) : Animal(species) {
    override fun makeSound() {
        println("$species говорит: Я попугай")
    }
    companion object {
        const val HUNGER_THRESHOLD = 5
    }
}