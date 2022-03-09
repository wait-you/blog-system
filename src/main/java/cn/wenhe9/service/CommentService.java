package cn.wenhe9.service;

import cn.wenhe9.model.pojo.Comment;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评论业务处理
 * @author dujinliang
 */
public interface CommentService {
    /**
     * 根据文章id分页获取评论
     * @param aid 文章id
     * @param page 当前页数
     * @param count 一页显示的条数
     * @return 分页返回评论
     */
    PageInfo<Comment> getComments(Integer aid, int page, int count);

    void publishComment(Comment comment);
}
