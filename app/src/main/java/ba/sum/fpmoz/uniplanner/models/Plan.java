package ba.sum.fpmoz.uniplanner.models;

public class Plan {
    private String planId;
    private String name;
    private String dayOfWeek;
    private String time;
    private String description;
    private int dayOfWeekId;
    private String userId;

    public Plan() {
    }

    public Plan(String planId, String userId, String name, int dayOfWeekId, String time, String description) {
        this.planId = planId;
        this.userId = userId;
        this.name = name;
        this.dayOfWeekId = dayOfWeekId;
        this.time = time;
        this.description = description;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}