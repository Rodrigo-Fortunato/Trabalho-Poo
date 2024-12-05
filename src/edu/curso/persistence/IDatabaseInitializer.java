package edu.curso.persistence;

import edu.curso.controller.EventosException;

public interface IDatabaseInitializer {
    void initializeDatabase() throws EventosException;
    void initializeTables() throws EventosException;
}
