package cn.wenhe9.web.client;

import cn.wenhe9.model.pojo.Article;
import cn.wenhe9.model.pojo.Comment;
import cn.wenhe9.service.ArticleService;
import cn.wenhe9.service.CommentService;
import cn.wenhe9.service.SiteService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author dujinliang
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private SiteService siteService;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        return this.index(request, 1, 5);
    }

    @GetMapping("/page/{p}")
    public String index (HttpServletRequest request,
                         @PathVariable("p") int page,
                        @RequestParam(value = "count", defaultValue = "5") int count){
        PageInfo<Article> articles = articleService.queryArticleWithPage(page, count);
        List<Article> heartArticles = articleService.getHeartArticle();
        request.setAttribute("articles", articles);
        request.setAttribute("heartArticles", heartArticles);
        logger.info("分页获取文章信息:页码" + page + "，条数" + count);
        return  "client/index";
    }

    @GetMapping("/article/{id}")
    public String getArticleById(@PathVariable("id") Integer id, HttpServletRequest request){
        Article article = articleService.queryArticleById(id);

        if (article != null){
            //查询封装评论相关数据
            this.getArticleComments(article, request);

            //更新文章点击量
            siteService.updateStatistic(article);
            request.setAttribute("article", article);
            return "client/articleDetails";
        }else{
            logger.warn("查询文章详情结果为空，查询文章id: "+id);
            // 未找到对应文章页面，跳转到提示页
            return "comm/error_404";
        }
    }

    public void getArticleComments(Article article, HttpServletRequest request){
        if (article.getAllowComment()){
            //cp表示评论页码， commentPage
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            request.setAttribute("cp", cp);
            PageInfo<Comment> comments = commentService.getComments(article.getId(), Integer.parseInt(cp), 3);
            request.setAttribute("comments", comments);
        }
    }
}
