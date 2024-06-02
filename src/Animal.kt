import java.util.*

abstract class Animal(
    var species: String,
    open val firstFoodType: Food,
    open val secondFoodType: Food
) : BaseEntity() {
    override val id: UUID = UUID.randomUUID()
    var hungerLevel: Int = 15

    open fun makeSound() {}

    fun decreaseHungerLevel(amount: Int) {
        hungerLevel -= amount
        if (hungerLevel < 0) hungerLevel = 0
    }
}

enum class FoodType {
    Apple,
    Meat,
    Cucumber,
    Beer,
    HQD
}

abstract class Food {
    open val value: Int = 0
    open val type: FoodType = FoodType.Apple
}

class Apple(override val value: Int) : Food() {
    override val type = FoodType.Apple
}
class HQD(override val value: Int) : Food() {
    override val type = FoodType.HQD
}
class Meat(override val value: Int) : Food() {
    override val type = FoodType.Meat
}
class Beer(override val value: Int) : Food() {
    override val type = FoodType.Beer
}
class Cucumber(override val value: Int) : Food() {
    override val type = FoodType.Cucumber
}