import java.util.*

val MAX_CAPACITY=5

class EnclosureImpl(override val id: UUID = UUID.randomUUID()) : BaseEntity(), Enclosure {
    override val animals: MutableList<Animal> = mutableListOf()
    override val openPartEnclosure: MutableList<Animal> = mutableListOf()
    override val closePartEnclosure: MutableList<Animal> = mutableListOf()
    override var foodQuantity: Int =0
    private val hashMap: MutableMap<Food, Int> = mutableMapOf()

    fun <T : Food, G : Food> addEnclosureFoodType(firstFoodType: T, secondFoodType: G) {
        hashMap[firstFoodType] = 0
        hashMap[secondFoodType] = 0
    }


    override fun addAnimal(species: String): Boolean {
        if ((!animals.isEmpty() && animals.size<MAX_CAPACITY && animals[0].species==species) || animals.isEmpty()) {
            animals.add(createAnimal(species))
            val randomNumber = (1..2).random()
            if (randomNumber==1) {
                openPartEnclosure.add(createAnimal(species))
            }
            else {
                closePartEnclosure.add(createAnimal(species))
            }
            return true
        }
        else {
            return false
        }
    }

    override fun getEnclosureStatus() {
        if (!animals.isEmpty()) {
            println("В вольере ${animals.size} животных ${animals[0].species}")
            println("Запас еды: $foodQuantity")
        }
        else {
            println("Вольер пуст")
        }
    }
    override fun moveAnimal(animal: Animal) {
        if (openPartEnclosure.contains(animal)) {
            closePartEnclosure.add(animal)
            openPartEnclosure.remove(animal)
        }
        else {
            openPartEnclosure.add(animal)
            closePartEnclosure.remove(animal)
        }
    }

    private fun createAnimal(species: String): Animal {
        return when (species.toLowerCase()) {
            "волк" -> Wolf(species)
            "попугай" -> Parrot(species)
            "обезьяна" -> Monkey(species)
            else -> throw IllegalArgumentException("Неподдерживаемый вид животного: $species")
        }
    }

    override fun removeAnimal(species: String): Boolean {
        val removed = animals.removeIf { it.species == species }
        return removed
    }
    override fun hasAnimal(species: String): Boolean {
        return animals.any { it.species == species }
    }

    override fun getAnimal(species: String): Animal? {
        return animals.find { it.species == species }
    }
    override fun getAnimalCounts(): Int {
        var count = 0
        for (animal in animals) {
            count++
        }
        return count
    }

    override fun refillFood(quantity: Int) {
        foodQuantity += quantity
        animals.forEach { it.decreaseHungerLevel(-quantity) }
    }

}

abstract class BaseEntity {
    open val id: UUID = UUID.randomUUID()
}


class ZooImpl<T :BaseEntity> :  Zoo {
    private val enclosures: MutableList<Enclosure> = mutableListOf(EnclosureImpl(), EnclosureImpl(), EnclosureImpl())
    private val visitors: MutableList<Visitor> = mutableListOf()
    private val employees: MutableList<Employee> = mutableListOf()
    override var isPaused = false

    val entityList: MutableList<T> = mutableListOf()

    init {
        repeat(15) {
            val randomAnimalType = listOf("попугай", "волк", "обезьяна").random()
            val newAnimal = createAnimal(randomAnimalType)
            addNewEntity(newAnimal as T)
        }
    }

    fun createAnimal(species: String): Animal {
        return when (species.lowercase(Locale.getDefault())) {
            "волк" -> Wolf()
            "попугай" -> Parrot()
            "обезьяна" -> Monkey()
            else -> throw IllegalArgumentException("Неподдерживаемый вид животного: $species")
        }
    }

    override fun getZooStatus() {
        println("Статус зоопарка:")

        // Вывод информации о вольерах
        val enclosures = entityList.filterIsInstance<Enclosure>()
        println("Количество вольеров: ${enclosures.size}")

        enclosures.forEachIndexed { index, enclosure ->
            println("Вольер ${index + 1}:")
            if (enclosure.animals.isNotEmpty()) {
                val species = enclosure.animals[0].species // Предполагается, что все животные в вольере одного вида
                println("$species: ${enclosure.animals.size} шт")
            } else {
                println("Вольер пуст")
            }
        }

        // Вывод информации о сотрудниках
        val employees = entityList.filterIsInstance<Employee>()
        println("Количество сотрудников: ${employees.size}")

        // Вывод информации о посетителях
        val visitors = entityList.filterIsInstance<Visitor>()
        println("Количество посетителей: ${visitors.size}")
    }


