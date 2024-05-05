package com.example.Nimish.BlogApp.services.impl;

import com.example.Nimish.BlogApp.entities.category;
import com.example.Nimish.BlogApp.exceptions.ResourceNotFoundException;
import com.example.Nimish.BlogApp.payloads.categoryDto;
import com.example.Nimish.BlogApp.services.categoryService;
import com.example.Nimish.BlogApp.repositories.categoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class categoryServiceImpl implements categoryService {

    @Autowired
    private categoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public categoryDto createCategory(categoryDto categoryDto) {
        category cat=this.modelMapper.map(categoryDto, category.class);

        category addedCat=this.categoryRepo.save(cat);

        return this.modelMapper.map(addedCat,categoryDto.class);

    }

    @Override
    public categoryDto updateCategory(categoryDto categoryDto, Integer categoryId) {
        category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() ->new ResourceNotFoundException("category" ,"category id",categoryId ));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());

        cat.setCategoryDescription(categoryDto.getCategoryDescription());

        category updatedCat = this.categoryRepo.save(cat);

        return this.modelMapper.map(updatedCat, categoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category" , "categoryId" , categoryId));

        this.categoryRepo.delete(cat);
    }

    @Override
    public categoryDto getCategory(Integer categoryId) {
        category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category","category id",categoryId));

        return this.modelMapper.map(cat, categoryDto.class);
    }

    @Override
    public List<categoryDto> getCategories() {
        List<category> categories = this.categoryRepo.findAll();
        List<categoryDto> catDos= categories.stream().map((cat) ->this.modelMapper.map(cat, categoryDto.class))
                .collect(Collectors.toList());
        return catDos;
    }
}
