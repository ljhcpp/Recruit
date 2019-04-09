package org.wlgzs.recruit.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.recruit.base.BaseController;
import org.wlgzs.recruit.domain.Question;
import org.wlgzs.recruit.util.result.Result;

/**
 * @author 阿杰
 * Create 2018-08-09 20:56
 * Description:
 */
@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {
    /**
     * Description 分页查询所有问题
     * Param [pageNum, pageSize]
     **/
    @RequestMapping("/findAll")
    public ModelAndView findAll(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        Page<Question> pageInfo = questionService.findAll(pageNum,pageSize);
        model.addAttribute("current",pageInfo.getCurrent());  //当前页数
        model.addAttribute("pages",pageInfo.getPages());      //总页数
        model.addAttribute("questions",pageInfo.getRecords());     //集合
        model.addAttribute("msg","查询成功");
        return new ModelAndView("testnode");
    }
    /**
     * Description 添加问题
     * Param [question]
     **/
    @RequestMapping("/addQuestion")
    public Result addQuestion(Question question){
        return questionService.addQuestion(question);
    }
    /**
     * Description 删除问题
     * Param [questionId]
     **/
    @RequestMapping("/deleteQuestion")
    public Result deleteQuestion(int questionId){
        return questionService.delete(questionId);
    }
    /**
     * Description 通过id查询问题
     * Param [scoreItemId]
     **/
    @RequestMapping("/findById")
    public Result findById(int scoreItemId){
        return questionService.findById(scoreItemId);
    }
    /**
     * Description 修改问题
     * Param [questionId, questionName]
     **/
    @RequestMapping("/update")
    public Result update(int questionId,String questionName){
        return questionService.update(questionId,questionName);
    }
}
