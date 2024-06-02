interface Zoo {
    fun deleteEmployee()
    fun removeEmployee(employee: Employee)
    fun removeVisitor(visitor: Visitor)
    fun getZooStatus()
    fun getEmployeeStatus()
    fun getVisitorStatus()
    fun makeAnimalSpeak()
    fun checkAnimalStatus()
    fun editInformation()
    fun deleteEnclosure()
    var isPaused: Boolean
    fun updateAnimalStatus()
    fun shuffleAnimals()
    fun checkEnclosureStatus()
    fun buyFood()
    fun removeAnimal()
    fun addSpecialVisitor(visitor: Visitor)
    fun addSpecialEmployee(employee: Employee)
    fun addEntity()
    fun removeEntity()

}
