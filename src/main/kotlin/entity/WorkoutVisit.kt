package entity;

import javax.persistence.*

@Entity
@Table(name = "workout_visits", uniqueConstraints = [UniqueConstraint(name = "unique_constraint", columnNames = [WorkoutVisit.COLUMN_NAME_CLIENT, WorkoutVisit.COLUMN_NAME_WORKOUT])])
class WorkoutVisit (

    @ManyToOne
    @JoinColumn(name = COLUMN_NAME_CLIENT)
    val client: Client,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_WORKOUT)
    val workout: Workout,

    @Convert(converter = VisitStatus.Converter::class)
    @Column(name = "status_id", nullable = false)
    var visitStatus: VisitStatus
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    companion object {
        const val COLUMN_NAME_CLIENT = "client_id"
        const val COLUMN_NAME_WORKOUT = "workout_id"
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorkoutVisit

        if (client != other.client) return false
        if (workout != other.workout) return false

        return true
    }

    override fun hashCode(): Int {
        var result = client.hashCode()
        result = 31 * result + workout.hashCode()
        return result
    }
}
