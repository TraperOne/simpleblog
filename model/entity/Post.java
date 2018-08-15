package myblog.simpleblog.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 2000)
    private String content;
    private String author;
    private String category;
    private Date added = new Date();
    //relacja 1:n z punktu widzenia tabeli post
    //FetchType.EAGER - zachłanny - utworzenie obiektu klasy post od razu pobiera do listy relacje z comment
    //FetchType.LEAZY - leniwy - relacje nie są zutomatycznie pobierane
    //CascadeType.ALL - dopuszcza wszystkie operacje niezależne od powiązań (DELETE, UPDATE, itp)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post(List<Comment> comments) {
        this.comments = comments;
    }

    public Post(String title, String content, String author, String category, Date added) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.added = added;
    }

    @Override
    public String toString() {
        return id+";"+title+";"+content +";"+author+";"+category+";"+added;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(Comment comment) {
        this.comments.add(comment);
    }

    public Post() {  }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }
}
