package org.unipi.bioconnect.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.exception.DatabaseGenericException;
import org.unipi.bioconnect.exception.KeyException;

import java.util.function.Supplier;

@Slf4j
@Service
public class DatabaseOperationExecutor {

    public <T> T executeWithExceptionHandling(Supplier<T> action, String database) {
        try {
            return action.get();
        } catch (DuplicateKeyException e) {
            throw new KeyException("Entity with the provided ID already exists");
        } catch (DataAccessResourceFailureException e) {
            log.error("{} connection error: {}, stackTrace: {}", database, e.getMessage(), e.getMessage());
            throw new DataAccessResourceFailureException("Database connection error, operation not performed");
        } catch (DataAccessException e) {
            log.error("{} generic error: {}, stackTrace: {}", database, e.getMessage(), e.getStackTrace());
            throw new DatabaseGenericException("Generic database error, operation not performed");
        }
    }
}
