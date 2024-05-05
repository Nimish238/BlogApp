package com.example.Nimish.BlogApp.services;

import com.example.Nimish.BlogApp.payloads.categoryDto;

import java.util.List;

public interface categoryService {

    //create
    categoryDto createCategory(categoryDto categoryDto);

    //update
    categoryDto updateCategory(categoryDto categoryDto,Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);

    //get
    categoryDto getCategory(Integer categoryId);

    //get all
    List<categoryDto> getCategories();
}
