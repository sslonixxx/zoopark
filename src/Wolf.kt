class Wolf(species: String="волк",
           override val firstFoodType: Food = Meat(10),
           override val secondFoodType: Food = Beer(5)
) : Animal(species,firstFoodType,secondFoodType) {
    override fun makeSound() {
        println("$species воет на луну: Ууууу")
    }
    companion object {
        const val HUNGER_THRESHOLD = 10
    }
}