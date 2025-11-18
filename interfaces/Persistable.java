package com.student.management.interfaces;

import java.io.IOException;

public interface Persistable {
    void save(String filepath) throws IOException;
    void load(String filepath) throws IOException;
}