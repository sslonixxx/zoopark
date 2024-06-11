class Parrot(species: String="попугай",
             override val firstFoodType: Food = Cucumber(5),
             override val secondFoodType: Food = Apple(3),
             override val maxHungry: Int = 5
) : Animal(species,firstFoodType,secondFoodType, maxHungry) {
    override fun makeSound() {
        println("$species говорит: Я попугай")
    }
}