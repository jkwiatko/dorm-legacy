package com.dorm.webapp.shared.data.entities;

import com.dorm.webapp.shared.data.entities.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Message extends BaseEntity {

    private User author;
    private List<User> addresses;

    private String title;
    private String content;

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToMany
    public List<User> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<User> addressee) {
        this.addresses = addressee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
