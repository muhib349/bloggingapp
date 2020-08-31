package com.sqh.bloggingapp.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;


@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "title", nullable = false)
    @NotBlank(message = "*Please provide title")
    @Length(min = 5, message = "*Your title must have at least 5 characters")
    private String title;
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Collection<Like> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Collection<Dislike> dislikes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Collection<Like> getLikes() {
        return likes;
    }

    public void setLikes(Collection<Like> likes) {
        this.likes = likes;
    }

    public Collection<Dislike> getDislikes() {
        return dislikes;
    }

    public void setDislikes(Collection<Dislike> dislikes) {
        this.dislikes = dislikes;
    }
}
