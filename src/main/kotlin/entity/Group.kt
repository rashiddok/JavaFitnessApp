package entity

import entity.converter.ConverterYearMonthToLocalDate
import java.time.YearMonth
import java.time.LocalTime;
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

    @Column(name = "time", nullable = false)
    val time: LocalTime,

    @Column(name = "endTime", nullable = false)
    val endTime: LocalTime,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "group", fetch = FetchType.EAGER)
    val workout: MutableList<Workout>
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
        //TODO это плохо, т.к. не нужно создавать группы одинакового типа в один месяц. Необходимо ограничить создание дублирующей группы
        if(id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = workoutType.hashCode()
        result = 31 * result + period.hashCode()
        return result
    }
}