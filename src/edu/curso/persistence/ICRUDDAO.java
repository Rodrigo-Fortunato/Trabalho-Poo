package edu.curso.persistence;

import edu.curso.controller.EventosException;

import java.util.List;

public interface ICRUDDAO<T> {

    void inserir(T t ) throws EventosException;
    void atualizar( T t ) throws EventosException;
    void remover(T t  ) throws EventosException;
    List<T> pesquisarPor(String generic ) throws EventosException;


}
