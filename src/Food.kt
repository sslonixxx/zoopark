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