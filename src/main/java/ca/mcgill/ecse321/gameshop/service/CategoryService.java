package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    @Transactional
    public Category createCategory(String name){
        if (name==null){
            throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("Category name must not be empty."));
        }
        for(Category i : repo.findAll()){
            if(i.getCategoryName().equals(name)){
                throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("Category already exists."));
            }
        }
        Category c = new Category(name);
        return repo.save(c);
    }

    public Category findCategoryById(int cId){
        if(cId<0){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Category With ID " + cId + " is not valid."));
        }
        else if (repo.findCategoryByCategoryId(cId)==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Category With ID " + cId + " is not valid."));
        }
        return repo.findCategoryByCategoryId(cId);
    }

    @Transactional
    public Category updateCategory(int cId, String name){
        if(cId<0){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Category With ID " + cId + " is not valid."));}
        else if (repo.findCategoryByCategoryId(cId)==null){throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Category With ID " + cId + " is not valid."));}
        for (Category i : repo.findAll()){
            if (i.getCategoryName().equals(name)){
                throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("Category already exists."));
            }
        }
        Category toUpdate= repo.findCategoryByCategoryId(cId);
        toUpdate.setCategoryName(name);
        return repo.save(toUpdate);
    }

    public void deleteCategory(int cId) {
        if (cId<0){throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Category With ID " + cId + " is not valid."));}
        else if(repo.findCategoryByCategoryId(cId) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Category With ID " + cId + " is not valid."));
        }
        repo.deleteById(cId);
    }
    public Iterable<Category> getAllCategories(){
        return repo.findAll();
    }
}