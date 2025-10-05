package domain;

public class Status {
    private int statusId;
    private String name;

    // Constructors
    public Status() {
    }

    public Status(int statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    // Getters and Setters
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusId=" + statusId +
                ", name='" + name + '\'' +
                '}';
    }
}