    fun addNewEntity(newEntity: T) {
        when (newEntity) {
            is Animal -> {
                entityList.filter { it is Enclosure }.let { it ->
                    var suitableEnclosure: Enclosure? = null
                    it.forEach {
                        if (it is Enclosure) {
                            if (it.addAnimal(newEntity.species)) {
                                entityList.add(newEntity)
                                suitableEnclosure = it
                                return
                            }
                        }
                    }
                    if (suitableEnclosure == null) {
                        val newEnclosure = EnclosureImpl()
                        newEnclosure.addAnimal(newEntity.species)
                        newEnclosure.addEnclosureFoodType(newEntity.firstFoodType, newEntity.secondFoodType)
                        entityList.add(newEntity)
                        entityList.add(newEnclosure as T)

                    }
                }
            }

            is Employee -> {
                entityList.add(newEntity as T)
                println("Сотрудник добавлен!")

            }

            is Visitor -> {
                entityList.add(newEntity as T)
                println("Посетитель добавлен!")

            }

            is Enclosure -> {
                entityList.add(newEntity as T)
                println("Вольер добавлен!")
            }
        }
    }

    override fun addEntity() {
        val scanner = Scanner(System.`in`)
        println("Выберите тип сущности для добавления:")
        println("1. Животное")
        println("2. Сотрудник")
        println("3. Посетитель")
        println("4. Вольер")

        when (scanner.nextInt()) {
            1 -> {
                println("Выберите вид животного:")
                println("1. Волк")
                println("2. Попугай")
                println("3. Обезьяна")
                scanner.nextLine()
                val animalChoice = scanner.nextInt()

                val species = when (animalChoice) {
                    1 -> "Волк"
                    2 -> "Попугай"
                    3 -> "Обезьяна"
                    else -> {
                        println("Неверный выбор, попробуйте снова.")
                        return
                    }
                }
                // Создаем конкретный объект животного
                val newAnimal = when (species) {
                    "Волк" -> Wolf()
                    "Попугай" -> Parrot()
                    "Обезьяна" -> Monkey()
                    else -> return
                }
                addNewEntity(newAnimal as T)
            }

            2 -> {
                println("Введите имя сотрудника:")
                scanner.nextLine() // Consume newline
                val name = scanner.nextLine()
                println("Кем будет заниматься сотрудник? (волков, попугаев или обезьян):")
                val position = scanner.nextLine()
                val employee = Employee(name, position)
                addNewEntity(employee as T)
            }

            3 -> {
                println("Введите имя посетителя:")
                scanner.nextLine() // Consume newline
                val name = scanner.nextLine()
                println("Введите пол посетителя (М/Ж):")
                val gender = scanner.nextLine().uppercase().first()
                val visitor = Visitor(name, gender, 50)
                addNewEntity(visitor as T)
            }

            4 -> {
                val enclosure = EnclosureImpl()
                addNewEntity(enclosure as T)
            }

            else -> {
                println("Неверный выбор, попробуйте снова.")
            }
        }
    }


//    fun getEntityList(): List<T> {
//        return entityList
//    }

    inline fun <reified U : T> getEntityByType(): List<U> {
        return entityList.filterIsInstance<U>()
    }

    fun deleteEntityById(id: UUID) {
        val entityToRemove = entityList.find { it.id == id }
        if (entityToRemove is Enclosure) {
            for (animal in entityToRemove.animals) {
                entityList.remove(animal as T)
            }
            entityList.remove(entityToRemove as T)
        } else {
            entityList.remove(entityToRemove)
        }
        println("remove done")
    }

