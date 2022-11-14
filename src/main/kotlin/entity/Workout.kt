package entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(name = "workouts", uniqueConstraints = [UniqueConstraint(name = "unique_constraint", columnNames = [Workout.COLUMN_NAME_DATE, Workout.COLUMN_NAME_GROUP])])
class Workout (

    @Column(name = COLUMN_NAME_DATE, nullable = false)
    val date: LocalDateTime,

    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_GROUP, nullable = false)
    val group: Group
) {
    @OneToMany(mappedBy = "workout")
    val visits: MutableList<WorkoutVisit> = ArrayList()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    companion object {
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_GROUP = "group_id"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Workout

        if (date != other.date) return false
        if (group != other.group) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + group.hashCode()
        return result
    }
}