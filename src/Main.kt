import java.util.*

val MAX_CAPACITY=5

fun main() {
    val zoo: Zoo = ZooImpl()
    val timer = Timer()
    var time =0
    val feeder1 = Employee("Соня", "волк")
    val feeder2 = Employee("Вика", "попугай")
    val feeder3 = Employee("Кристина", "обезьяна")

    val visitor = Visitor("Марат", 'м',50)

    zoo.addSpecialEmployee(feeder1)
    zoo.addSpecialEmployee(feeder2)
    zoo.addSpecialEmployee(feeder3)

    zoo.addSpecialVisitor(visitor)

    val timerTask = object : TimerTask() {

        override fun run() {
            if (!zoo.isPaused) {
                zoo.updateAnimalStatus()
                println("Тик таймера")
                time++
            }
            if (time%5==0) {
                zoo.shuffleAnimals()
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
        println("1. Добавить посетителя")
        println("2. Добавить животное в вольер")
        println("3. Удалить животное")
        println("4. Проверить статус зоопарка")
        println("5. Проверить статус сотрудников")
        println("6. Проверить статус посетителей")
        println("7. Приказать животному подать голос")
        println("8. Узнать статус животного")
        println("9. Нанять сотрудника")
        println("10. Уволить сотрудника")
        println("11. Редактировать информацию")
        println("12. Действие с вольерами")

        println("0. Выйти")

        choice = scanner.nextInt()
        scanner.nextLine()

        when (choice) {
            1 -> zoo.addVisitor()
            2 -> zoo.addAnimal()
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
            9 -> zoo.addEmployee()
            10 -> zoo.deleteEmployee()
            11 -> zoo.editInformation()
            12->{
                println("Что хотите сделать?")
                println("1-добавить вольер")
                println("2-удалить вольер")
                println("3-проверить статус вольера")
                val number = scanner.nextLine()
                if (number=="1") {
                    zoo.addEnclosure()
                }
                if (number=="3") {
                    zoo.checkEnclosureStatus()
                }
                else {
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