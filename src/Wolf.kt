class Wolf(species: String="волк",
           override val firstFoodType: Food = Meat(10),
           override val secondFoodType: Food = Beer(5),
           override val maxHungry: Int = 10
) : Animal(species,firstFoodType,secondFoodType, maxHungry) {
    override fun makeSound() {
        println("$species воет на луну: Ууууу")
    }
}