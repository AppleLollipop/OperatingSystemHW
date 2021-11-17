package dao;

import bean.Configure;
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
 * @create 2021/11/2 12:37
 */
public class ConfigureDao {
    Connection c = null;

    public ConfigureDao()
    {
        c = new JdbcSqlite().getJDBC();
        System.out.println("Opened database successfully");
    }

    //查询数据：Jcb
    public List<JCB> select()
    {
        List list = new ArrayList<JCB>();

        try {
            String sql = "SELECT * FROM configure;";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int maxProcess  = rs.getInt("maxProcess");
                int maxPriority  = rs.getInt("maxPriority");
                int maxType  = rs.getInt("maxType");
                int maxWork  = rs.getInt("maxWork");
                int memory  = rs.getInt("memory");
                int memoryBlock  = rs.getInt("memoryBlock");
                int memoryNumber  = rs.getInt("memoryNumber");
                int instructions  = rs.getInt("instructions");
                List type  = new StringToList().init(rs.getString("type"));

                Configure configure = new Configure();
                configure.setType(type);
                configure.setInstructions(instructions);
                configure.setMemoryNumber(memoryNumber);
                configure.setMemory(memory);
                configure.setMemoryBlock(memoryBlock);
                configure.setMaxWork(maxWork);
                configure.setMaxType(maxType);
                configure.setMaxPriority(maxPriority);
                configure.setMaxProcess(maxProcess);
                configure.setId(id);

                list.add(configure);
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
}
