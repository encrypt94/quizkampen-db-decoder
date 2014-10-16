import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class QuizkampenDatabaseDecoder
{

    public static void main(String[] args)
    {
	if(args.length < 1)
	    {
		System.out.println("usage: java QuizDuelloDatabaseDecoder <quizkampen db path>");
		System.exit(0);
	    }
	Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
	ArrayList<Long> processed = new ArrayList<Long>();
	try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+args[0]);
	    connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM qk_questions");
            while(resultSet.next()) 
		{
		    long id = resultSet.getLong("id");
		    if(!processed.contains(id))
		    	{
			    PreparedStatement pstat = connection.prepareStatement("UPDATE qk_questions SET question = ? , alt1 = ?, alt2 = ?, alt3 = ?, alt4 = ?, cat_name = ? WHERE id = ?");
			    String question = QuizkampenDecoder.decode(resultSet.getString("question"),id);
			    String alt1 = QuizkampenDecoder.decode(resultSet.getString("alt1"),id);
			    String alt2 = QuizkampenDecoder.decode(resultSet.getString("alt2"),id);
			    String alt3 = QuizkampenDecoder.decode(resultSet.getString("alt3"),id);
			    String alt4 = QuizkampenDecoder.decode(resultSet.getString("alt4"),id);
			    String catName = QuizkampenDecoder.decode(resultSet.getString("cat_name"),id);
			    pstat.setString(1, question);
			    pstat.setString(2, alt1);
			    pstat.setString(3, alt2);
			    pstat.setString(4, alt3);
			    pstat.setString(5, alt4);
			    pstat.setString(6, catName);
			    pstat.setLong(7, id);
			    pstat.executeUpdate();
			    pstat.close();
			    processed.add(id);
			}
		}
        }
	catch (Exception e)
	    {
		e.printStackTrace();
	    }
	finally 
	    {
		try 
		    {
			resultSet.close();
			statement.close();
			connection.commit();
			connection.close();
		    }
		catch (Exception e)
		    {
			e.printStackTrace();
		    }
	    }
    }
}
