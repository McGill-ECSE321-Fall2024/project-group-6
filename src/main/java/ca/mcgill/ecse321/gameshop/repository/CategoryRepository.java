package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    public Category findCategoryByCategoryId(int categoryId);
    public Category findCategoryByCategoryName(String categoryName);
}

