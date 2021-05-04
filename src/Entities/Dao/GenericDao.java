
package Entities.dao;



import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;

/**
 *
 * @author Tsepetzidis
 * 
 * GenericDao has and controls all the database connection properties.
 * It helps the program to connect to the database and execute the queries.
 */
public class GenericDao {
    public static enum viewFor{CREATE, EDIT, SEARCH;}                           // We use them to call the Views of objectMenu, so that they know for what purpose we want them
    public static final String daTiFormat = "dd/MM/yyyy";                       //The date time format
    protected static Connection conn;
    protected static Statement statement;
    protected static ResultSet result;
    protected static PreparedStatement ps;
    
    private String server = "localhost";
    private String port = "3306";
    private String dataBase = "scool";
    private String username = "root";
    private String password = "";
    
    
    public static GenericDao instance;
    private final String GETNUMBEROFTABLES ="SELECT COUNT(DISTINCT `table_name`) AS TotalNumberOfTables FROM `information_schema`.`columns` WHERE `table_schema` = ?";
    
    
    public GenericDao(){
        getProperties();                                                        //Reads the last connection properties from a properties file
        
    }
    /**
     * Returns our singleton database
     * @return GenericDao                                                       //We make GenericDao singleton
     */
    public static GenericDao getInstance(){
        if(instance == null){
            instance = new GenericDao();
        }
        return instance;
    }
    
    
    /**
     * Sets the default properties to the properties file.
     */
    public void setDefaultProperties(){
        Properties scoolProperties = new Properties();
        scoolProperties.setProperty("server", "localhost");
        scoolProperties.setProperty("port", "3306");
        scoolProperties.setProperty("dataBase", "scool");
        scoolProperties.setProperty("username", "root");
        scoolProperties.setProperty("password", "");
        Path PropertyFile = Paths.get("scool.Properties");
        try{
            Writer PropWriter = Files.newBufferedWriter(PropertyFile);
            scoolProperties.store(PropWriter, "sCOOL Properties");
            PropWriter.close();
        }
        catch(IOException ex){
            System.out.println("IO Exception :" +
            ex.getMessage());
        }
        getProperties();
    }
    
    
    /**
     * Sets database properties to properties file
     */
    public void setProperties(){
        Properties scoolProperties = new Properties();
        scoolProperties.setProperty("server", this.server);
        scoolProperties.setProperty("port", this.port);
        scoolProperties.setProperty("dataBase", this.dataBase);
        scoolProperties.setProperty("username", this.username);
        scoolProperties.setProperty("password", this.password);
        Path PropertyFile = Paths.get("scool.Properties");
        try{
            Writer PropWriter = Files.newBufferedWriter(PropertyFile);
            scoolProperties.store(PropWriter, "sCOOL Properties");
            PropWriter.close();
        }
        catch(IOException ex){
            System.out.println("IO Exception :" +
            ex.getMessage());
        }
    }
    
    
    
    public void getProperties(){
        Path PropertyFile = Paths.get("scool.Properties");
        try{
            Reader PropReader = Files.newBufferedReader(PropertyFile);
            Properties scoolProperties = new Properties();
            scoolProperties.load(PropReader);
            this.server = scoolProperties.getProperty("server");
            this.port = scoolProperties.getProperty("port");
            this.dataBase = scoolProperties.getProperty("dataBase");
            this.username = scoolProperties.getProperty("username");
            this.password = scoolProperties.getProperty("password");
            PropReader.close();
        }
        catch(IOException Ex)                                                   //If the file with the properties doesn't exist,
        {
            setDefaultProperties();                                             //creates new one with default options.
        }
    }
    
