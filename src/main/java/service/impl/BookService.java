package service.impl;

import repository.book.BookRepository;
import service.IService;

import java.util.List;

public class BookService implements IService {
    private static BookRepository bookRepository = new BookRepository();
    @Override
    public List getAll() {
        return BookRepository.findAll();
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void update(int id, Object o) {

    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public List findByName(String name) {
        return null;
    }
}
