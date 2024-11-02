package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    @Transactional
    public Category createCategory(String name){
        if (name==null){
            throw new IllegalArgumentException("Category name must be valid.");
        }
        Category c = new Category(name);
       return repo.save(c);
    }

    public Category findCategoryById(int cId){
        if(cId<0){throw new IllegalArgumentException("Category ID is not valid.");}
        else if (repo.findCategoryByCategoryId(cId)==null){ throw new IllegalArgumentException("Category does not exist.");}
        return repo.findCategoryByCategoryId(cId);
    }

    @Transactional
    public Category updateCategory(int cId, String name){
        if(cId<0){throw new IllegalArgumentException("Category ID is not valid.");}
        Category toUpdate= repo.findCategoryByCategoryId(cId);
        toUpdate.setCategoryName(name);
        return repo.save(toUpdate);
    }

    public void deleteCategory(int cId) {
        if (repo.findCategoryByCategoryId(cId) == null) {
            throw new IllegalArgumentException("Category does not exist.");
        }
        repo.deleteById(cId);
    }
    public Iterable<Category> getAllCategories(){
        return repo.findAll();
    }
}
