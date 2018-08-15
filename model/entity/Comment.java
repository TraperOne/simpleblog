package myblog.simpleblog.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Comment {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String message;
    private String author;
    private Date dateAdded = new Date();
    //n:1 patrząc z tabeli comment
    //złączenie na podstawie id z tabeli post
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(String message, String author, Date dateAdded, Post post) {
        this.message = message;
        this.author = author;
        this.dateAdded = dateAdded;
        this.post = post;
    }

    public Comment() {   }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
