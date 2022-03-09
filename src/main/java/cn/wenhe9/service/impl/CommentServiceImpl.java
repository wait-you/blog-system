package cn.wenhe9.service.impl;

import cn.wenhe9.mapper.CommentMapper;
import cn.wenhe9.mapper.StatisticMapper;
import cn.wenhe9.model.pojo.Comment;
import cn.wenhe9.model.pojo.Statistic;
import cn.wenhe9.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author dujinliang
 * 2021/4/21
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        PageHelper.startPage(page, count);

        List<Comment> comments = commentMapper.queryCommentsByIdWithPage(aid);

        PageInfo<Comment> pageInfo = new PageInfo<>(comments);

        return pageInfo;
    }

    @Override
    public void publishComment(Comment comment) {
        commentMapper.publicComment(comment);

        Statistic statistic = statisticMapper.queryStatisticByArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()+1);
        statisticMapper.updateArticleCommentsWithId(statistic);
    }
}