    /**
     * Does the connection to the database.
     */
    public Connection getConnection() {
        Connection connection = null;
        try{
            
             connection = DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+dataBase,username,password);
             
        }
        catch(SQLException e){
            System.out.println("THE PROGRAM CAN'T CONNECT TO THE DATABASE. CHECK IF THE SERVER IS ONLINE AND CHECK YOUR SETTINGS!!!");
            
        }
        return connection;
    }
    
    
    /**
     * Checks if the number of tables is the right.
     * Must be 8 tables
     * @return String
     */
    public String checkTables(){
        String resultTables ="";                    
        int numberOfTables = 0;
        
        try {
            result = makePreparedStatement(GETNUMBEROFTABLES ,new Object[]{(Object)dataBase});
            result.next();
            numberOfTables = result.getInt(1);
            if(numberOfTables == 0){
                resultTables = "EMPTY DB";                                      //if the number is 0, returns "EMPTY DB"
            }
            else if(numberOfTables == 8){                                       //if the number is 8, returns "OK"
                resultTables = "OK";
            }
            else if(numberOfTables !=8){                                        //if the number is not 8, returns "WRONG DATABASE"
                resultTables = "WRONG DATABASE";
            }
            result.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Somthing went wrong in GenericDao.checkTables()");
        }
        
        return resultTables;
    }
    
    
    /**
     * Checks the connection with database
     * @return boolean
     */
    public boolean checkDbConnection(){
        Connection conn;
        boolean result=false;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+dataBase,username,password);
            result = true;
            conn.close();
        }
        catch(SQLException e){
            result = false;
        }
        return result;
    }
    
    
    /**
     * Checks the connection with the server
     * @return boolean
     */
    public boolean checkServerConnection(){
        Connection conn;
        boolean result = false;
        try{
            
             conn = DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/?user="+username+"&password="+password);
             result = true;
             conn.close();
             
        }
        catch(SQLException e){
            result=false;
            
        }
        return result;
    }
    
    
    /**
     * closes the connection
     * @param rs
     * @param stmt
     * @param conn 
     */
    protected void closeConnections(ResultSet rs, Statement stmt, Connection conn) {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SOMETHING WENT WRONG WITH CLOSING CONNECTION");
        }
    }

    
    /**
     * closes the connections
     * @param pstm
     * @param conn 
     */
    protected void closeConnections(PreparedStatement pstm, Connection conn) {
        try {
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SOMETHING WENT WRONG WITH CLOSING CONNECTION");
        }
    }
    
    
    /**
     * executes a queries in database.
     * @param str
     * @return ResultSet 
     */
    public ResultSet makeStatement(String str){
        conn = getConnection();
        
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(str);
        } 
        catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("SOMETHING WENT WRONG WITH makeStatement");
        }
        
//        
        return result;
    }
    
    
    /**
     * Executes a prepared statement to the database
     * @param str
     * @param objArray
     * @return ResultSet
     */
    public ResultSet makePreparedStatement(String str,Object[] objArray){
        conn = getConnection();
        try {
            ps = conn.prepareStatement(str);                                    //Prepares a statement with the given querie
            if(objArray.length>0){
                for (int i = 0; i < objArray.length; i++) {                     //DownCasting the given objects and setts them in the righ place.
                    if(Integer.class.isInstance(objArray[i])){
                        ps.setInt(i+1,(int)objArray[i]);
                    }
                    else if(objArray[i] instanceof String){
                        ps.setString(i+1, (String)objArray[i]);
                    }
                    else if(Double.class.isInstance(objArray[i])){
                        ps.setDouble(i+1, (double)objArray[i]);
                    }
                    else if(objArray[i] instanceof LocalDate){
                        LocalDate x = (LocalDate) objArray[i];
                        ps.setDate(i+1, Date.valueOf(x));
                    }
                }
            }
            result = ps.executeQuery();
        } catch (SQLException ex) {
            
            System.out.println("SOMETHING GET WRONG WITH makeStatement IN GenericDao");
        }
          
        
        return result;
    }
    
    
    
    /**
     * Executes a prepared statement to the database
     * @param str
     * @param objArray 
     */
    public void makeUpdateStatement(String str,Object[] objArray){
        conn = getConnection();
        try {
            ps = conn.prepareStatement(str);
            if(objArray.length>0){
                for (int i = 0; i < objArray.length; i++) {
                    if(Integer.class.isInstance(objArray[i])){
                        ps.setInt(i+1,(int)objArray[i]);
                    }
                    else if(objArray[i] instanceof String){
                        ps.setString(i+1, (String)objArray[i]);
                    }
                    else if(Double.class.isInstance(objArray[i])){
                        ps.setDouble(i+1, (double)objArray[i]);
                    }
                    else if(objArray[i] instanceof LocalDate){
                        LocalDate x = (LocalDate) objArray[i];
                        ps.setDate(i+1, Date.valueOf(x));
                    }
                }
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("SOMETHING GET WRONG WITH makeStatement IN GenericDao");
        }
        
    }
    
    
    /**
     * Getters and Setters 
     */
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
