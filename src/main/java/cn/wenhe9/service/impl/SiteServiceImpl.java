package cn.wenhe9.service.impl;

import cn.wenhe9.mapper.ArticleMapper;
import cn.wenhe9.mapper.CommentMapper;
import cn.wenhe9.mapper.StatisticMapper;
import cn.wenhe9.model.pojo.Article;
import cn.wenhe9.model.pojo.Comment;
import cn.wenhe9.model.pojo.Statistic;
import cn.wenhe9.model.responseData.StaticticsBo;
import cn.wenhe9.service.SiteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author dujinliang
 * 2021/4/22
 */
@Service
@Transactional
public class SiteServiceImpl implements SiteService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public List<Comment> recentComments(int limit) {
        PageHelper.startPage(1, limit > 10 || limit < 1 ? 10 : limit);
        List<Comment> comments = commentMapper.queryNewComments();
        return comments;
    }

    @Override
    public List<Article> recentArticles(int limit) {
        PageHelper.startPage(1, limit > 10 || limit < 0 ? 10 : limit);

        List<Article> articles = articleMapper.queryArticleWithPage();
        Statistic statistic;

        for (Article article : articles) {
            statistic = statisticMapper.queryStatisticByArticleId(article.getId());

            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }

        return articles;
    }

    @Override
    public StaticticsBo getStatistics() {
        StaticticsBo staticticsBo = new StaticticsBo();
        Integer articleNum = articleMapper.countArticle();
        Integer commentNum = commentMapper.countComment();

        staticticsBo.setArticles(articleNum);
        staticticsBo.setComments(commentNum);
        return staticticsBo;
    }

    @Override
    public void updateStatistic(Article article) {
        Statistic statistic = statisticMapper.queryStatisticByArticleId(article.getId());
        statistic.setHits(statistic.getHits()+1);
        statisticMapper.updateArticleHitsWithId(statistic);
    }
}
