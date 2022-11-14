package entity

import javax.persistence.*

@Entity
@Table(name = "clients", uniqueConstraints = [UniqueConstraint(name = "uniqueConstraint", columnNames = [Client.COLUMN_NAME_FIRST_NAME, Client.COLUMN_NAME_LAST_NAME, Client.COLUMN_NAME_PATRONYMIC])])
class Client(

    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false)
    var firstName: String,

    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false)
    var lastName: String,

    @Column(name = COLUMN_NAME_PATRONYMIC, nullable = false)
    var patronymic: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    companion object{
        const val COLUMN_NAME_FIRST_NAME = "first_name"
        const val COLUMN_NAME_LAST_NAME = "last_name"
        const val COLUMN_NAME_PATRONYMIC = "patronymic"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Client

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (patronymic != other.patronymic) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode() ?: 0
        result = 31 * result + (lastName.hashCode() ?: 0)
        result = 31 * result + (patronymic.hashCode() ?: 0)
        return result
    }
}