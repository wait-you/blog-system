package cn.wenhe9.mapper;

import cn.wenhe9.model.pojo.Article;
import cn.wenhe9.model.pojo.Statistic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作Statistic的持久层
 * @author dujinliang
 */
@Mapper
@Repository
public interface StatisticMapper {
    /**
     * 初始化文章对应的统计信息
     * @param article 内含articleId
     */
    void addStatistic(Article article);

    /**
     * 根据文章id查询点击量和评论量相关信息
     * @param articleId articleId
     * @return 返回点击量和评论量相关信息
     */
    Statistic queryStatisticByArticleId(Integer articleId);

    /**
     * 根据文章id更新点击量
     * @param statistic 内含文章id
     */
    void updateArticleHitsWithId(Statistic statistic);

    /**
     * 根据文章id更新评论量
     * @param statistic 内含文章id
     */
    void updateArticleCommentsWithId(Statistic statistic);

    /**
     * 根据文章id删除统计数据
     * @param aid 文章id
     */
    void deleteArticleHitsWithId(int aid);

    /**
     * 统计文章热度信息
     * @return 返回所有点击量不为0的热度信息
     */
    List<Statistic> getStatistic();

    /**
     * 统计文章总访问量
     * @return 返回总访问量
     */
    long getTotalVisit();

    /**
     * 统计所有的评论量
     * @return 返回总评论量
     */
    long getTotalComment();
}
