import java.util.*

val MAX_CAPACITY=5

    fun main() {
        val zoo = ZooImpl<BaseEntity>()
        val timer = Timer()
        var time = 0
        val feeder1 = Employee("Соня", "волк")
        val feeder2 = Employee("Вика", "попугай")
        val feeder3 = Employee("Кристина", "обезьяна")

        val visitor = Visitor("Марат", 'м', 50)

        zoo.addNewEntity(feeder1)
        zoo.addNewEntity(feeder2)
        zoo.addNewEntity(feeder3)

        zoo.addNewEntity(visitor)

        val timerTask = object : TimerTask() {

            override fun run() {
                if (!zoo.isPaused) {
                    zoo.updateAnimalStatus()
                    println("Тик таймера")
                    time++
                }
                if (time % 5 == 0) {
                    zoo.shuffleAnimals()
                    zoo.buyFood()
                }
            }
        }
        timer.scheduleAtFixedRate(timerTask, 0, 4000)

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
            println("2. Удалить сущность")
            println("3. Проверить статус зоопарка")
            println("4. Проверить статус сотрудников")
            println("5. Проверить статус посетителей")
            println("6. Проверить статус вольера")
            println("7. Проверить статус животного")
            println("8. Приказать животному подать голос")
            println("9. Редактировать информацию")
            println("0. Выйти")

            choice = scanner.nextInt()
            scanner.nextLine()

            when (choice) {
                1 -> {
                    pauseTimer(zoo)
                    zoo.addEntity()
                    resumeTimer(zoo)
                }
                2 -> {
                    pauseTimer(zoo)
                    println("Что хотите удалить?")
                    zoo.removeEntity()
                    resumeTimer(zoo)}

                3 -> {
                    pauseTimer(zoo)
                    zoo.getZooStatus()
                    resumeTimer(zoo)}

                4 -> {
                    pauseTimer(zoo)
                    zoo.getEmployeeStatus()
                    resumeTimer(zoo)
                }

                5 -> {
                    pauseTimer(zoo)
                    zoo.getVisitorStatus()
                    resumeTimer(zoo)
                }
                6 -> {
                    pauseTimer(zoo)
                    zoo.checkEnclosureStatus()
                    resumeTimer(zoo)
                }
                7 -> {
                    pauseTimer(zoo)
                    zoo.checkAnimalStatus()
                    resumeTimer(zoo)
                }
                8 -> {
                    pauseTimer(zoo)
                    zoo.makeAnimalSpeak()
                    resumeTimer(zoo)
                }
                9 -> {
                    pauseTimer(zoo)
                    zoo.editInformation()
                    resumeTimer(zoo)
                }
                0 -> {
                    System.exit(0)
                }
                else -> {
                    println("Неверный выбор. Попробуйте снова.")
                }
            }

        } while (choice != 0)
    }
