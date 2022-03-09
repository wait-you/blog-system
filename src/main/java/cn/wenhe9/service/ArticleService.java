package cn.wenhe9.service;

import cn.wenhe9.model.pojo.Article;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dujinliang
 */
public interface ArticleService {
    /**
     * 分页查询article
     * @param page 当前页数
     * @param count 页面展示条数
     * @return 返回分页数据
     */
    PageInfo<Article> queryArticleWithPage(Integer page, Integer count);

    /**
     * 统计前十的热点文章
     * @return 返回热点文章集合
     */
    List<Article> getHeartArticle();

    /**
     * 根据文章id查询单个文章详情
     * @param id 文章id
     * @return 返回单个文章详情
     */
    Article queryArticleById(Integer id);

    /**
     * 发布文章
     * @param article 要发布的文章
     */
    void publicArticle(Article article);

    /**
     * 根据id修改文章
     * @param article 要更新的文章,内含文章id
     */
    void updateArticleById(Article article);

    /**
     * 根据id删除文章
     * @param id 要删除的文章，内含文章id
     */
    void deleteArticleById(Integer id);
}
