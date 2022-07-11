package entity

import javax.persistence.*

@Entity
@Table(name = "subscriptions", uniqueConstraints = [UniqueConstraint(name = "uniqueConstraint", columnNames = [Subscription.COLUMN_NAME_GROUP, Subscription.COLUMN_NAME_CLIENT])])
class Subscription (

    @ManyToOne
    @JoinColumn(name = COLUMN_NAME_CLIENT, nullable = false)
    val client: Client,

    @ManyToOne
    @JoinColumn(name = COLUMN_NAME_GROUP, nullable = false)
    val group: Group,

    @Column(name = "order_count", nullable = false)
    val orderCount: Int,

    @Column(name = "compensation_rate", nullable = false)
    val compensationRate: Int,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    companion object {
        const val COLUMN_NAME_GROUP = "group_id"
        const val COLUMN_NAME_CLIENT = "client_id"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Subscription

        if (client != other.client) return false
        if (group != other.group) return false

        return true
    }

    override fun hashCode(): Int {
        var result = client.hashCode()
        result = 31 * result + group.hashCode()
        return result
    }
}