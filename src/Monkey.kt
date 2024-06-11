class Monkey(
    species: String = "обезьяна",
    override val firstFoodType: Food = HQD(10),
    override val secondFoodType: Food = Apple(3),
    override val maxHungry: Int = 7
) : Animal(species,firstFoodType,secondFoodType, maxHungry) {

    override fun makeSound() {
        println("$species кричит: УУУУаааа")
    }
}
