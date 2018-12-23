package me.dbpj.service;

import me.dbpj.dao.*;

import me.dbpj.entity.Author;
import me.dbpj.entity.Conference;
import me.dbpj.entity.Paper;
import me.dbpj.entity.Publish;
import me.dbpj.utils.Pair;
import me.dbpj.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.security.auth.login.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class MysqlService {
    @Autowired
    PaperDao paperDao;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    ConferenceDao conferenceDao;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    FieldDao fieldDao;

    @Autowired
    InvolveDao involveDao;

    @Autowired
    PublishDao publishDao;

    String classDrive="com.mysql.jdbc.Driver";
    String jdbcUrl="jdbc:mysql://61.164.127.132:3306/test?useSSL=false";
    String jdbcUsername="root";
    String jdbcPassword="dbpj123456";


    public List<Paper> findPaper(Paper t)
    {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<Paper> example = Example.of(t ,matcher);
        List<Paper> e=paperDao.findAll(example);
        return e;
    }

    public List<Author> findAuthor(Author t)
    {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<Author> example = Example.of(t ,matcher);
        List<Author> e=authorDao.findAll(example);
        return e;
    }

    public Paper savePaper(Paper t)
    {
        return  paperDao.save(t);
    }
    public Author saveAuthor(Author t)
    {
        return  authorDao.save(t);
    }
    public Conference saveConference(Conference t)
    {
        return  conferenceDao.save(t);
    }

    public void deletePaper(Paper t)
    {
        paperDao.delete(t);
    }
    public void deleteAuthor(Author t)
    {
        authorDao.delete(t);
    }
    public void deleteConference(Conference t)
    {
        conferenceDao.delete(t);
    }

    public void deleteConferenceByName(String name)
    {
        conferenceDao.deleteByCName(name);
    }

    public Conference findConferenceByName(String name)
    {
        return conferenceDao.findByCName(name);
    }

    public List<Paper> findPaperByTwoAuthorInOneField(String authorName1, String authorName2){

        List<Paper> papers=new ArrayList<>();
        String sql="SELECT P1.* from author as X " +
                "join publish AS A on X.author_id=A.author_id " +
                "join paper as P1 on P1.paper_id=A.paper_id " +
                "join involve as i1 on P1.paper_id=i1.paper_id " +
                "join field as f1 on f1.field_id=i1.field_id " +
                "join publish as B on B.paper_id=A.paper_id " +
                "join author as Y on Y.author_id=B.author_id " +
                //"WHERE field_name=? " +
                "WHERE X.author_id=? " +
                "and Y.author_id=? ";
        try {
            String classDrive="com.mysql.jdbc.Driver";
            String jdbcUrl="jdbc:mysql://61.164.127.132:3306/test?useSSL=false";
            String jdbcUsername="root";
            String jdbcPassword="dbpj123456";
            Class.forName(classDrive);
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println("OPEN CON");
            //String sql = "select authorId from author where authorName=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
          //  pstmt.setString(1, fieldName);
            pstmt.setString(1, authorName2);
            pstmt.setString(2, authorName1);
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                Paper p= new Paper();
                p.setId(rs.getInt("paper_id"));
                p.setPAbstract(rs.getString("paper_abstract"));
                p.setPCitation(rs.getInt("paper_citation"));
                p.setPTitle(rs.getString("paper_title"));
                p.setPYear(rs.getInt("publish_year"));
                p.setConference(conferenceDao.findById(rs.getInt("conference_id")));
                papers.add(p);
                //System.out.println(p.getPTitle());
            }
            con.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
        return  papers;
    }

    public List<Author> findAuthorByCoNum(String authorName,Integer topK)
    {
        List<Author> authors=new ArrayList<>();
        String sql="select Y.author_id,count(*) as num from author as X " +
                "join publish as A on X.author_id=A.author_id " +
                "join publish as B on B.paper_id=A.paper_id " +
                "join author as Y on Y.author_id=B.author_id " +
                "WHERE X.author_name= ? " +
                "GROUP BY Y.author_id  " +
                "ORDER BY count(*) DESC " +
                "LIMIT ?";
        try {
            Class.forName(classDrive);
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println("OPEN CON");
            //String sql = "select authorId from author where authorName=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, authorName);
            pstmt.setInt(2, topK);
            System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                Author p= new Author();
                p=authorDao.findByAuthorId(rs.getInt("author_id"));
                authors.add(p);
                //System.out.println(p.getPTitle());
            }
            con.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
        return  authors;
    }


    public List<Paper> findPaperByCitation(Integer topK)
    {
        List<Paper> papers=new ArrayList<>();
        String sql="select * from paper ORDER BY paper_citation DESC LIMIT ?";
        try {
            Class.forName(classDrive);
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println("OPEN CON");
            //String sql = "select authorId from author where authorName=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
           // pstmt.setString(1, authorName);
            pstmt.setInt(1, topK);
            System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                Paper p= new Paper();
                p=paperDao.findPaperById(rs.getInt("paper_id"));
                papers.add(p);
                //System.out.println(p.getPTitle());
            }
            con.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
        return  papers;
    }

    public Map<String,Integer> proportion()
    {
        //List<Integer> nums= new ArrayList<>();
        //List<> papers=new ArrayList<>();
        Map<String,Integer> mp=new TreeMap<>();
        String sql=" select COUNT(pX.paper_id) AS num,cX.conference_name from paper as pX JOIN conference as cX WHERE pX.conference_id=cX.conference_id GROUP BY cX.conference_name\n";
        try {
            Class.forName(classDrive);
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println("OPEN CON");
            //String sql = "select authorId from author where authorName=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            // pstmt.setString(1, authorName);
            System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                Paper p= new Paper();
                Integer num=(rs.getInt("num"));
                String cf=(rs.getString("conference_name"));
                mp.put(cf,num);

                //papers.add(p);
                //System.out.println(p.getPTitle());
            }
            con.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
        return  mp;
    }

    public  List<Pair<String,Integer>> mostAuthor(Integer topK)
    {
        List<Pair<String,Integer>> nums=new ArrayList<>();
        //List<> papers=new ArrayList<>();
        Map<Integer,String> mp=new TreeMap<>();
        String sql="select count(pX.paper_id) as num,aX.author_name as aname from publish as "+
        " pubX JOIN author as aX JOIN paper as pX WHERE pubX.author_id = aX.author_id AND pX.paper_id=pubX.paper_id GROUP BY aX.author_id ORDER BY num DESC limit ?";
        try {
            Class.forName(classDrive);
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println("OPEN CON");
            //String sql = "select authorId from author where authorName=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
             pstmt.setInt(1, topK);
            System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                Paper p= new Paper();
                Integer num=(rs.getInt("num"));
                String author_name=(rs.getString("aname"));
                mp.put(num,author_name);
                Pair<String,Integer> pair=new Pair<>();
                pair.setFirst(author_name);
                pair.setSecond(num);
                nums.add(pair);
            }
            con.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
        return  nums;
    }

    public  List<Pair<String,Integer>> mostDepart(Integer topK)
    {
        //List<> papers=new ArrayList<>();
        List<Pair<String,Integer>> nums=new ArrayList<>();
        Map<Integer,String> mp=new TreeMap<>();
        String sql="select count(pX.paper_id) as num,dX.department_name as dname from publish as "+
        " pubX JOIN department as dX JOIN paper as pX WHERE pubX.department_id = dX.department_id AND pX.paper_id=pubX.paper_id GROUP BY dX.department_id ORDER BY num DESC limit ?";

        try {
            Class.forName(classDrive);
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println("OPEN CON");
            //String sql = "select authorId from author where authorName=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, topK);
            System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                Paper p= new Paper();
                Integer num=(rs.getInt("num"));
                String depart_name=(rs.getString("dname"));
                mp.put(num,depart_name);
                Pair<String,Integer> pair=new Pair<>();
                pair.setFirst(depart_name);
                pair.setSecond(num);
                nums.add(pair);
                //papers.add(p);
                //System.out.println(p.getPTitle());
            }
            con.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
        return  nums;
    }

    public List<Pair<Integer,Pair<String,String>>> coNumDesc(Integer topK)
    {
        //List<> papers=new ArrayList<>();
        List<Pair<Integer,Pair<String,String>>> nums=new ArrayList<>();
        Map<Integer,Map<String,String>> mp=new TreeMap<>();
        String sql="SELECT count(pubX.publish_id) as num,aX.author_name as a1,aY.author_name as a2 FROM publish AS pubX JOIN publish as pubY JOIN author as aX JOIN author as aY WHERE pubX.paper_id=pubY.paper_id and aX.author_id=pubX.author_id and aY.author_id=pubY.author_id  and pubX.author_id!=pubY.author_id GROUP BY aX.author_id,aY.author_id ORDER BY num DESC LIMIT ?";
        try {
            Class.forName(classDrive);
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println("OPEN CON");
            //String sql = "select authorId from author where authorName=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, topK);
            System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                Paper p= new Paper();
                Integer num=(rs.getInt("num"));
                String a1=(rs.getString("a1"));
                String a2=(rs.getString("a2"));
                Map<String,String> tmp=new TreeMap<>();
                tmp.put(a1,a2);
                mp.put(num,tmp);
                Pair<String,String> pair=new Pair<>();
                pair.setFirst(a1);
                pair.setSecond(a2);
                Pair<Integer,Pair<String,String>> pp=new Pair<>();
                pp.setFirst(num);
                pp.setSecond(pair);
                nums.add(pp);
                //papers.add(p);
                //System.out.println(p.getPTitle());
            }
            con.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
        return  nums;
    }

    //select paper_title,paper_citation from paper ORDER BY paper_citation DESC LIMIT 20
    //select COUNT(pX.paper_id),cX.conference_name from paper as pX JOIN conference as cX WHERE pX.conference_id=cX.conference_id GROUP BY cX.conference_name
    //select count(pX.paper_id) as num,aX.author_name from publish as
    //pubX JOIN author as aX JOIN paper as pX WHERE pubX.author_id = aX.author_id AND pX.paper_id=pubX.paper_id GROUP BY aX.author_id ORDER BY num DESC
    //select count(pX.paper_id) as num,dX.department_name from publish as
    //pubX JOIN department as dX JOIN paper as pX WHERE pubX.department_id = dX.department_id AND pX.paper_id=pubX.paper_id GROUP BY dX.department_id ORDER BY num DESC
}
