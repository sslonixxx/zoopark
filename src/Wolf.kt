class Wolf(species: String) : Animal(species) {
    override fun makeSound() {
        println("$species воет на луну: Ууууу")
    }
    companion object {
        const val HUNGER_THRESHOLD = 10
    }
}