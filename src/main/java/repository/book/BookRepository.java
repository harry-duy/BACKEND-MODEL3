package repository.book;

import model.Book;
import repository.connection.DBRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private static List<Book> books = new ArrayList<>();

    public static List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = DBRepository.getConnection().
                    createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from books");
            while (resultSet.next()){
                int idBook =resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                Double price = resultSet.getDouble("price");
                String imageURL = resultSet.getString("ImageURL");
                int quantity = resultSet.getInt("stock_quantity");
                String description = resultSet.getString("book_description");
                books.add(new Book(idBook,title,author,price,quantity,imageURL,description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
}
