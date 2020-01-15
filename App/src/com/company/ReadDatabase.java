package com.company;

import validators.Nota;
import validators.Student;
import validators.Tema;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReadDatabase {

    private final String url = "jdbc:postgresql://localhost:1234/java";
    private final String user = "postgres";
    private final String password = "admin";

    public ReadDatabase() {
        //connectToPostgres();
    }

    public void connectToPostgres(){

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:1234/java";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","admin");
            Connection c = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void emptyTable(String tableName) {
        String SQL = "DELETE FROM " + tableName;
        //System.out.println(SQL);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet ignored = stmt.executeQuery(SQL)) {
        } catch (SQLException ex) {
            //System.out.println(ex.getMessage());
        }
    }

    public void addStudents(Student st) {
        String SQL = "INSERT INTO studenti VALUES ('" + st.getNume() + "'," +
                String.valueOf(st.getGrupa()) + ",'" + st.getEmail() + "'," + "'" + st.getCadruDidactic() + "')";
        //System.out.println(SQL);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet ignored = stmt.executeQuery(SQL)) {
        } catch (SQLException ex) {
        }
    }
    public void addTema(Tema t) {
        String SQL = "INSERT INTO teme VALUES ('" + t.getDescriere() + "'," +
                String.valueOf(t.getStartWeek()) + "," + t.getDeadlineWeek() + ")";
//        System.out.println(SQL);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet ignored = stmt.executeQuery(SQL)) {
        } catch (SQLException ex) {
        }
    }
    public void addNote(Nota n){
        String SQL = "INSERT INTO note VALUES (" + n.getIdStudent() + "," +
                String.valueOf(n.getIdTema()) + "," + n.getNota() + "," + n.getDate() + ",'" +
                n.getProfesor()+ "','" + n.getFeedback()+ "')";
//        System.out.println(SQL);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet ignored = stmt.executeQuery(SQL)) {
        } catch (SQLException ex) {
        }
    }



    public Iterable<Student> getStudents() {

        String SQL = "SELECT nume, grupa, email, cadru_didactic FROM studenti";
        List<Student> lista= new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            // display actor information
            //displayStudent(rs);
            while(rs.next()) {
                String nume = rs.getString("nume");
                int grupa = Integer.parseInt(rs.getString("grupa"));
                String email = rs.getString("email");
                String cadruDidactic = rs.getString("cadru_didactic");
                lista.add(new Student(nume,grupa,email,cadruDidactic));
            }
    } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    public Iterable<Tema> getTeme() {

        String SQL = "SELECT descriere, startweek, deadlineweek  FROM teme";
        List<Tema> lista= new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            // display actor information
            //displayStudent(rs);
            while(rs.next()) {
                String descriere = rs.getString("descriere");
                int startweek = Integer.parseInt(rs.getString("startweek"));
                int deadlineweek = Integer.parseInt(rs.getString("deadlineweek"));
                lista.add(new Tema(descriere,startweek,deadlineweek));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    public Iterable<Nota> getNote() {

        String SQL = "SELECT idstudent, note.idtema, nota, week, profesor, feedback  FROM note";
        List<Nota> lista= new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            // display actor information
            //displayStudent(rs);
            while(rs.next()) {
                long idStudent = Long.parseLong(rs.getString("idStudent"));
                long idTema = Long.parseLong(rs.getString("idTema"));
                float nota = Float.parseFloat(rs.getString("nota"));
                int week = Integer.parseInt(rs.getString("week"));
                String profesor = rs.getString("profesor");
                String feedback = rs.getString("feedback");
                lista.add(new Nota(idStudent,idTema,week,nota,profesor,feedback));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lista;
    }


    public void writeStudents() {
        //String SQL = "SELECT cod_s, nume, grupa FROM student";
        String SQL = "INSERT INTO studenti VALUES ('Mano', 214,'mail','prf')";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            // display actor information
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void displayStudent(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("nume") + " "
                    + rs.getString("grupa") + " "
                    + rs.getString("email")+ " "
                    + rs.getString("cadru_didactic") );

        }
    }


}

