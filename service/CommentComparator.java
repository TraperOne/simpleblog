package myblog.simpleblog.service;

import myblog.simpleblog.model.entity.Comment;

import java.util.Comparator;

public class CommentComparator implements Comparator<Comment> {
    @Override
    public int compare(Comment comment1, Comment comment2){
        return (int)(comment2.getDateAdded().getTime() - comment1.getDateAdded().getTime());
    }
}
