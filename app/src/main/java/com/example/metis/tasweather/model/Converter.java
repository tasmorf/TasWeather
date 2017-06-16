package com.example.metis.tasweather.model;


public interface Converter<F, T> {

    T convert(F from) throws ConversionException;
}
