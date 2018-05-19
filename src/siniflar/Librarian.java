package siniflar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import KutuphaneciModul.Kutuphaneci;

public class Librarian extends BaseUser implements LibrarianController
{
    private int librarianID;
    private String name;
  
    private String userName;
    private String password;
    private int bookID;
    private String title;
    private String bookCategory;
    private String bookAuthor;
    private int bookPage;
    private Date bookDate;
    private int userID;
    private Date dateOfIssue;
    private Date returnDate;
    
    private java.sql.Connection connection;
	private Statement statement;
	private ResultSet rs;
	
	private ResultSet rsBookOfauthor;
	private ResultSet rsyazar;
	private Statement stBookOfauthor;
	private Statement styazar;
	private Statement stKitapEkle;
	private Statement stYazarEkle;
	private Statement stYazarYoksa;


    public int getLibrarianID() {
        return librarianID;
    }

    @Override
    public String getName() {
        return name;
    }


    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public int getBookPage() {
        return bookPage;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public int getUserID() {
        return userID;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setLibrarianID(int librarianID) {
        this.librarianID = librarianID;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookPage(int bookPage) {
        this.bookPage = bookPage;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    
    //Kitap Ekleme
    @Override
    public void  addBook(String title,int numberOfPages,int categoryId,int bookIssueId,String bookReleaseDate,String bookPublisher,String authorName) throws SQLException
    {
    	connectDB();
    	String sqlYazarKontrol="SELECT author_id FROM author WHERE author_name='"+authorName+"'";
    	int yazarID=0;
    	int bookID=0;

    	rs=statement.executeQuery(sqlYazarKontrol);
    	if(rs.next())
    	{
    		yazarID=rs.getInt(1);
    		statement.executeUpdate("INSERT INTO book(book_title,book_number_of_pages,book_category_id,book_issue_status_id,book_release_date,book_publisher) VALUES ('"+title+"','"+numberOfPages+"','"+categoryId+"','"+bookIssueId+"','"+bookReleaseDate+"','"+bookPublisher+"')");
    		rsBookOfauthor=statement.executeQuery("SELECT book.book_id FROM book ORDER BY book.book_id DESC LIMIT 1");
    		while(rsBookOfauthor.next())
    		{
    			bookID=rsBookOfauthor.getInt(1);
    		}
    		statement.executeUpdate("INSERT INTO authors_of_book(authorsOfBook_book_id,authorsOfBook_author_id) VALUES('"+bookID+"','"+yazarID+"')");
    	}
    	else
    	{
    		
    		statement.executeUpdate("INSERT INTO book(book_title,book_number_of_pages,book_category_id,book_issue_status_id,book_release_date,book_publisher) VALUES ('"+title+"','"+numberOfPages+"','"+categoryId+"','"+bookIssueId+"','"+bookReleaseDate+"','"+bookPublisher+"')");
    		statement.executeUpdate("INSERT INTO author(author_name) VALUES ('"+authorName+"')");
    		String sqlYazarYoksa="SELECT author.author_id FROM author ORDER BY author.author_id DESC LIMIT 1";
    		rsyazar=statement.executeQuery(sqlYazarYoksa);
    		while(rsyazar.next())
    		{
    			yazarID=rsyazar.getInt(1);
    		}
    		rsBookOfauthor=statement.executeQuery("SELECT book.book_id FROM book ORDER BY book.book_id DESC LIMIT 1");
    		while(rsBookOfauthor.next())
    		{
    			bookID=rsBookOfauthor.getInt(1);
    		}
    		statement.executeUpdate("INSERT INTO authors_of_book(authorsOfBook_book_id,authorsOfBook_author_id) VALUES('"+bookID+"','"+yazarID+"')");
    	}
		
		closeDB();
    }

    public void addBookFromFile(String fileName)
    {
        //Dosyadan kitap ekleme yeri
    }
    public  void issueBooks(int librarianID, int userID, int bookID, Date dateOfIssue)
    {
        //kitap Ã¶dÃ¼nÃ§ verme
    }
    public DefaultTableModel getBooks(String query,String[] baslik) throws SQLException
    {
    	connectDB();
    	rs=statement.executeQuery(query);
    	Object [][]veri;
    	int count=0;
    	rs.last();
		count=rs.getRow();
		veri=new Object[count][baslik.length];
		rs.first();
		for(int i=0;i<count;i++)
		{
			 for(int j=0;j<baslik.length;j++)
			 veri[i][j]=rs.getObject(j+1);
			 rs.next();
		}
		closeDB();
		return new DefaultTableModel(veri, baslik);
    }

    private void chargeFine()
    {

    }
    public  void getBooks(int issueStatusId)
    {
        //Ã¶dÃ¼nÃ§ verilen gÃ¶sterilen kitaplarÄ± gÃ¶sterme
    }
    public void getOutDatedBooks()
    {
       
    }

    public void returnBook(String librarianID, String userID, String bookID, Date returnDate)
    {
        //kitap geri alma
    }

    public void Login(String kullaniciAdi,String parola)
    {
        try {
	        
         Class.forName("com.mysql.jdbc.Driver");
         String url="jdbc:mysql://127.0.0.1:3306/librarymanagement?serverTimezone=UTC";
         Connection con = DriverManager.getConnection(url, "root", "1234");
         Statement stmt = con.createStatement();
         
         String sql = "SELECT * FROM user WHERE user_username='"+kullaniciAdi+"'and user_password='"+parola+"'";
         
         ResultSet rs=stmt.executeQuery(sql);
         if(rs.next())
         {
      	JOptionPane.showMessageDialog(null, "Login Successfuly..");
         	Kutuphaneci librarian = new Kutuphaneci();
			librarian.setVisible(true);
  	   }
			
         else
      	   JOptionPane.showMessageDialog(null, "Incorrect username and Password...");
      	   
       
         con.close();
         } 
  	   
  	   catch (Exception ex){
             System.out.println(ex);
         } 
    }
    
    
    public static List<Category>  cmbCategory ()
    {
    	
    	List<Category> liste=new ArrayList<Category>();
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver");
       	 	String url="jdbc:mysql://127.0.0.1:3306/librarymanagement?serverTimezone=UTC";
            Connection con = DriverManager.getConnection(url,"root","1234");
            Statement st = con.createStatement();
            ResultSet resultSet;
            
        	String query="SELECT * FROM category";
        	resultSet=st.executeQuery(query);
        	
        	while(resultSet.next())
        	{
        		Category siradakiCategory=new Category();
        		siradakiCategory.setID(resultSet.getInt("category_id"));
        		siradakiCategory.setName(resultSet.getString("category_name"));
        		liste.add(siradakiCategory);
        	}
        	st.close();
        	con.close();
        	
    	}
    	catch (SQLException ex) {
			ex.printStackTrace();
		}
    	catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
    	
    	return liste;
    }
    
    private void connectDB()
    {
        try
        {
        	 Class.forName("com.mysql.jdbc.Driver");
        	 String url="jdbc:mysql://127.0.0.1:3306/librarymanagement?serverTimezone=UTC";
             connection = DriverManager.getConnection(url,"root","1234");
             statement = connection.createStatement();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    //veri tabani baglantisi kapatma
    private void closeDB()
    {
        try
        {
            connection.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
