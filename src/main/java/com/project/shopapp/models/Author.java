package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    Set<Book> bookSet;

    @ManyToMany(mappedBy = "followedAuthor", fetch = FetchType.EAGER)
    private Set<User> followers = new HashSet<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id != null && id.equals(author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Column(name = "bio")
    private String bio;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "a_status")
    private Integer status;

    public void addFollower(User user) {
        this.getFollowers().add(user);
        user.getFollowedAuthor().add(this);
    }

    public void deleteFollower(User user) {
        this.getFollowers().remove(user);
        user.getFollowedAuthor().remove(this);
    }


}
