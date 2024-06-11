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
                if (animal.hungerLevel<animal.maxHungry) {
                    val randomNumber = (1..2).random()
                    if (randomNumber == 1) {
                        animal.decreaseHungerLevel(-animal.firstFoodType.value)
                        println("животное ${animal.species} поело еды вида ${animal.firstFoodType.type} от посетителя $name")
                    }
                    if (randomNumber==2) {
                        animal.decreaseHungerLevel(-animal.secondFoodType.value)
                        println("животное ${animal.species} поело еды вида ${animal.secondFoodType.type} от посетителя $name")
                    }
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
        println("Посетитель: $name, Пол: ${getGender()} Денег осталось:${money}" )
    }

    fun changeName(newName: String) {
        name = newName
    }
}


