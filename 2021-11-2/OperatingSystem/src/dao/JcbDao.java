package dao;

import bean.JCB;
import util.JdbcSqlite;
import util.StringToList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xzy
 * @create 2021/10/31 13:08
 */
public class JcbDao {
    Connection c = null;

    public JcbDao()
    {
        c = new JdbcSqlite().getJDBC();
        System.out.println("Opened database successfully");
    }

    //查询数据：Jcb
    public List<JCB> select()
    {
        List list = new ArrayList<JCB>();

        try {
            String sql = "SELECT * FROM jcb;";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String name  = rs.getString("name");
                int needSpace  = rs.getInt("needSpace");
                int priority  = rs.getInt("priority");
                List type = new StringToList().init(rs.getString("type"));
                int time = rs.getInt("time");

                JCB jcb = new JCB();
                jcb.setId(id);
                jcb.setName(name);
                jcb.setNeedSpace(needSpace);
                jcb.setPriority(priority);
                jcb.setTime(time);
                jcb.setType(type);

                list.add(jcb);
            }

            rs.close();
            ps.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Operation done successfully");
        return list;
    }

    public static void main(String[] args) {
        System.out.println(new JcbDao().select());
    }
}
