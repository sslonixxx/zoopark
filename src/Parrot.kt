class Parrot(species: String="попугай",
             override val firstFoodType: Food = Cucumber(5),
             override val secondFoodType: Food = Apple(3)
) : Animal(species,firstFoodType,secondFoodType) {
    override fun makeSound() {
        println("$species говорит: Я попугай")
    }
    companion object {
        const val HUNGER_THRESHOLD = 5
    }
}