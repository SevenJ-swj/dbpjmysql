package me.dbpj;

import me.dbpj.dao.PaperDao;
import me.dbpj.entity.Author;
import me.dbpj.entity.Conference;
import me.dbpj.entity.Paper;
import me.dbpj.service.MysqlService;
import me.dbpj.utils.Pair;
import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeTest {

    @Autowired
    private MysqlService mysqlService;

    @Test
    public void runInsertAndDel() {
        //limit为查询次数范围
        Integer limit=10000;
        FileWriter writerInsert;
        FileWriter writerDelete;
        try {
            writerInsert = new FileWriter("mysqlInsertTime.csv");
            writerDelete = new FileWriter("mysqlDeleteTime.csv");
            StringBuilder fisrtline=new StringBuilder(" ");
            for(Integer insertNum=limit;insertNum<=limit;insertNum*=10) fisrtline.append(","+insertNum.toString());
            writerInsert.write(fisrtline.toString()+"\n");
            writerDelete.write(fisrtline.toString()+"\n");
            //测试次数cnt
            for(Integer cnt=0;cnt<2;cnt++) {
                StringBuilder sbInsert = new StringBuilder();
                StringBuilder sbDelete = new StringBuilder();
                sbInsert.append(cnt.toString());
                sbDelete.append(cnt.toString());
                for(int insertNum=limit;insertNum<=limit;insertNum*=10) {
                    List<Paper> papers=new ArrayList<>();
                    Long t1 = System.currentTimeMillis();
                    for (Integer id = 0; id < insertNum; id++) {
                        Paper paper = new Paper();
                        String pAbstract = "Multilingual parallel text corpora provide a powerful means for propagating linguistic knowledge across languages. We present a model which jointly learns linguistic structure for each language while inducing links between them. Our model supports fully symmetrical knowledge transfer, utilizing any combination of supervised and unsupervised data across language barriers. The proposed non-parametric Bayesian model effectively combines cross-lingual alignment with target language predictions. This architecture is a potent alternative to projection methods which decompose these decisions into two separate stages. We apply this approach to the task of morphological segmentation, where the goal is to separate a word into its individual morphemes. When tested on a parallel corpus of Hebrew and Arabic, our joint bilingual model effectively incorporates all available evidence from both languages, yielding significant performance gains.";
                        pAbstract.replaceAll("\"", "").replaceAll("\\\\", "");
                        paper.setPTitle("testPaper"+id.toString());
                        paper.setPYear(2019);
                        paper.setPAbstract(pAbstract);
                        paper.setPCitation(100);
                        paper.setPPage(20);
                        Paper resP=mysqlService.savePaper(paper);
                        papers.add(resP);
                    }
                    Long t2 = System.currentTimeMillis();
                    sbInsert.append(","+String.valueOf(t2-t1));
                    //System.out.println("Excute:" + (t2 - t1));

                    t1 = System.currentTimeMillis();
                    for (Integer id = 0; id < insertNum; id++) {
                        Paper paper = papers.get(id);
                        mysqlService.deletePaper(paper);
                    }
                    t2 = System.currentTimeMillis();
                    sbDelete.append(","+String.valueOf(t2-t1));
                    //System.out.println("Excute:" + (t2 - t1));
                }
                writerInsert.write(sbInsert.toString()+"\n");
                writerDelete.write(sbDelete.toString()+"\n");
            }
            writerInsert.flush();
            writerInsert.close();
            writerDelete.flush();
            writerDelete.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @Test
    public void runCitation()
    {
        try {
            List<Paper> papers=new ArrayList<>();
            Integer topK=20;
            FileWriter writer= new FileWriter("mysqlPaperCitationTopK.csv");

            for(int cnt=0;cnt<10;cnt++){
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(cnt));
                Long t1=System.currentTimeMillis();
                 papers = mysqlService.findPaperByCitation(topK);
                Long t2=System.currentTimeMillis();
                sb.append(","+String.valueOf(t2-t1));
                writer.write(sb.toString()+"\n");
            }
            for(int i=0;i<papers.size();i++)
            {
                writer.write(papers.get(i).getPTitle().replace(","," ")+","+papers.get(i).getPCitation()+"\n");

            }
            writer.flush();
            writer.close();
        }
        catch (Exception e){

        }
    }

    @Test
    public void runProportion()
    {
        Map<String,Integer> cf=new TreeMap<>();
        try {
            FileWriter writer= new FileWriter("mysqlConfProportion.csv");
            for(int cnt=0;cnt<10;cnt++){
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(cnt));
                Long t1=System.currentTimeMillis();
                cf = mysqlService.proportion();
                Long t2=System.currentTimeMillis();
                sb.append(","+String.valueOf(t2-t1));
                writer.write(sb.toString()+"\n");
            }
            for(String key: cf.keySet())
            {
                writer.write(key+","+cf.get(key)+"\n");
            }
            writer.flush();
            writer.close();
        }
        catch (Exception e){

        }


    }

    @Test
    public void mostAuthor()
    {
        List<Pair<String,Integer>> cf = new ArrayList<>();
        int topK=20;
        try {
            FileWriter writer= new FileWriter("mysqlMostAuthor.csv");
            for(int cnt=0;cnt<10;cnt++){
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(cnt));
                Long t1=System.currentTimeMillis();
                cf = mysqlService.mostAuthor(topK);
                Long t2=System.currentTimeMillis();
                sb.append(","+String.valueOf(t2-t1));
                writer.write(sb.toString()+"\n");
            }
            for(int i=0;i<cf.size();i++)
            {
                writer.write(cf.get(i).getFirst().replace(","," ")+","+cf.get(i).getSecond().toString()+"\n");
            }
            writer.flush();
            writer.close();
        }
        catch (Exception e){

        }
    }

    @Test
    public void mostDep()
    {
        List<Pair<String,Integer>> cf=new ArrayList<>();
        int topK=20;
        try {
            FileWriter writer= new FileWriter("mysqlMostDepartment.csv");
            for(int cnt=0;cnt<10;cnt++){
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(cnt));
                Long t1=System.currentTimeMillis();
                cf = mysqlService.mostDepart(topK);
                Long t2=System.currentTimeMillis();
                sb.append(","+String.valueOf(t2-t1));
                writer.write(sb.toString()+"\n");
            }
            for(int i=0;i<cf.size();i++)
            {
                writer.write(cf.get(i).getFirst().replace(","," ")+","+cf.get(i).getSecond().toString()+"\n");
            }
            writer.flush();
            writer.close();
        }
        catch (Exception e){

        }
    }

    @Test
    public void coNum()
    {
        int topK=20;
        try {
            List<Pair<Integer,Pair<String,String>>> cf=new ArrayList<>();
            FileWriter writer= new FileWriter("mysqlCoByNum.csv");
            for(int cnt=0;cnt<10;cnt++){
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(cnt));
                Long t1=System.currentTimeMillis();
                cf = mysqlService.coNumDesc(topK);
                Long t2=System.currentTimeMillis();
                sb.append(","+String.valueOf(t2-t1));
                writer.write(sb.toString()+"\n");
            }
            for(int i=0;i<cf.size();i+=2)
            {
                writer.write(cf.get(i).getFirst()+","+cf.get(i).getSecond().getFirst().replace(","," ")+","+cf.get(i).getSecond().getSecond().replace(","," ")+"\n");
            }
            writer.flush();
            writer.close();
        }
        catch (Exception e){

        }
    }
}