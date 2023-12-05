package com.hyb;


import org.junit.Test;
import org.neo4j.driver.*;

public class Neo4jText {
    private static Session session = GraphDatabase.driver("bolt://localhost:7687",
            AuthTokens.basic("neo4j","admin")).session();

    /**
     * 查看数据库中的数据
     */
    @Test
    public void matchReturnTest(){
        Result run = session.run("match (n) return n");
        while (run.hasNext()){
            Record next = run.next();
            System.out.println(next);
        }
        session.close();
    }


    /**
     * 创建节点
     */
    @Test
    public void createTest(){
        session.run("create (Dav:Person{name:'David',age:'20',hobby:'painting',gender:'male'})");
        session.run("create (Jor:Person{name:'maki',age:'21',hobby:'swimming',gender:'female'})");
        session.run("create (Gra:Person{name:'gewen',age:'18',hobby:'runting',gender:'female'})" +
                "- [friend:Likes] -> (Emm:Person{name:'Emma',age:'18',hobby:'painting',gender:'female'})");
        session.run("create (Jac:Person{name:'Jack',age:'20 ',hobby:'singing',gender:'female'})");


        session.close();
    }

    /**
     * 删除节点
     */
    @Test
    public void deleteTest(){
        session.run("match (Gra:Person) - [friend] -> (Emm:Person) delete Gra,Emm,friend");
        Result run = session.run("match (p:Person) return p.name");
        while(run.hasNext()){
            Record next = run.next();
            System.out.println(next);
        }
        session.close();
    }

    /**
     * 移除属性
     */
    @Test
      public void removeTest(){
        Result run = session.run("match (p{name:'Jack'}) remove p.hobby return p.name,p.age,p.hobby,p.gender");
        while (run.hasNext()){
            Record next = run.next();
            System.out.println(next);
        }
        session.close();
    }
}
