package myblog.simpleblog.repository;

import myblog.simpleblog.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    //lista rekordów z tabeli
    List<Post> findAll();
    //posortowana lista rekordów
    List<Post> findAllByCategoryLikeOrderByAddedDesc(String category);
    //zwraca posta po id
    Post findOneById(Long id);

    //zapytanie natvie SQL
//    @Query("SELECT p FROM post p ORDER BY p.added DESC")
//    List<Post> getOrderedPosts();
//
//    @Query("SELECT p FROM post p WHERE p.id =:id")
//    List<Post> myQuery(@Param("id"));

}
