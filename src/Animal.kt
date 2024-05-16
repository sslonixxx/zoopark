open class Animal(var species: String) {
    var hungerLevel: Int = 15

    open fun makeSound() {}

    fun decreaseHungerLevel(amount: Int) {
        hungerLevel -= amount
        if (hungerLevel < 0) hungerLevel = 0
    }

}