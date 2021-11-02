package dao;

import bean.JCB;
import bean.PCB;
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
public class PcbDao {
    Connection c = null;

    public PcbDao()
    {
        c = new JdbcSqlite().getJDBC();
        System.out.println("Opened database successfully");
    }

    //查询数据：PCB
    public List<PCB> select()
    {
        List list = new ArrayList<PCB>();

        try {
            String sql = "SELECT * FROM pcb;";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String name  = rs.getString("name");
                int needSpace  = rs.getInt("needSpace");
                int priority  = rs.getInt("priority");
                List type = new StringToList().init(rs.getString("type"));
                int time = rs.getInt("time");

                PCB pcb = new PCB();
                pcb.setId(id);
                pcb.setName(name);
                pcb.setNeedSpace(needSpace);
                pcb.setPriority(priority);
                pcb.setType(type);
                pcb.setTime(time);

                list.add(pcb);
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
        System.out.println(new PcbDao().select());
    }
}
