package cn.wenhe9.service;

import cn.wenhe9.model.pojo.Article;
import cn.wenhe9.model.pojo.Comment;
import cn.wenhe9.model.pojo.Statistic;
import cn.wenhe9.model.responseData.StaticticsBo;

import java.util.List;

/**
 * 博客站点业务处理
 * @author dujinliang
 */
public interface SiteService {
    /**
     * 最新收到的评论
     * @param limit 评论的数量
     * @return 返回最新的评论
     */
    List<Comment> recentComments(int limit);

    /**
     * 最新的文章
     * @param limit 文章的数量
     * @return 返回最新的文章
     */
    List<Article> recentArticles(int limit);

    /**
     * 获取后台统计数据
     * @return 返回后台统计数据
     */
    StaticticsBo getStatistics();

    /**
     * 更新某个文章的统计数据
     * @param article 要更新的文章
     */
    void updateStatistic(Article article);
}
