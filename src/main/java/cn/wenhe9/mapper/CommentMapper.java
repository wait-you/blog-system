package cn.wenhe9.mapper;

import cn.wenhe9.model.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author dujinliang
 * 2021/4/22
 */
@Mapper
@Repository
public interface CommentMapper {

    /**
     *根据id分页查询该文章的评论
     * @param aid 文章的id
     * @return 分页返回文章的评论
     */
    List<Comment> queryCommentsByIdWithPage(Integer aid);

    /**
     * 查询最新的几条评论
     * @return 最新的评论
     */
    List<Comment> queryNewComments();

    /**
     * 发表评论
     * @param comment 评论的内容
     */
    void publicComment(Comment comment);

    /**
     * 查询评论数量
     * @return 返回评论总数
     */
    Integer countComment();

    /**
     * 通过文章id删除评论
     * @param aid 文章id
     */
    void deleteCommentById(Integer aid);
}
