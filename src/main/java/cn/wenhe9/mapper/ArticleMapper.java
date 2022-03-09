package cn.wenhe9.mapper;

import cn.wenhe9.model.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作Article的持久层
 * @author dujinliang
 */
@Mapper
@Repository
public interface ArticleMapper {
    /**
     * 通过id查询文章
     * @param id 文章id
     * @return 文章
     */
    Article queryArticleById(Integer id);

    /**
     * 发表文章
     * @param article 编写好的文章
     * @return 获取自动生成的主键
     */
    Integer publishArticle(Article article);

    /**
     * 分页查询
     * @return 返回按顺寻查找到文章
     */
    List<Article> queryArticleWithPage();

    /**
     * 根据id删除文章
     * @param id 要删除的文章的id
     */
    void  deleteArticleById(Integer id);

    /**
     * 统计站点所有文章的数量
     * @return 站点所有文章的数量
     */
    Integer countArticle();

    /**
     * 根据id更新文章内容 文章标题 文章内容 修改时间 文章分类 文章标签 是否允许被评论 文章缩略图
     * @param article 更新的文章的内容,内含要更新的文章的id
     */
    void updateArticleById(Article article);
}
