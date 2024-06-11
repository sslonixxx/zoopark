import java.util.*

abstract class Animal(
    var species: String,
    open val firstFoodType: Food,
    open val secondFoodType: Food,
    open val maxHungry: Int
) : BaseEntity() {
    override val id: UUID = UUID.randomUUID()
    var hungerLevel: Int = 15

    open fun makeSound() {}

    fun decreaseHungerLevel(amount: Int) {
        hungerLevel -= amount
        if (hungerLevel < 0) hungerLevel = 0
    }
}


