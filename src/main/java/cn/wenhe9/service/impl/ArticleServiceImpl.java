package cn.wenhe9.service.impl;

import cn.wenhe9.mapper.ArticleMapper;
import cn.wenhe9.mapper.CommentMapper;
import cn.wenhe9.mapper.StatisticMapper;
import cn.wenhe9.model.pojo.Article;
import cn.wenhe9.model.pojo.Statistic;
import cn.wenhe9.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dujinliang
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageInfo<Article> queryArticleWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);

        List<Article> articles = articleMapper.queryArticleWithPage();
        Statistic statistic;

        for (Article article : articles) {
            statistic = statisticMapper.queryStatisticByArticleId(article.getId());

            article.setHits(statistic.getHits());
            article.setHits(statistic.getCommentsNum());
        }

        return new PageInfo<>(articles);
    }

    @Override
    public List<Article> getHeartArticle() {
        List<Statistic> statisticList = statisticMapper.getStatistic();
        List<Article> articles = new ArrayList<>();
        Article article;

        for (int i = 0; i < statisticList.size(); i++) {
            article = articleMapper.queryArticleById(statisticList.get(i).getArticleId());

            article.setHits(statisticList.get(i).getHits());
            article.setCommentsNum(statisticList.get(i).getCommentsNum());

            articles.add(article);

            if (i>=9) {
                break;
            }
        }
        return articles;
    }

    @Override
    public Article queryArticleById(Integer id) {
        Article article;
        Object o = redisTemplate.opsForValue().get("article" + id);
        if (o!=null){
            article = (Article) o;
        }else{
            article = articleMapper.queryArticleById(id);
            if (article!=null){
                redisTemplate.opsForValue().set("article"+id, article);
            }
        }
        return article;
    }

    @Override
    public void publicArticle(Article article) {
        //去除表情
        article.setContent(EmojiParser.parseToAliases(article.getContent()));

        article.setCreated(new Date());
        article.setHits(0);
        article.setCommentsNum(0);

        //插入文章，同时插入文章数据
        articleMapper.publishArticle(article);
        statisticMapper.addStatistic(article);
    }

    @Override
    public void updateArticleById(Article article) {
        //更新修改时间
        article.setModified(new Date());

        articleMapper.updateArticleById(article);

        //删除redis的缓存
        redisTemplate.delete("article" + article.getId());
    }

    @Override
    public void deleteArticleById(Integer id) {
        //根据文章id删除文章，同时删除redis中的缓存
        articleMapper.deleteArticleById(id);
        redisTemplate.delete("article" + id);

        //删除和文章相关的评论以及点击量等信息
        commentMapper.deleteCommentById(id);
        //名字写错了，内容是对的
        statisticMapper.deleteArticleHitsWithId(id);
    }
}