    override fun removeEntity() {
        val scanner = Scanner(System.`in`)
        println(" 1 - посетителя\n 2 - сотудника\n 3 - животное\n 4 - вольер")
        val value = scanner.nextLine()
        when (value) {
            "1" -> {
                val visitorList = getEntityByType<Visitor>()
                for (i in 0 until visitorList.size) {
                    println("${i + 1} : ${visitorList[i].name}")
                }
                val answer = scanner.nextLine().lowercase(Locale.getDefault())
                deleteEntityById(visitorList[answer.toInt() - 1].id)
            }

            "2" -> {
                val employeeList = getEntityByType<Employee>()
                for (i in 0 until employeeList.size) {
                    println("${i + 1} : ${employeeList[i].name}")
                }
                val answer = scanner.nextLine()
                deleteEntityById(employeeList[answer.toInt() - 1].id)
            }

            "3" -> {
                val animalList = getEntityByType<Animal>()
                for (i in 0 until animalList.size) {
                    println("${i + 1} : ${animalList[i].type}")
                }
                val answer = scanner.nextLine().lowercase(Locale.getDefault())
                deleteEntityById(animalList[answer.toInt() - 1].id)
            }

            "4" -> {
                val enclosureList = getEntityByType<EnclosureImpl>()
                println("введите номер вольера от ${1} до ${enclosureList.size}")
                val answer = scanner.nextLine().lowercase(Locale.getDefault())
                deleteEntityById(enclosureList[answer.toInt() - 1].id)
            }

            else -> {
                println("некорректный формат, выберите номер сущности, которую хотите удалить")
            }
        }
    }

    override fun shuffleAnimals() {
        val enclosures = entityList.filterIsInstance<Enclosure>()
        for (enclosure in enclosures) {
            val randomNumber = (1..enclosure.animals.size).random()
            for (i in 1..randomNumber) {
                val randomAnimal = enclosure.animals.random()
                enclosure.moveAnimal(randomAnimal)
            }
        }
    }

    override fun checkEnclosureStatus() {
        val enclosures = entityList.filterIsInstance<Enclosure>()
        val scanner = Scanner(System.`in`)
        println("Введите номер вольера, который хотите проверить: 1..${enclosures.size}")
        val number = scanner.nextInt()
        if (number in 1..enclosures.size) {
            val enclosure = enclosures[number - 1]
            enclosure.getEnclosureStatus()
        } else {
            println("Неправильный номер вольера")
        }
    }

    override fun removeAnimal() {
        val scanner = Scanner(System.`in`)
        println("Введите вид животного для удаления:")
        val species = scanner.nextLine()

        var animalRemoved = false

        for (enclosure in enclosures) {
            if (enclosure.hasAnimal(species)) {
                val animal = enclosure.getAnimal(species)
                if (animal != null) {
                    enclosure.removeAnimal(species)
                    animalRemoved = true
                    println("Животное вида $species удалено из вольера.")
                    break
                }
            }
        }

        if (!animalRemoved) {
            println("Животное вида $species не найдено в зоопарке.")
        }
    }

    override fun updateAnimalStatus() {
        for (enclosure in enclosures) {
            val animals = enclosure.animals
            for (animal in animals) {
                animal.hungerLevel-- // Уменьшаем уровень голода с каждым тиком таймера
                val hungerThreshold = when (animal) {
                    is Wolf -> Wolf.HUNGER_THRESHOLD
                    is Parrot -> Parrot.HUNGER_THRESHOLD
                    is Monkey -> Monkey.HUNGER_THRESHOLD
                    else -> 10 // Значение по умолчанию
                }
                if (animal.hungerLevel <= hungerThreshold) {
                    // Порог голода достигнут, нужно пополнить запас еды в вольере
                    // Проверяем наличие сотрудника для пополнения запасов еды
                    val employee = employees.find { it.position == animal.species }
                    if (employee != null) {
                        // Пополняем запас еды в вольере, если есть сотрудник
                        employee.refillFood(enclosure, 10) // Например, пополняем еду первого типа на 10 единиц
                    } else {
                        println("Нет сотрудника для пополнения запасов еды в вольере ${animal.species}.")
                    }
                    break
                }
            }
        }
    }

    override fun deleteEnclosure() {
        val scanner = Scanner(System.`in`)
        println("Введите номер вольера для удаления:")
        val enclosureNumber = scanner.nextInt()
        enclosures.removeAt(enclosureNumber - 1)
        println("Вольер №$enclosureNumber успешно удален.")
    }

    override fun deleteEmployee() {
        val scanner = Scanner(System.`in`)
        println("Введите имя сотрудника для увольнения:")
        val name = scanner.nextLine()
        val employeeToRemove = employees.find { it.name == name }
        if (employeeToRemove != null) {
            removeEmployee(employeeToRemove)
            println("Сотрудник уволен!")
        } else {
            println("Сотрудник с именем $name не найден.")
        }
    }

    override fun removeEmployee(employee: Employee) {
        employees.remove(employee)
    }

    override fun removeVisitor(visitor: Visitor) {
        visitors.remove(visitor)
    }


    override fun getEmployeeStatus() {
        println("Статус сотрудников:")
        for (employee in employees) {
            employee.getEmployeeStatus()
        }
    }

