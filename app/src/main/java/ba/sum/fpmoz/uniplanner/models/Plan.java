package ba.sum.fpmoz.uniplanner.models;

public class Plan {
    private String name;
    private String dayOfWeek;
    private String time;
    private String description;
    private int dayOfWeekId;

    public Plan() {
    }

    public Plan(String name, int dayOfWeekId, String time, String description) {
        this.name = name;
        this.dayOfWeekId = dayOfWeekId;
        this.time = time;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDayOfWeekId() {
        return dayOfWeekId;
    }

    public void setDayOfWeekId(int dayOfWeekId) {
        this.dayOfWeekId = dayOfWeekId;
    }
}