package me.dbpj.controller;

import me.dbpj.VO.ResultVO;
import me.dbpj.entity.Author;
import me.dbpj.entity.Conference;
import me.dbpj.entity.Paper;
import me.dbpj.service.MysqlService;
import me.dbpj.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@Controller
@RequestMapping("/SQL")
public class QueryController {


    @Autowired
    MysqlService mysqlService;
    @CrossOrigin
    @PostMapping("/author/cooperationBetween")
    private ResultVO queryAuCoPaper(
            @RequestParam("type")String type,
            @RequestParam("authorA")String author1,
            @RequestParam("authorB")String author2,
            @RequestParam("queryTime")String queryTime,
            @RequestParam("showTime")String showTime
                                ){
        Long time1=System.currentTimeMillis();
        List<Paper> papers= mysqlService.findPaperByTwoAuthorInOneField(author1,author2);
        System.out.println(papers.size());
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        Map<String,Object> ans=new TreeMap<>();
        List<Map<String,String>> ls=new ArrayList<>();
        Integer index=0;
        for(Paper p:papers)
        {
            Map<String,String> mp=new TreeMap<>();
            mp.put("paperTitle",p.getPTitle());
            mp.put("abstract",p.getPAbstract().substring(0,30));
            mp.put("conference",p.getConference().getCName());
            mp.put("publishYear",String.valueOf(p.getPYear()));
            mp.put("citation",String.valueOf(p.getPCitation()));
            mp.put("pages",String.valueOf(p.getPPage()));
            //ls.add(mp);
            ans.put((index++).toString(),mp);
        }
        //ans.put("SQLResult",ls);
        //ans.put("costTime",time);
        System.out.println(ResultVOUtil.success(ans,String.valueOf(time)).toString());
        return ResultVOUtil.success(ans,String.valueOf(time));
    }
    @CrossOrigin
    @PostMapping("/author/cooperateWith")
    private ResultVO queryCoWithPaper(
            @RequestParam("type")String type,
            @RequestParam("author")String author1,
            @RequestParam("queryTime")String queryTime,
            @RequestParam("showTime")String showTime
    ){
        Long time1=System.currentTimeMillis();
        List<Author> authors= mysqlService.findAuthorByCoNum(author1,Integer.valueOf(showTime));
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        Map<String,Object> ans=new TreeMap<>();
        List<Map<String,String>> ls=new ArrayList<>();
        Integer index=0;
        for(Author author:authors)
        {
            Map<String,String> mp=new TreeMap<>();
            mp.put("name",author.getAuthorName());
            ans.put((index++).toString(),mp);
            ls.add(mp);
        }
        //ans.put("SQLResult",ls);
        //ans.put("costTime",time);
        System.out.println(ResultVOUtil.success(ans,String.valueOf(time)).toString());
        return ResultVOUtil.success(ans,String.valueOf(time));
    }
    @PostMapping("/author/insert")
    private ResultVO saveAuthor(
            @RequestParam("type")String type,
            @RequestParam("authorName")String author1,
            @RequestParam("authorURL")String authorUrl
    ){
        Author author=new Author();
        author.setAuthorName(author1);
        author.setAuthorUrl(authorUrl);
        Long time1=System.currentTimeMillis();
        mysqlService.saveAuthor(author);
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        Map<String,Object> ans=new TreeMap<>();
        //ans.put("SQLTime",time);
        return ResultVOUtil.success(ans,String.valueOf(time));
    }
    @PostMapping("/author/delete")
    private ResultVO delAuthor(
            @RequestParam("type")String type,
            @RequestParam("authorName")String author1,
                                @RequestParam("authorUrl")String authorUrl
    ){
        Author author=new Author();
        author.setAuthorName(author1);
        author.setAuthorUrl(authorUrl);
        Long time1=System.currentTimeMillis();
        mysqlService.deleteAuthor(author);
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        Map<String,Object> ans=new TreeMap<>();
        //ans.put("SQLTime",time);
        return ResultVOUtil.success(ans,String.valueOf(time));
    }

    @PostMapping("/conference/insert")
    private ResultVO saveConference(
            @RequestParam("type")String type,
            @RequestParam("conference")String conference1
    ){
        Conference conference=new Conference();
        conference.setCName(conference1);
        Long time1=System.currentTimeMillis();
        mysqlService.saveConference(conference);
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        Map<String,Object> ans=new TreeMap<>();
        ans.put("SQLTime",time);
        return ResultVOUtil.success(ans);
    }
    @PostMapping("/conference/delete")
    private ResultVO deleteConference(
            @RequestParam("type")String type,
            @RequestParam("conference")String conference1
    ){
        Conference conference=new Conference();
        conference.setCName(conference1);
        Long time1=System.currentTimeMillis();
        Conference  cf=mysqlService.findConferenceByName(conference1);
        mysqlService.deleteConference(cf);
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        Map<String,Object> ans=new TreeMap<>();
        //ans.put("SQLTime",time);
        return ResultVOUtil.success(ans,String.valueOf(time));
    }

