package com.movinder.be.service;

import com.movinder.be.exception.InvalidIDException;
import org.bson.types.ObjectId;

public class Utility {
    public static void validateID(String id){
        if (!ObjectId.isValid(id)){
            throw new InvalidIDException(id);
        }
    }
}
