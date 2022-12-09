package entity

import javax.persistence.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(name = "uniqueConstraint", columnNames = [User.COLUMN_NAME_USERNAME, User.COLUMN_NAME_PASSWORD, User.COLUMN_NAME_IS_ADMIN])])
class User(

    @Column(name = COLUMN_NAME_USERNAME, nullable = false)
    var username: String,

    @Column(name = COLUMN_NAME_PASSWORD, nullable = false)
    var password: String,

    @Column(name = COLUMN_NAME_IS_ADMIN, nullable = false)
    var isAdmin: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    companion object{
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_IS_ADMIN = "isAdmin"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (username != other.username) return false
        if (password != other.password) return false
        if (isAdmin != other.isAdmin) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode() ?: 0
        result = 31 * result + (password.hashCode() ?: 0)
        result = 31 * result + (isAdmin.hashCode() ?: 0)
        return result
    }
}