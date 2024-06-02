class Monkey(
    species: String = "обезьяна",
    override val firstFoodType: Food = HQD(10),
    override val secondFoodType: Food = Apple(3)
) : Animal(species,firstFoodType,secondFoodType) {

    override fun makeSound() {
        println("$species кричит: УУУУаааа")
    }
    companion object {
        const val HUNGER_THRESHOLD = 7
    }
}
