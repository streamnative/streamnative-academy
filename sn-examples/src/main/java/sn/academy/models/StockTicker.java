package sn.academy.models;

import java.sql.Date;
import java.util.Objects;

public class StockTicker {
    private Date date;
    private String name;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Double volume;

    public StockTicker() {}

    public StockTicker(Date date,
                       String name,
                       Double open,
                       Double close,
                       Double high,
                       Double low,
                       Double volume) {
        this.date = date;
        this.name = name;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockTicker that = (StockTicker) o;
        return Objects.equals(date, that.date) && Objects.equals(name, that.name) && Objects.equals(open, that.open) && Objects.equals(close, that.close) && Objects.equals(high, that.high) && Objects.equals(low, that.low) && Objects.equals(volume, that.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, name, open, close, high, low, volume);
    }

    @Override
    public String toString() {
        return "StockTicker{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", volume=" + volume +
                '}';
    }
}
