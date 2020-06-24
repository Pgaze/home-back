package fr.janvier.home.utils;

import fr.janvier.home.beans.InfluxQueryResult;
import org.influxdb.dto.QueryResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

public class InfluxUtils {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;


    public static InfluxQueryResult getFieldInResultOneLine(QueryResult qr, String fieldName){
        InfluxQueryResult r = new InfluxQueryResult();
        AtomicBoolean isOk = new AtomicBoolean();
        isOk.set(false);
        if(qr != null && qr.getResults()!=null){
            qr.getResults().stream().forEach(result -> {
                if(!result.hasError() && result.getSeries() !=null){
                    result.getSeries().stream().forEach(series -> {
                        if(series.getValues().size() == 1){
                            r.setTime(LocalDateTime.from(dtf.parse((String)series.getValues().get(0).get(series.getColumns().indexOf("time")))));
                            r.setValue((Double)series.getValues().get(0).get(series.getColumns().indexOf(fieldName)));
                            isOk.set(true);
                        }
                    });
                }
            });
        }
        return isOk.get()?r:new InfluxQueryResult();
    }

}
