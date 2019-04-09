package org.wlgzs.recruit.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.recruit.base.BaseController;
import org.wlgzs.recruit.domain.Interview;
import org.wlgzs.recruit.domain.Question;
import org.wlgzs.recruit.domain.ScoreItem;
import org.wlgzs.recruit.domain.Student;
import org.wlgzs.recruit.util.result.Result;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author 阿杰
 * Create 2018-08-08 14:53
 * Description:
 */
@RestController
@RequestMapping("/interview")
public class InterviewController extends BaseController {

    /**
     * Description 加入面试
     * Param [studentId]
     **/
    @RequestMapping("/joinInterview")
    public Result joinInterview(int studentId, HttpSession session) {
        return studentService.joinInterview(studentId, session);
    }

    /**
     * Description 退出面试
     * Param [studentId]
     **/
    @RequestMapping("/exitInterview")
    public Result exitInterview(int studentId) {
        return studentService.exitInterview(studentId);
    }

    /**
     * Description 面试
     * Param [studentId]
     **/
    @RequestMapping("/interview")
    public ModelAndView interview(Model model) {
        List<ScoreItem> scoreItems = scoreItemService.findAllScoreItem();
        model.addAttribute("scoreItems", scoreItems);
        List<Question> questionList = questionService.findAllQuestion();
        model.addAttribute("questionList", questionList);
        Student student = studentService.findInterviewStudent();
        if (student.getStudentId() != 0) {
            if ("笔试通过".equals(student.getStatus())) {
                model.addAttribute("msg", "该学生正在进行一面");
            }
            if ("一面通过".equals(student.getStatus())) {
                model.addAttribute("msg", "该学生正在进行二面");
            }
            if ("二面通过".equals(student.getStatus())) {
                model.addAttribute("msg", "该学生正在进行三面");
            }
            List<String> strings = new ArrayList<>();
            if (student.getWrittenTestImg() != null && student.getWrittenTestImg().contains(",")) {
                String[] img = student.getWrittenTestImg().split(",");
                strings = Arrays.asList(img);
            }
            model.addAttribute("img", strings);
        } else {
            model.addAttribute("msg", "现在没有面试的学生");
        }
        model.addAttribute("student", student);
        return new ModelAndView("Being-interviewed");
    }

    /**
     * Description 提交面试打分
     * Param [studentId, detail]
     **/
    @RequestMapping("/submitInterview")
    public ModelAndView submitInterview(Model model,HttpSession session, String detail) {
        if(session.getAttribute("userName")==null || detail==null){
            model.addAttribute("msg","未知错误");
            return new ModelAndView("redirect:/interview/submitInterview");
        }
        String interviewer = (String) session.getAttribute("userName");
        Student student = studentService.findInterviewStudent();
        interviewService.submitInterview(student, detail, interviewer);
        return new ModelAndView("redirect:/student/findAllStudent");
    }

    /**
     * Description 查询全部面试结果
     * Param [pageNum, pageSize]
     **/
    @RequestMapping("/allInterviewResult")
    public ModelAndView allInterviewResult(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Page<Student> pageInfo = studentService.allInterviewResult(pageNum, pageSize);
        model.addAttribute("current", pageInfo.getCurrent());  //当前页数
        model.addAttribute("pages", pageInfo.getPages());      //总页数
        model.addAttribute("students", pageInfo.getRecords());    //集合
        model.addAttribute("msg", "查询成功");
        return new ModelAndView("result");
    }

    /**
     * Description 查询一面结果
     * Param [model, pageNum, pageSize]
     **/
    @RequestMapping("/OneInterviewList")
    public ModelAndView oneInterviewList(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Page<Student> pageInfo = studentService.OneInterviewList(pageNum, pageSize);
        model.addAttribute("current", pageInfo.getCurrent());  //当前页数
        model.addAttribute("pages", pageInfo.getPages());      //总页数
        model.addAttribute("students", pageInfo.getRecords());    //集合
        model.addAttribute("msg", "查询成功");
        return new ModelAndView("result1");
    }

    /**
     * Description 查询二面结果
     * Param [model, pageNum, pageSize]
     **/
    @RequestMapping("/TwoInterviewList")
    public ModelAndView twoInterviewList(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Page<Student> pageInfo = studentService.TwoInterviewList(pageNum, pageSize);
        model.addAttribute("current", pageInfo.getCurrent());  //当前页数
        model.addAttribute("pages", pageInfo.getPages());      //总页数
        model.addAttribute("students", pageInfo.getRecords());    //集合
        model.addAttribute("msg", "查询成功");
        return new ModelAndView("result2");
    }

