package myblog.simpleblog.controller;

import myblog.simpleblog.model.entity.Comment;
import myblog.simpleblog.model.entity.Post;
import myblog.simpleblog.model.form.PostForm;
import myblog.simpleblog.service.CommentComparator;
import myblog.simpleblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Controller
public class PostController {

    PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/post/add")
    public String addPost(Model model){
        //obiekt do wprowadzania wartosci z html
        model.addAttribute("postForm", new PostForm());
        return "addPostForm";
    }

    @PostMapping("/post/add")
    public String addPost(@ModelAttribute @Valid PostForm postForm, BindingResult bindingResult, Authentication auth){
        if(bindingResult.hasErrors()){
            return "addPostForm";
        }
        //zapis poprzez PostService
        if (auth != null) {
            UserDetails principal = (UserDetails) auth.getPrincipal();
            postService.createPost(postForm, principal.getUsername());
        }else{
            postService.createPost(postForm, "unknown");
        }
        return "addPostForm";
    }
    @GetMapping("post/all")
    public String allPosts(Model model, Authentication auth){
        UserDetails principal = (UserDetails) auth.getPrincipal();
        Collection<GrantedAuthority> authList = (Collection<GrantedAuthority>) principal.getAuthorities();
        Boolean isAdmin = authList.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        List<Post> postsList = postService.getPost();
        model.addAttribute("postsList", postsList);
        model.addAttribute("isAdmin", isAdmin);
        return "allPostsPage";
    }
    @GetMapping("/post/{id}")
    public String onePost(@PathVariable Long id, Model model, Authentication auth){
        //zapytanie z service
        UserDetails principal = (UserDetails) auth.getPrincipal();
        Collection<GrantedAuthority> authList = (Collection<GrantedAuthority>) principal.getAuthorities();
        Boolean isAdmin = authList.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Post onePost = postService.getOnePost(id);
        //lista komentarzy
        List<Comment> commentList = onePost.getComments();
        //sortowanie listy
        CommentComparator commentComparator = new CommentComparator();
        commentList.sort(commentComparator);
        model.addAttribute("onePost",onePost);
        model.addAttribute("commentList",commentList);
        model.addAttribute("comment", new Comment());
        model.addAttribute("principal", principal);
        model.addAttribute("isAdmin", isAdmin);
        return "postPage";
    }
    @PostMapping("/post/{id}")
    public String onePost(@PathVariable Long id, @ModelAttribute @Valid Comment comment, BindingResult bindingResult, Authentication auth){
        if(bindingResult.hasErrors()){
            return "redirect:/post/"+id;
        }
        UserDetails principal = (UserDetails) auth.getPrincipal();
            //obsługa zapisu komentarza
        postService.addComment(id, comment, principal.getUsername());
        return "redirect:/post/"+id;
    }

    @GetMapping("/post/delete/{id}")
    public String deleteOnePost(@PathVariable Long id){
        //usuniecie posta przez PostService
        postService.deleteOnePost(id);
        return "redirect:/post/all";
    }

    @GetMapping("/post/edit/{id}")
    public String editOnePost(@PathVariable Long id, Model model){
        //z servisu przepisujemy pola z PostForm do Post
        PostForm postForm = postService.getPostToEdit(id);
        model.addAttribute("postForm", postForm);
        model.addAttribute("id", id);
        return "editPostForm";
    }
    @PostMapping("/post/edit/{id}")
    public String editOnePost(@PathVariable Long id, @ModelAttribute @Valid PostForm postForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "editPostForm";
        }
        //update przez service
        postService.editPost(id,postForm);
        return "redirect:/post/all";
    }

    @GetMapping("/post/deleteComment/{id_comment}")
    public String deleteComment(@PathVariable Long id_comment){
        Long post_id = postService.getPostId(id_comment);
        postService.deleteOneComment(id_comment);
        return "redirect:/post/"+post_id;
    }

    //API bloga
    @GetMapping("/api/all")
    public String postApi(Model model){
        List<Post> postsList = postService.getPost();
        model.addAttribute("postsList", postsList);
        return "posts";
    }
}
