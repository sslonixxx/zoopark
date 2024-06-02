//import java.util.*
//
//class ZooImpl : Zoo {
//    private val enclosures: MutableList<Enclosure> = mutableListOf(EnclosureImpl(),EnclosureImpl(),EnclosureImpl())
//    private val visitors: MutableList<Visitor> = mutableListOf()
//    private val employees: MutableList<Employee> = mutableListOf()
//    override var isPaused = false
//
//
//    init {
//        val availableSpecies = listOf("волк", "попугай", "обезьяна")
//        for (i in 1..15) {
//
//            val randomSpecies = availableSpecies.random()
//            var suitableEnclosure: Enclosure? = null
//            for (enclosure in enclosures) {
//                if (enclosure.addAnimal(randomSpecies)) {
//                    suitableEnclosure = enclosure
//                    break
//                }
//            }
//
//            // Если не найден подходящий вольер, создаем новый
//            if (suitableEnclosure == null) {
//                val newEnclosure = EnclosureImpl()
//                newEnclosure.addAnimal(randomSpecies)
//                enclosures.add(newEnclosure)
//            }
//
//            println("Инициализация завершена. Зоопарк готов к работе.")
//        }
//    }
//
//    override fun shuffleAnimals() {
//        for (enclosure in enclosures) {
//            var randomNumber = (1..enclosure.animals.size).random()
//            for (i in 1..randomNumber) {
//                val randomAnimal = enclosure.animals.random()
//                enclosure.moveAnimal(randomAnimal)
//            }
//        }
//    }
//    override fun checkEnclosureStatus() {
//        val scanner = Scanner(System.`in`)
//        println("Введите вольера, который хотите проверить: 1..${enclosures.size}")
//        val number = scanner.nextInt()
//        for (enclosure in enclosures) {
//            if (enclosures.indexOf(enclosure)==number) {
//                enclosure.getEnclosureStatus()
//            }
//        }
//
//    }
//
//    override fun addAnimal() {
//        val scanner = Scanner(System.`in`)
//        println("Введите вид животного для добавления:")
//        val species = scanner.nextLine()
//
//        // Поиск свободного вольера с подходящим видом животного
//        var suitableEnclosure: Enclosure? = null
//        for (enclosure in enclosures) {
//            if (enclosure.addAnimal(species)) {
//                suitableEnclosure = enclosure
//                break
//            }
//        }
//
//        // Если не найден подходящий вольер, создаем новый
//        if (suitableEnclosure == null) {
//            val newEnclosure = EnclosureImpl()
//            newEnclosure.addAnimal(species)
//            enclosures.add(newEnclosure)
//        }
//
//        println("Животное успешно добавлено в вольер.")
//    }
//
//    override fun removeAnimal() {
//        val scanner = Scanner(System.`in`)
//        println("Введите вид животного для удаления:")
//        val species = scanner.nextLine()
//
//        var animalRemoved = false
//
//        for (enclosure in enclosures) {
//            if (enclosure.hasAnimal(species)) {
//                val animal = enclosure.getAnimal(species)
//                if (animal != null) {
//                    enclosure.removeAnimal(species)
//                    animalRemoved = true
//                    println("Животное вида $species удалено из вольера.")
//                    break
//                }
//            }
//        }
//
//        if (!animalRemoved) {
//            println("Животное вида $species не найдено в зоопарке.")
//        }
//    }
//
//    override fun updateAnimalStatus() {
//        for (enclosure in enclosures) {
//            val animals = enclosure.animals
//            for (animal in animals) {
//                animal.hungerLevel-- // Уменьшаем уровень голода с каждым тиком таймера
//                val hungerThreshold = when (animal) {
//                    is Wolf -> Wolf.HUNGER_THRESHOLD
//                    is Parrot -> Parrot.HUNGER_THRESHOLD
//                    is Monkey -> Monkey.HUNGER_THRESHOLD
//                    else -> 10 // Значение по умолчанию
//                }
//                if (animal.hungerLevel <= hungerThreshold) {
//                    // Порог голода достигнут, нужно пополнить запас еды в вольере
//                    // Проверяем наличие сотрудника для пополнения запасов еды
//                    val employee = employees.find { it.position == animal.species }
//                    if (employee != null) {
//                        // Пополняем запас еды в вольере, если есть сотрудник
//                        employee.refillFood(enclosure, 10) // Например, пополняем еду первого типа на 10 единиц
//                    } else {
//                        println("Нет сотрудника для пополнения запасов еды в вольере ${animal.species}.")
//                    }
//                    break
//                }
//            }
//        }
//    }
//
//
//    override fun addEnclosure() {
//        var enclosure=EnclosureImpl()
//        enclosures.add(enclosure)
//    }
//
//    override fun deleteEnclosure(){
//        val scanner = Scanner(System.`in`)
//        println("Введите номер вольера для удаления:")
//        val enclosureNumber = scanner.nextInt()
//        enclosures.removeAt(enclosureNumber - 1)
//        println("Вольер №$enclosureNumber успешно удален.")
//    }
//    override fun addEmployee() {
//        val scanner = Scanner(System.`in`)
//        println("Введите имя сотрудника:")
//        val name = scanner.nextLine()
//        println("Кем будет заниматься сотрудник? (волков, попугаев или обезьян):")
//        val position = scanner.nextLine()
//        val employee = Employee(name, position)
//        employees.add(employee)
//        println("Сотрудник нанят!")
//    }
//    override fun deleteEmployee() {
//        val scanner = Scanner(System.`in`)
//        println("Введите имя сотрудника для увольнения:")
//        val name = scanner.nextLine()
//        val employeeToRemove = employees.find { it.name == name }
//        if (employeeToRemove != null) {
//            removeEmployee(employeeToRemove)
//            println("Сотрудник уволен!")
//        } else {
//            println("Сотрудник с именем $name не найден.")
//        }
//    }
//    override fun removeEmployee(employee: Employee) {
//        employees.remove(employee)
//    }
//
//    override fun addVisitor() {
//        val scanner = Scanner(System.`in`)
//        println("Введите имя посетителя:")
//        val name = scanner.nextLine()
//        println("Введите пол посетителя (М/Ж):")
//        val gender = scanner.nextLine().toUpperCase().first()
//        val visitor = Visitor(name, gender, 50)
//        visitors.add(visitor)
//        println("Посетитель добавлен!")
//    }
//
//    override fun removeVisitor(visitor: Visitor) {
//        visitors.remove(visitor)
//    }
//
//    override fun getZooStatus() {
//        println("Статус зоопарка:")
//
//        // Вывод информации о вольерах
//        println("Количество вольеров: ${enclosures.size}")
//        for ((index, enclosure) in enclosures.withIndex()) {
//            println("Вольер ${index + 1}:")
//            if (!enclosure.animals.isEmpty()) {
//                println("${enclosure.animals[0].species} :${enclosure.animals.size} шт")
//            }
//        }
//
//        // Вывод информации о сотрудниках
//        println("Количество сотрудников: ${employees.size}")
//
//        // Вывод информации о посетителях
//        println("Количество посетителей: ${visitors.size}")
//
//    }
//
//
//    override fun getEmployeeStatus() {
//        println("Статус сотрудников:")
//        for (employee in employees) {
//            employee.getEmployeeStatus()
//        }
//    }
//
//    override fun getVisitorStatus() {
//        println("Статус посетителей:")
//        for (visitor in visitors) {
//            visitor.getVisitorStatus()
//        }
//
//    }
//
//    override fun makeAnimalSpeak() {
//        val scanner = Scanner(System.`in`)
//        println("Введите вид животного, которому нужно подать голос:")
//        val species = scanner.nextLine()
//
//        // Поиск животного во всех вольерах
//        var animalFound = false
//        for (enclosure in enclosures) {
//            if (enclosure.hasAnimal(species)) {
//                val animal = enclosure.getAnimal(species)
//                animal?.makeSound()
//                animalFound = true
//                break
//            }
//        }
//
//        if (!animalFound) {
//            println("Животное с видом $species не найдено в зоопарке.")
//        }
//    }
//
//
//    override fun checkAnimalStatus() {
//        val scanner = Scanner(System.`in`)
//        println("Введите вид животного, уровень сытости которого вы хотите узнать:")
//        val species = scanner.nextLine().toLowerCase()
//
//        // Поиск животного заданного вида во всех вольерах
//        var animalFound = false
//        for (enclosure in enclosures) {
//            if (enclosure.hasAnimal(species)) {
//                val animal = enclosure.getAnimal(species)
//                if (animal != null) {
//                    println("Уровень сытости ${animal.species}: ${animal.hungerLevel}")
//                    animalFound = true
//                    break
//                }
//            }
//        }
//
//        if (!animalFound) {
//            println("Животное с видом $species не найдено в зоопарке.")
//        }
//    }
//    override fun buyFood() {
//        if (visitors.isNotEmpty()) {
//            var randomVisitor = (0..visitors.size - 1).random()
//            var randomEnclosure = (0..enclosures.size - 1).random()
//            visitors[randomVisitor].feedAnimalByVisitor(enclosures[randomEnclosure].openPartEnclosure)
//        }
//    }
//    override fun addSpecialVisitor(visitor: Visitor) {
//        visitors.add(visitor)
//    }
//
//    override fun addSpecialEmployee(employee: Employee) {
//        employees.add(employee)
//    }
//
//
//    override fun editInformation() {
//        val scanner = Scanner(System.`in`)
//        println("Выберите, кого вы хотите отредактировать:")
//        println("1. Посетитель")
//        println("2. Сотрудник")
//        val choice = scanner.nextInt()
//        scanner.nextLine()
//        when (choice) {
//            1 -> {
//                println("Введите имя посетителя, которого вы хотите отредактировать:")
//                val name = scanner.nextLine()
//                val visitor = visitors.find { it.name == name }
//                if (visitor != null) {
//                    println("Введите новое имя:")
//                    val newName = scanner.nextLine()
//                    visitor.changeName(newName)
//                    println("Имя посетителя успешно изменено на $newName.")
//                } else {
//                    println("Посетитель с именем $name не найден.")
//                }
//            }
//            2 -> {
//                println("Введите имя сотрудника, которого вы хотите отредактировать:")
//                val name = scanner.nextLine()
//                val employee = employees.find { it.name == name }
//                if (employee != null) {
//                    println("Введите новое имя сотрудника:")
//                    val newName = scanner.nextLine()
//                    println("Введите новую должность сотрудника:")
//                    val newPosition = scanner.nextLine()
//                    employee.changeName(newName)
//                    employee.changePosition(newPosition)
//                    println("Информация о сотруднике успешно изменена.")
//                } else {
//                    println("Сотрудник с именем $name не найден.")
//                }
//            }
//            else -> {
//                println("Неверный выбор. Попробуйте снова.")
//                editInformation()
//            }
//        }
//
//    }
//
//}