    override fun getVisitorStatus() {
        println("Статус посетителей:")
        for (visitor in visitors) {
            visitor.getVisitorStatus()
        }

    }

    override fun makeAnimalSpeak() {
        val scanner = Scanner(System.`in`)
        println("Введите вид животного, которому нужно подать голос:")
        val species = scanner.nextLine()

        // Поиск животного во всех вольерах
        var animalFound = false
        for (enclosure in enclosures) {
            if (enclosure.hasAnimal(species)) {
                val animal = enclosure.getAnimal(species)
                animal?.makeSound()
                animalFound = true
                break
            }
        }

        if (!animalFound) {
            println("Животное с видом $species не найдено в зоопарке.")
        }
    }


    override fun checkAnimalStatus() {
        val scanner = Scanner(System.`in`)
        println("Введите вид животного, уровень сытости которого вы хотите узнать:")
        val species = scanner.nextLine().toLowerCase()

        // Поиск животного заданного вида во всех вольерах
        var animalFound = false
        for (enclosure in enclosures) {
            if (enclosure.hasAnimal(species)) {
                val animal = enclosure.getAnimal(species)
                if (animal != null) {
                    println("Уровень сытости ${animal.species}: ${animal.hungerLevel}")
                    animalFound = true
                    break
                }
            }
        }

        if (!animalFound) {
            println("Животное с видом $species не найдено в зоопарке.")
        }
    }

    override fun buyFood() {
        if (visitors.isNotEmpty()) {
            var randomVisitor = (0..visitors.size - 1).random()
            var randomEnclosure = (0..enclosures.size - 1).random()
            visitors[randomVisitor].feedAnimalByVisitor(enclosures[randomEnclosure].openPartEnclosure)
        }
    }

    override fun addSpecialVisitor(visitor: Visitor) {
        visitors.add(visitor)
    }

    override fun addSpecialEmployee(employee: Employee) {
        employees.add(employee)
    }


