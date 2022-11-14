package entity.converter

import java.sql.Date
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class ConverterYearMonthToLocalDate : AttributeConverter<YearMonth, Date> {
    override fun convertToDatabaseColumn(attribute: YearMonth?): Date {
        return Date.valueOf(attribute!!.atDay(1))
    }

    override fun convertToEntityAttribute(dbData: Date?): YearMonth {
        return YearMonth.from(
            Instant
                .ofEpochMilli(dbData!!.time)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );
    }
}