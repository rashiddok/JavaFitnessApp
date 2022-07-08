package entity

import entity.converter.ConverterYearMonthToLocalDate
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.time.YearMonth
import javax.persistence.*

@Entity
@Table(name = "groups", uniqueConstraints = [UniqueConstraint(name = "uniqueConstraint", columnNames = [Group.columnNameWorkoutType])])
class Group(

    @Convert(converter = WorkoutType.Converter::class)
    @Column(name = columnNameWorkoutType, nullable = false)
    val workoutType: WorkoutType,

    @Column(name = "price", nullable = false)
    val price: Int,

    @Convert(converter = ConverterYearMonthToLocalDate::class)
    @Column(name = columnNamePeriod, nullable = false)
    val period: YearMonth,

    @OneToMany(mappedBy = "group")
    val workout: List<Workout>
) {
    @Column(name = "isActive")
    var isActive: Boolean = true

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    companion object{
        const val columnNameWorkoutType = "type_id"
        const val columnNamePeriod = "period"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Group

        if (workoutType != other.workoutType) return false
        if (period != other.period) return false

        return true
    }

    override fun hashCode(): Int {
        var result = workoutType.hashCode()
        result = 31 * result + period.hashCode()
        return result
    }
}