    override fun editInformation() {
        val scanner = Scanner(System.`in`)
        println("Выберите, кого вы хотите отредактировать:")
        println("1. Посетитель")
        println("2. Сотрудник")
        val choice = scanner.nextInt()
        scanner.nextLine()
        when (choice) {
            1 -> {
                println("Введите имя посетителя, которого вы хотите отредактировать:")
                val name = scanner.nextLine()
                val visitor = visitors.find { it.name == name }
                if (visitor != null) {
                    println("Введите новое имя:")
                    val newName = scanner.nextLine()
                    visitor.changeName(newName)
                    println("Имя посетителя успешно изменено на $newName.")
                } else {
                    println("Посетитель с именем $name не найден.")
                }
            }

            2 -> {
                println("Введите имя сотрудника, которого вы хотите отредактировать:")
                val name = scanner.nextLine()
                val employee = employees.find { it.name == name }
                if (employee != null) {
                    println("Введите новое имя сотрудника:")
                    val newName = scanner.nextLine()
                    println("Введите новую должность сотрудника:")
                    val newPosition = scanner.nextLine()
                    employee.changeName(newName)
                    employee.changePosition(newPosition)
                    println("Информация о сотруднике успешно изменена.")
                } else {
                    println("Сотрудник с именем $name не найден.")
                }
            }

            else -> {
                println("Неверный выбор. Попробуйте снова.")
                editInformation()
            }
        }

    }
    }


    fun main() {
        val zoo = ZooImpl<BaseEntity>()
        val timer = Timer()
        var time = 0
        val feeder1 = Employee("Соня", "волк")
        val feeder2 = Employee("Вика", "попугай")
        val feeder3 = Employee("Кристина", "обезьяна")

        val visitor = Visitor("Марат", 'м', 50)

//    zoo.addSpecialEmployee(feeder1)
//    zoo.addSpecialEmployee(feeder2)
//    zoo.addSpecialEmployee(feeder3)
//
//    zoo.addSpecialVisitor(visitor)
//    val animal = Monkey()
//    val animal2 = Monkey()
        val enclose = EnclosureImpl()
        zoo.addNewEntity(enclose)
//    zoo.addNewEntity(animal)
//    zoo.addNewEntity(animal2)


        val timerTask = object : TimerTask() {

            override fun run() {
                if (!zoo.isPaused) {
                    zoo.updateAnimalStatus()
                    println("Тик таймера")
                    time++
                }
                if (time % 5 == 0) {
                    //zoo.shuffleAnimals()
                    zoo.buyFood()
                }
            }
        }
        timer.scheduleAtFixedRate(timerTask, 0, 3000)

        displayMenu(zoo)
    }

    fun pauseTimer(zoo: Zoo) {
        zoo.isPaused = true
        println("Таймер приостановлен.")
    }

    fun resumeTimer(zoo: Zoo) {
        zoo.isPaused = false
        println("Таймер возобновлен.")
    }

    fun displayMenu(zoo: Zoo) {
        val scanner = Scanner(System.`in`)
        var choice: Int

        do {
            println("Добро пожаловать, директор!")
            println("Выберите действие:")
            println("1. Добавить сущность")
            println("3. Удалить животное")
            println("4. Проверить статус зоопарка")
            println("5. Проверить статус сотрудников")
            println("6. Проверить статус посетителей")
            println("7. Приказать животному подать голос")
            println("8. Узнать статус животного")
            println("10. Уволить сотрудника")
            println("11. Редактировать информацию")
            println("12. Действие с вольерами")

            println("0. Выйти")

            choice = scanner.nextInt()
            scanner.nextLine()

            when (choice) {
                1 -> zoo.addEntity()
                3 -> {
                    zoo.removeAnimal()
                }

                4 -> {
                    pauseTimer(zoo)
                    zoo.getZooStatus()
                }

                5 -> {
                    pauseTimer(zoo)
                    zoo.getEmployeeStatus()
                }

                6 -> {
                    pauseTimer(zoo)
                    zoo.getVisitorStatus()
                }

                7 -> zoo.makeAnimalSpeak()
                8 -> {
                    pauseTimer(zoo)
                    zoo.checkAnimalStatus()
                }

                10 -> zoo.deleteEmployee()
                11 -> zoo.editInformation()
                12 -> {
                    println("Что хотите сделать?")
                    println("1-добавить вольер")
                    println("2-удалить вольер")
                    println("3-проверить статус вольера")
                    val number = scanner.nextLine()
                    if (number == "1") {
                    }
                    if (number == "3") {
                        zoo.checkEnclosureStatus()
                    } else {
                        zoo.deleteEnclosure()
                    }

                }

                0 -> {
                    System.exit(0)
                }

                else -> {
                    println("Неверный выбор. Попробуйте снова.")
                }
            }

            if (choice !in listOf(4, 5, 6, 8)) {
                resumeTimer(zoo)
            }
        } while (choice != 0)
    }