    @PostMapping("/paper/insert")
    private ResultVO savePaper(
            @RequestParam("type")String type,
            @RequestParam("authorName")String authorName,
            @RequestParam("authorUrl")String authorUrl,
            @RequestParam("paperTitle")String paperTitle,
            @RequestParam("conference")String conference,
            @RequestParam("publishYear")String publishYear,
            @RequestParam("citationTime")String citationTime,
            @RequestParam("Department")String department,
            @RequestParam("field")String field
    ){
        Long time1=System.currentTimeMillis();
        Conference cf = mysqlService.findConferenceByName(conference);
        Paper paper=new Paper();
        paper.setPTitle(paperTitle);
        paper.setConference(cf);
        paper.setPYear(Integer.valueOf(publishYear));
        paper.setPCitation(Integer.valueOf(citationTime));

        mysqlService.savePaper(paper);
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        Map<String,Object> ans=new TreeMap<>();
        //ans.put("SQLTime",time);
        return ResultVOUtil.success(ans,String.valueOf(time));
    }

    @PostMapping("/paper/delete")
    private ResultVO delPaper(@RequestParam("type")String type,
                              @RequestParam("authorName")String authorName,
                              @RequestParam("authorUrl")String authorUrl,
                              @RequestParam("paperTitle")String paperTitle,
                              @RequestParam("conference")String conference,
                              @RequestParam("publishYear")String publishYear,
                              @RequestParam("citationTime")String citationTime,
                              @RequestParam("Department")String department,
                              @RequestParam("field")String field
    ){
        Conference cf = mysqlService.findConferenceByName(conference);
        Paper paper=new Paper();
        paper.setPTitle(paperTitle);
        paper.setConference(cf);
        paper.setPYear(Integer.valueOf(publishYear));
        paper.setPCitation(Integer.valueOf(citationTime));
        Long time1=System.currentTimeMillis();
        mysqlService.deletePaper(paper);
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        Map<String,Object> ans=new TreeMap<>();
        return ResultVOUtil.success(ans,String.valueOf(time));
    }
    @CrossOrigin
    @PostMapping("/paper/simple-query")
    private ResultVO queryPaper(@RequestParam("type")String type,
                                @RequestParam("conference")String conferenceN,
                                @RequestParam("author")String authorN,
                                @RequestParam("field")String fieldN,
                                @RequestParam("publishYear")String publishYear,
                                @RequestParam("showTime")String showTime,
                                @RequestParam("paperTitle")String paperTitle,
                                @RequestParam("queryTime")String queryTime
                                ){
        System.out.println("paper query");
        Conference cf = mysqlService.findConferenceByName(conferenceN);
        Paper paper=new Paper();
        paper.setPTitle(paperTitle);
        paper.setConference(cf);
        if(publishYear!="")
        paper.setPYear(Integer.valueOf(publishYear));
        Map<String,Object> ans=new TreeMap<>();
        Long time1=System.currentTimeMillis();
        List<Paper> papers=mysqlService.findPaper(paper);
        System.out.println(papers.size());
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
       //List<Map<String,String>> ls=new ArrayList<>();
        Integer index=0;
        for(Paper p:papers)
        {
            Map<String,String> mp=new TreeMap<>();
            mp.put("paperTitle",p.getPTitle());
            mp.put("abstract",p.getPAbstract());
            mp.put("conference",p.getConference().getCName());
            mp.put("publishYear",String.valueOf(p.getPYear()));
            mp.put("citation",String.valueOf(p.getPCitation()));
            mp.put("pages",String.valueOf(p.getPPage()));
            //ls.add(mp);
            ans.put(String.valueOf(index++),mp);
        }
       // ans.put("SQLResult",ls);
       // ans.put("costTime",time);
        System.out.println(ans.toString());
        System.out.println(ResultVOUtil.success(ans).toString());
        return ResultVOUtil.success(ans,String.valueOf(time));
    }

    @PostMapping("/author/simple-query")
    private ResultVO queryAuthor(
            @RequestParam("type")String type,
            @RequestParam("conference")String conferenceN,
            @RequestParam("author")String authorN,
            @RequestParam("field")String fieldN,
            @RequestParam("publishYear")String publishYear,
            @RequestParam("showTime")String showTime,
            @RequestParam("paperTitle")String paperTitle,
            @RequestParam("queryTime")String queryTime

    ){
        Author author=new Author();
        author.setAuthorName(authorN);
        Map<String,Object> ans=new TreeMap<>();
        Long time1=System.currentTimeMillis();
        List<Author> authors=mysqlService.findAuthor(author);
        Long time2=System.currentTimeMillis();
        Long time=(time2-time1);
        List<Map<String,String>> ls=new ArrayList<>();
        Integer index=0;
        for(Author p:authors)
        {
            Map<String,String> mp=new TreeMap<>();
            mp.put("authorName",p.getAuthorName());
            mp.put("authorUrl",p.getAuthorUrl());
            ls.add(mp);
            ans.put((index++).toString(),mp);
        }
       // ans.put("SQLResult",ls);
        //ans.put("costTime",time);
        return ResultVOUtil.success(ans,String.valueOf(time));
    }
}
