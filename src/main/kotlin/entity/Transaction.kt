package entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "transactions")
class Transaction(

    @Convert(converter = TransactionType.Converter::class)
    @Column(name = "type_id", nullable = false)
    val type: TransactionType,

    @Column(name = "amount", nullable = false)
    val amount: Int,

    @Column(name = "comment", nullable = false)
    val comment: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false, updatable = false)
    val client: Client
){
    @Column(name = "transaction_date", nullable = false)
    val date: LocalDateTime = LocalDateTime.now()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        if (type != other.type) return false
        if (amount != other.amount) return false
        if (comment != other.comment) return false
        if (client != other.client) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + amount
        result = 31 * result + comment.hashCode()
        result = 31 * result + client.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}
