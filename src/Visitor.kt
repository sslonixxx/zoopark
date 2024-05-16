class Visitor(name: String, private val gender: Char, private var money: Int) : Person(name) {


    fun buyFood(cost : Int): Boolean {
        if (money>=cost) {
            money-=cost
            println("Посетитель $name купил еду за $cost рублей")
            return true
        }
        else {
            println("Недостаточно средств на покупку еды")
            return false
        }
    }
    fun feedAnimalByVisitor(openPartEnclosure: MutableList<Animal>) {
        if (buyFood(10)) {
            for (animal in openPartEnclosure) {
                val hungerThreshold = when (animal) {
                    is Wolf -> Wolf.HUNGER_THRESHOLD
                    is Parrot -> Parrot.HUNGER_THRESHOLD
                    is Monkey -> Monkey.HUNGER_THRESHOLD
                    else -> 10 // Значение по умолчанию
                }
                if (animal.hungerLevel<hungerThreshold) {
                    animal.decreaseHungerLevel(-7)
                    println("Животное ${animal.species} покормлено посетителем $name")
                }
                else {
                    println("Животное ${animal.species} отказывается от еды")
                }
            }
        }
    }
    fun getGender(): Char {
        return gender
    }

    fun getVisitorStatus() {
        println("Посетитель: $name, Пол: ${getGender()}")
    }

    fun changeName(newName: String) {
        name = newName
    }
}


