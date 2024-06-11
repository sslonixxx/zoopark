import java.util.*

abstract class BaseEntity {
    open val id: UUID = UUID.randomUUID()
}