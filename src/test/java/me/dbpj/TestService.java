package me.dbpj;

import me.dbpj.dao.PaperDao;
import me.dbpj.entity.Author;
import me.dbpj.entity.Paper;
import me.dbpj.service.MysqlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestService {

    @Autowired
    private MysqlService mysqlService;

    @Test
    public void runOneDocker() {
        System.out.println("START");
        List<Paper> papers=mysqlService.findPaperByTwoAuthorInOneField("Joxan Jaffar","Andrew E. Santosa");
        System.out.println("END");
        for(Paper p :papers)
        {
            System.out.println(p.getPTitle());
        }
        List<Author> authors=mysqlService.findAuthorByCoNum("Joxan Jaffar",10);
        for(Author author:authors)
        {
            System.out.println(author.getAuthorName());
        }
    }

}

