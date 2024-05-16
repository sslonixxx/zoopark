class Employee(name: String, var position: String) : Person(name) {
    fun refillFood(enclosure: Enclosure, quantity: Int) {
        if (position == enclosure.animals[0].species.toLowerCase()) {
            enclosure.refillFood(quantity)
            println("Сотрудник $name пополнил запасы еды в вольерах животных ${enclosure.animals[0].species}. Статусы животных обновлены")
        }
    }

    fun getEmployeeStatus() {
        println("Сотрудник: $name, Должность: кормит $position")
    }

    fun changeName(newName: String) {
        name = newName
    }

    fun changePosition(newPosition: String) {
        position = newPosition
    }
}