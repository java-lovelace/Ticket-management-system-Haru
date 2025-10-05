package domain;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private Ticket ticket;
    private User user;
    private String content;
    private Timestamp createdAt;

    // Constructors
    public Comment() {
    }

    public Comment(int commentId, Ticket ticket, User user, String content, Timestamp createdAt) {
        this.commentId = commentId;
        this.ticket = ticket;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", ticket=" + ticket +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
