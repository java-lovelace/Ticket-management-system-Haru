package domain;

import java.sql.Timestamp;

public class Ticket {
    private int ticketId;
    private String title;
    private String description;
    private User reporter;
    private User assignee;
    private Category category;
    private Status status;
    private Timestamp created_at;
    private Timestamp updated_at;

    // Constructors
    public Ticket() {
    }

    public Ticket(int ticketId, String title, String description, User reporter, User assignee, Category category, Status status, Timestamp createdAt, Timestamp updatedAt) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        this.category = category;
        this.status = status;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", reporter=" + reporter +
                ", assignee=" + assignee +
                ", category=" + category +
                ", status=" + status +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
