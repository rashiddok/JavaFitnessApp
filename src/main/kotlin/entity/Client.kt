package entity

import javax.persistence.*

@Entity
@Table(name = "clients", uniqueConstraints = [UniqueConstraint(name = "uniqueConstraint", columnNames = [Client.columnNameFirstName, Client.columnNameLastName, Client.columnNamePatronymic])])
class Client(

    @Column(name = columnNameFirstName, nullable = false)
    var firstName: String?,

    @Column(name = columnNameLastName, nullable = false)
    var lastName: String?,

    @Column(name = columnNamePatronymic, nullable = false)
    var patronymic: String?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    companion object{
        const val columnNameFirstName = "first_name"
        const val columnNameLastName = "last_name"
        const val columnNamePatronymic = "patronymic"
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
        var result = firstName?.hashCode() ?: 0
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (patronymic?.hashCode() ?: 0)
        return result
    }
}