//fun main() {
//    val zoo = ZooImpl<BaseEntity>()
//
//    println("\nДля выполнения операций введите команды:")
//
//    println("1 name gender - добавить посетителя")
//    println("2 type - добавить животное")
//    println("3 name gender position - добавить сотрудника")
//
//    println("4 name - удалить сущность")
//
//    println("7 parrot - голос попугая")
//    println("8 wolf - голос волка")
//    println("9 monkey - голос обезьяны")
//
//    println("10 - статус зоопарка")
//    println("11 - статус посетителей")
//    println("12 - статус сотрудников")
//    println("13 - статус животных")
//
//    println("14 oldName newName - редактировать имя сотрудника")
//    println("15 name newPosition - редактировать должность сотрудника")
//    println("16 oldName newName - редактировать имя посетителя")
//
//    println("17 - поставить на паузу программу")
//    println("18 - возобновить программу")
//    println("19 - завершить программу")
//
//    println("20 - добавить вольер")
//    println("21 - удалить вольер")
//    println("22 - статус вольеров")
//
//    val zooTimer = ZooTimer(zoo)
//
//    val vet = Employee("Johny", "Male", "Vet")
//    val security = Employee("Alice", "Female", "Security")
//    val cleaner = Employee("Bob", "Male", "Cleaner")
//
//    val visitor = Visitor("Lukas", "Male")
//
//    zoo.addNewEntity(vet)
//    zoo.addNewEntity(security)
//    zoo.addNewEntity(cleaner)
//    zoo.addNewEntity(visitor)
//
//    zooTimer.start()
//    val scanner = Scanner(System.`in`)
//
//    while (true) {
//        val input = scanner.nextLine().lowercase(Locale.getDefault())
//        val commands = input.split(" ")
//        when (commands[0]) {
//            "1" -> {
//                if (commands.size == 3) {
//                    println(zoo.addNewEntity(Visitor(commands[1], commands[2])))
//                } else {
//                    println("Некорректный формат добавления посетителя. Используйте '1 name gender'")
//                }
//            }
//
//            "2" -> {
//                if (commands.size == 2) {
//                    println(zoo.addNewEntity(zoo.createAnimal(commands[1])))
//                } else {
//                    println("Некорректный формат добавления животного. Используйте '2 type'")
//                }
//            }
//
//            "3" -> {
//                if (commands.size == 4) {
//                    val name = commands[1]
//                    val gender = commands[2]
//                    val position = commands[3].replace("monkey", "Monkey Feeder").replace("parrot", "Parrot Feeder")
//                        .replace("wolf", "Wolf Feeder")
//                    val employee = Employee(name, gender, position)
//                    println(zoo.addNewEntity(employee))
//                } else {
//                    println("Некорректный формат добавления сотрудника. Используйте '3 name gender position'")
//                }
//            }
//
//
//            "4" -> {
//                println(" 1 - посетителя\n 2 - рабочего\n 3 - животное\n 4 - вольер")
//                val value = scanner.nextLine().lowercase(Locale.getDefault())
//                when (value) {
//                    "1" -> {
//                        val visitorList = zoo.getEntityByType<Visitor>()
//                        for (i in 0 until visitorList.size) {
//                            println("${i + 1} : ${visitorList[i].name}")
//                        }
//                        val answer = scanner.nextLine().lowercase(Locale.getDefault())
//                        zoo.deleteEntityById(visitorList[answer.toInt() - 1].id)
//                    }
//                    "2" -> {
//                        val employeeList = zoo.getEntityByType<Employee>()
//                        for (i in 0 until employeeList.size) {
//                            println("${i + 1} : ${employeeList[i].name}")
//                        }
//                        val answer = scanner.nextLine()
//                        zoo.deleteEntityById(employeeList[answer.toInt() - 1].id)
//                    }
//                    "3" -> {
//                        val animalList = zoo.getEntityByType<Animal>()
//                        for (i in 0 until animalList.size) {
//                            println("${i + 1} : ${animalList[i].type}")
//                        }
//                        val answer = scanner.nextLine().lowercase(Locale.getDefault())
//                        zoo.deleteEntityById(animalList[answer.toInt() - 1].id)
//                    }
//                    "4" -> {
//                        val enclosureList = zoo.getEntityByType<EnclosureImpl>()
//                        println("введите номер вольера от ${1} до ${enclosureList.size}")
//                        val answer = scanner.nextLine().lowercase(Locale.getDefault())
//                        zoo.deleteEntityById(enclosureList[answer.toInt() - 1].id)
//                    }
//                    else -> {
//                        println("некорректный формат, выберите номер сущности, которую хотите удалить")
//                    }
//                }
//            }
//
//            "5" -> {
//                if (commands.size == 2) {
//                    println(zoo.deleteEmployee(commands[1]))
//                } else {
//                    println("Некорректный формат удаления сотрудника. Используйте '5 name'")
//                }
//            }
//
//            "6" -> {
//                if (commands.size == 2) {
//                    println(zoo.deleteAnimal(commands[1]))
//                } else {
//                    println("Некорректный формат удаления животного. Используйте '6 animalType'")
//                }
//            }
//
//            "7" -> Parrot().makeSound()
//            "8" -> Wolf().makeSound()
//            "9" -> Monkey().makeSound()
//
//            "10" -> println(zoo.checkStatusZoo())
//            "11" -> println(zoo.checkStatusVisitors())
//            "12" -> println(zoo.checkStatusEmployees())
//            "13" -> println(zoo.checkStatusAnimals())
//            "22" -> zoo.checkStatusOpenablePart()
//
//            "14" -> {
//                if (commands.size == 3) {
//                    println(zoo.editEmployeeName(commands[1], commands[2]))
//                } else {
//                    println("Некорректный формат редактирования имени сотрудника. Используйте '14 oldName newName'")
//                }
//            }
//
//            "15" -> {
//                if (commands.size == 3) {
//                    println(zoo.editEmployeePosition(commands[1], commands[2]))
//                } else {
//                    println("Некорректный формат редактирования должности сотрудника. Используйте '15 name newPosition'")
//                }
//            }
//
//            "16" -> {
//                if (commands.size == 3) {
//                    println(zoo.editVisitorName(commands[1], commands[2]))
//                } else {
//                    println("Некорректный формат редактирования имени посетителя. Используйте '16 oldName newName'")
//                }
//            }
//
//
