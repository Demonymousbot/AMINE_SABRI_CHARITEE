package com.example.organisationms.Exception;

public class OrganisationAlreadyExistsException extends RuntimeException{
    public OrganisationAlreadyExistsException(String message) {
        super(message);
    }
}
