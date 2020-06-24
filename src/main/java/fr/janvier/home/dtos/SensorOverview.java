package fr.janvier.home.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.janvier.home.beans.Sensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SensorOverview {

    private static final DecimalFormat df= new DecimalFormat("#0.##");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM : HH:mm");

    private String name;
    private String location;
    private String description;
    private String measured;
    private String lastValue;
    private String lastValueTime;
    private String minValue;
    private String minValueTime;
    private String maxValue;
    private String maxValueTime;
    private String meanDay;
    private String meanDayPeriod;
    private String meanWeek;
    private String meanWeekPeriod;
    private String meanMonth;
    private String meanMonthPeriod;

    public SensorOverview(Sensor s){
        this.name = s.getName();
        this.location = s.getLocation();
        this.description = s.getDescription();
        this.measured = s.getMeasured();
    }

    public void setMinimumInformation(Double value, LocalDateTime time) {
        this.minValue = value!=null?df.format(value):"NaN";
        this.minValueTime = dtf.format(time);
    }

    public void setMaxInformation(Double value, LocalDateTime time) {
        this.maxValue = value!=null?df.format(value):"NaN";
        this.maxValueTime = dtf.format(time);
    }

    public void setLastInformation(Double value, LocalDateTime time) {
        this.lastValue = value!=null?df.format(value):"NaN";
        this.lastValueTime = dtf.format(time);
    }

    public void setMeanInformation(Double value, LocalDateTime time) {
        this.meanDay = value!=null?df.format(value):"NaN";
    }

    public void setMeanWeek(Double value) {
        this.meanWeek = value!=null?df.format(value):"NaN";
    }

    public void setMeanMonth(Double value) {
        this.meanMonth = value!=null?df.format(value):"NaN";
    }
}
