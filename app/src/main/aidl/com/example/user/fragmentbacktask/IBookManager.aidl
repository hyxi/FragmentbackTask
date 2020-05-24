package com.example.user.fragmentbacktask;

import com.example.user.fragmentbacktask.messager.Book;
import com.example.user.fragmentbacktask.messager.IRemoteServiceCallback;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);

    void registerCallback(IRemoteServiceCallback cb);
    void unregisterCallback(IRemoteServiceCallback cb);
}
