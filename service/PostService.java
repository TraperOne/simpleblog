package myblog.simpleblog.service;

import myblog.simpleblog.model.entity.Comment;
import myblog.simpleblog.model.entity.Post;
import myblog.simpleblog.model.form.PostForm;
import myblog.simpleblog.repository.CommentRepository;
import myblog.simpleblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    PostRepository postRepository;
    CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Post createPost (PostForm postForm, String email){
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
        post.setCategory(postForm.getCategory());
        //automatyczne dodanie emaila zalogowanego usera
        post.setAuthor(email);
        return postRepository.save(post);
    }
    public List<Post> getPost(){
        return postRepository.findAllByCategoryLikeOrderByAddedDesc("%");
    }
    public Post getOnePost(Long id){
        return postRepository.getOne(id);
    }

    public void deleteOnePost(Long id){
        //pyszukanie posta po id
        Post deletePost = postRepository.getOne(id);
        //usuniecie posta
        postRepository.delete(deletePost);
    }
    public PostForm getPostToEdit (Long id){
        Post post = postRepository.getOne(id);
        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        postForm.setCategory(post.getCategory());
        return postForm;
    }
    public Post editPost(Long id, PostForm postForm){
        //obiekt do edycji
        Post editedPost = postRepository.getOne(id);
        //przepisanie pól z PostForm
        editedPost.setTitle(postForm.getTitle());
        editedPost.setContent(postForm.getContent());
        editedPost.setCategory(postForm.getCategory());
        return postRepository.save(editedPost);
    }
    public boolean addComment(Long id, Comment comment, String email){
        Optional<Post>postOptional = postRepository.findById(id);
        if(!postOptional.isPresent()){
            return false;
        }
        Post currentPost = postOptional.get();
        comment.setAuthor(email);
        comment.setPost(currentPost);
        currentPost.setComments(comment);
        postRepository.save(currentPost);
        return true;
    }
    //metoda do usuwania komentarza
    public void deleteOneComment(Long id_comment){
        Comment comment = commentRepository.getOne(id_comment);
        Post post = comment.getPost();
        post.getComments().remove(comment);
        postRepository.save(post);
        commentRepository.delete(comment);
    }
    //metoda zwracająca id posta usuwanego komentarza
    public Long getPostId(Long id_comment){
        return commentRepository.getOne(id_comment).getPost().getId();
    }
}
