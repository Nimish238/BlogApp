package com.example.Nimish.BlogApp.controllers;

import com.example.Nimish.BlogApp.payloads.apiResponse;
import com.example.Nimish.BlogApp.payloads.categoryDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Nimish.BlogApp.services.categoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    @Autowired
    public categoryController(categoryService categoryService) {
        this.categoryService = categoryService;
    }

    //create
    @PostMapping("/")
    public ResponseEntity<categoryDto> createCategory(@Valid @RequestBody categoryDto categoryDto){

        categoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<categoryDto>(createCategory, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<categoryDto> updateCategory(@Valid @RequestBody categoryDto categoryDto, @PathVariable Integer categoryId){

        categoryDto createCategory = this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<categoryDto>(createCategory, HttpStatus.OK);

    }


    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<apiResponse> deleteCategory(@PathVariable Integer categoryId){

        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<apiResponse>(new apiResponse("category is deleted successfully",true), HttpStatus.OK);

    }


    //get by id
    @GetMapping("/{categoryId}")
    public ResponseEntity<categoryDto> getCategory(@PathVariable Integer categoryId){

        categoryDto categoryDto = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<categoryDto>(categoryDto,HttpStatus.OK);

    }


    //get-all
    @GetMapping("/")
    public ResponseEntity<List<categoryDto>> getCategories(){

        List<categoryDto> categories= this.categoryService.getCategories();
        return ResponseEntity.ok(categories);

    }

}
