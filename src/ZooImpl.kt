import java.util.*

class ZooImpl<T :BaseEntity> :  Zoo {
    override var isPaused = false

    private val entityList: MutableList<T> = mutableListOf()

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
                val visitorList = entityList.filterIsInstance<Visitor>()
                for (i in 0 until visitorList.size) {
                    println("${i + 1} : ${visitorList[i].name}")
                }
                val answer = scanner.nextLine().lowercase(Locale.getDefault())
                deleteEntityById(visitorList[answer.toInt() - 1].id)
            }

            "2" -> {
                val employeeList = entityList.filterIsInstance<Employee>()
                for (i in 0 until employeeList.size) {
                    println("${i + 1} : ${employeeList[i].name}")
                }
                val answer = scanner.nextLine()
                deleteEntityById(employeeList[answer.toInt() - 1].id)
            }

            "3" -> {
                val animalList = entityList.filterIsInstance<Animal>()
                for (i in 0 until animalList.size) {
                    println("${i + 1} : ${animalList[i].species}")
                }
                val answer = scanner.nextLine().lowercase(Locale.getDefault())
                deleteEntityById(animalList[answer.toInt() - 1].id)
            }

            "4" -> {
                val enclosureList = entityList.filterIsInstance<EnclosureImpl>()
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

    override fun updateAnimalStatus() {
        val enclosures=entityList.filterIsInstance<Enclosure>()
        val employees=entityList.filterIsInstance<Employee>()
        for (enclosure in enclosures) {
            val animals = enclosure.animals
            for (animal in animals) {
                animal.hungerLevel-- // Уменьшаем уровень голода с каждым тиком таймера
                if (animal.hungerLevel <= animal.maxHungry) {
                    // Порог голода достигнут, нужно пополнить запас еды в вольере
                    // Проверяем наличие сотрудника для пополнения запасов еды
                    val employee = employees.find { it.position == animal.species }
                    if (employee != null) {
                        // Пополняем запас еды в вольере, если есть сотрудник
                        employee.refillFood(enclosure) // Например, пополняем еду первого типа на 10 единиц
                    } else {
                        println("Нет сотрудника для пополнения запасов еды в вольере ${animal.species}.")
                    }
                    break
                }
            }
        }
    }

    override fun getEmployeeStatus() {
        println("Статус сотрудников:")
        val employees=entityList.filterIsInstance<Employee>()
        for (employee in employees) {
            employee.getEmployeeStatus()
        }
    }

    override fun getVisitorStatus() {
        println("Статус посетителей:")
        val visitors=entityList.filterIsInstance<Visitor>()
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
        val enclosures=entityList.filterIsInstance<Enclosure>()
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
        val enclosures=entityList.filterIsInstance<Enclosure>()
        for (enclosure in enclosures) {
            if (enclosure.hasAnimal(species)) {
                for (animal in enclosure.animals) {
                    if (animal != null) {
                        println("Уровень сытости ${animal.species}: ${animal.hungerLevel}")
                        animalFound = true
                    }
                }
            }
        }

        if (!animalFound) {
            println("Животное с видом $species не найдено в зоопарке.")
        }
    }

    override fun buyFood() {
        val visitors=entityList.filterIsInstance<Visitor>()
        val enclosures=entityList.filterIsInstance<Enclosure>()
        if (visitors.isNotEmpty()) {
            var randomVisitor = (0..visitors.size - 1).random()
            var randomEnclosure = (0..enclosures.size - 1).random()
            visitors[randomVisitor].feedAnimalByVisitor(enclosures[randomEnclosure].openPartEnclosure)
        }
    }

    override fun editInformation() {
        val scanner = Scanner(System.`in`)
        println("Выберите, кого вы хотите отредактировать:")
        println("1. Посетитель")
        println("2. Сотрудник")
        val choice = scanner.nextInt()
        scanner.nextLine()
        var employees=entityList.filterIsInstance<Employee>()
        var visitors=entityList.filterIsInstance<Visitor>()
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