    /**
     * Description 查询三面结果
     * Param [model, pageNum, pageSize]
     **/
    @RequestMapping("/ThreeInterviewList")
    public ModelAndView threeInterviewList(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Page<Student> pageInfo = studentService.ThreeInterviewList(pageNum, pageSize);
        model.addAttribute("current", pageInfo.getCurrent());  //当前页数
        model.addAttribute("pages", pageInfo.getPages());      //总页数
        model.addAttribute("students", pageInfo.getRecords());    //集合
        model.addAttribute("msg", "查询成功");
        return new ModelAndView("result3");
    }
    /**
     * Description 取消当前面试成绩
     * Param [studentId]
     **/
    @RequestMapping("/cancelResult")
    public Result cancelResult(int studentId){
        return studentService.cancelResult(studentId);
    }
    /**
     * Description 查看面试详情
     * Param [studentId, model]
     **/
    @RequestMapping("/interviewResult")
    public ModelAndView interviewResult(int studentId, Model model) {
        Student student = studentService.findStudentById(studentId);
        model.addAttribute("student", student);
        /*
         * Description 第一次面试成绩
         **/
        List<Interview> oneInterviews = interviewService.oneInterviewResult(studentId);
        Map<String, List> mapOne = new HashMap<>(oneInterviews.size());
        if (oneInterviews.size() != 0) {
            String strOne = oneInterviews.get(0).getDetail();//行为举止，言谈举止，个人素养-7,8,4
            strOne = strOne.substring(0, strOne.indexOf("-"));
            String[] scoreItemsOne = strOne.split(",");
            List<String> scoreItemOne = new ArrayList<>(Arrays.asList(scoreItemsOne));
            model.addAttribute("scoreItemOne", scoreItemOne);//打分项集合
            for (int i = 0; i < oneInterviews.size(); i++) {
                String str1 = oneInterviews.get(i).getDetail();//行为举止，言谈举止，个人素养-7,8,4
                str1 = str1.substring(str1.indexOf("-") + 1, str1.length());
                String[] scores1 = str1.split(",");
                float score = 0;
                for (String aScores1 : scores1) {
                    score += Float.parseFloat(aScores1);
                }
                score = score/scores1.length*10;
                List<String> score1 = new ArrayList<>();
                score1.add(oneInterviews.get(i).getInterviewer());
                score1.addAll(Arrays.asList(scores1));
                score1.add((float)(Math.round(score*100))/100+"");
                mapOne.put(i + "", score1);
            }
        }
        System.out.println(mapOne + "-------------");
        model.addAttribute("mapOne", mapOne);//评委及分数集合
        /*
         * Description 第二次面试成绩
         **/
        List<Interview> twoInterviews = interviewService.twoInterviewResult(studentId);
        Map<String, List> mapTwo = new HashMap<>(twoInterviews.size());
        if (twoInterviews.size() != 0) {
            String strTwo = twoInterviews.get(0).getDetail();//行为举止，言谈举止，个人素养-7,8,6
            strTwo = strTwo.substring(0, strTwo.indexOf("-"));
            String[] scoreItemsTwo = strTwo.split(",");
            List<String> scoreItemTwo = new ArrayList<>(Arrays.asList(scoreItemsTwo));
            model.addAttribute("scoreItemTwo", scoreItemTwo);//打分项集合
            for (int i = 0; i < twoInterviews.size(); i++) {
                String str2 = twoInterviews.get(i).getDetail();//行为举止，言谈举止，个人素养-7,8,4
                str2 = str2.substring(str2.indexOf("-") + 1, str2.length());
                String[] scores2 = str2.split(",");
                float score = 0;
                for (String aScores : scores2) {
                    score += Float.parseFloat(aScores);
                }
                score = score/scores2.length*10;
                List<String> score2 = new ArrayList<>();
                score2.add(twoInterviews.get(i).getInterviewer());
                score2.addAll(Arrays.asList(scores2));
                score2.add((float)(Math.round(score*100))/100+"");
                mapTwo.put(i + "", score2);
                System.out.println("第二次面试成绩");
            }
        }
        System.out.println(mapTwo + "-------------");
        model.addAttribute("mapTwo", mapTwo);//评委及分数集合
        /*
         * Description 第三次面试成绩
         **/
        List<Interview> threeInterviews = interviewService.threeInterviewResult(studentId);
        Map<String, List> mapThree = new HashMap<>(threeInterviews.size());
        if (threeInterviews.size() != 0) {
            String strThree = threeInterviews.get(0).getDetail();//行为举止，言谈举止，个人素养-7,8,6
            strThree = strThree.substring(0, strThree.indexOf("-"));
            String[] scoreItemsThree = strThree.split(",");
            List<String> scoreItemThree = new ArrayList<>(Arrays.asList(scoreItemsThree));
            model.addAttribute("scoreItemThree", scoreItemThree);//打分项集合
            for (int i = 0; i < threeInterviews.size(); i++) {
                String str3 = threeInterviews.get(i).getDetail();//行为举止，言谈举止，个人素养-7,8,4
                str3 = str3.substring(str3.indexOf("-") + 1, str3.length());
                String[] scores3 = str3.split(",");
                float score = 0;
                for (String aScores : scores3) {
                    score += Float.parseFloat(aScores);
                }
                score = score/scores3.length*10;
                List<String> score3 = new ArrayList<>();
                score3.add(threeInterviews.get(i).getInterviewer());
                score3.addAll(Arrays.asList(scores3));
                score3.add((float)(Math.round(score*100))/100+"");
                mapThree.put(i + "", score3);
                System.out.println("第三次面试成绩");
            }
        }
        System.out.println(mapThree + "-------------");
        model.addAttribute("mapThree", mapThree);//评委及分数集合
        return new ModelAndView("perinfor");
    }

    /**
     * Description 面试通过
     * Param [studentId]
     **/
    @RequestMapping("/passInterview")
    public Result passInterview(int studentId) {
        return studentService.passInterview(studentId);
    }

    /**
     * Description 面试失败
     * Param [studentId]
     **/
    @RequestMapping("/outInterview")
    public Result outInterview(int studentId) {
        return studentService.outInterview(studentId);
    }
}
