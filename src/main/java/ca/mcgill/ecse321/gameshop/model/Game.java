package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 45 "model.ump"
// line 130 "model.ump"
public class Game
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Game Attributes
    private String name;
    private String description;
    private float price;
    private int stockQuantity;
    private boolean approvedByOwner;
    private Image photo;
    private String category;

    //Game Associations
    private List<Review> reviews;
    private Manager manager;
    private List<Wishlist> wishlists;
    private List<Category> categories;
    private List<Cart> carts;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Game(String aName, String aDescription, float aPrice, int aStockQuantity, boolean aApprovedByOwner, Image aPhoto, String aCategory, Manager aManager, Category... allCategories)
    {
        name = aName;
        description = aDescription;
        price = aPrice;
        stockQuantity = aStockQuantity;
        approvedByOwner = aApprovedByOwner;
        photo = aPhoto;
        category = aCategory;
        reviews = new ArrayList<Review>();
        boolean didAddManager = setManager(aManager);
        if (!didAddManager)
        {
            throw new RuntimeException("Unable to create game due to manager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        wishlists = new ArrayList<Wishlist>();
        categories = new ArrayList<Category>();
        boolean didAddCategories = setCategories(allCategories);
        if (!didAddCategories)
        {
            throw new RuntimeException("Unable to create Game, must have at least 1 categories. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        carts = new ArrayList<Cart>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setName(String aName)
    {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setDescription(String aDescription)
    {
        boolean wasSet = false;
        description = aDescription;
        wasSet = true;
        return wasSet;
    }

    public boolean setPrice(float aPrice)
    {
        boolean wasSet = false;
        price = aPrice;
        wasSet = true;
        return wasSet;
    }

    public boolean setStockQuantity(int aStockQuantity)
    {
        boolean wasSet = false;
        stockQuantity = aStockQuantity;
        wasSet = true;
        return wasSet;
    }

    public boolean setApprovedByOwner(boolean aApprovedByOwner)
    {
        boolean wasSet = false;
        approvedByOwner = aApprovedByOwner;
        wasSet = true;
        return wasSet;
    }

    public boolean setPhoto(Image aPhoto)
    {
        boolean wasSet = false;
        photo = aPhoto;
        wasSet = true;
        return wasSet;
    }

    public boolean setCategory(String aCategory)
    {
        boolean wasSet = false;
        category = aCategory;
        wasSet = true;
        return wasSet;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public float getPrice()
    {
        return price;
    }

    public int getStockQuantity()
    {
        return stockQuantity;
    }

    public boolean getApprovedByOwner()
    {
        return approvedByOwner;
    }

    public Image getPhoto()
    {
        return photo;
    }

    public String getCategory()
    {
        return category;
    }
    /* Code from template association_GetMany */
    public Review getReview(int index)
    {
        Review aReview = reviews.get(index);
        return aReview;
    }

    public List<Review> getReviews()
    {
        List<Review> newReviews = Collections.unmodifiableList(reviews);
        return newReviews;
    }

    public int numberOfReviews()
    {
        int number = reviews.size();
        return number;
    }

    public boolean hasReviews()
    {
        boolean has = reviews.size() > 0;
        return has;
    }

    public int indexOfReview(Review aReview)
    {
        int index = reviews.indexOf(aReview);
        return index;
    }
    /* Code from template association_GetOne */
    public Manager getManager()
    {
        return manager;
    }
    /* Code from template association_GetMany */
    public Wishlist getWishlist(int index)
    {
        Wishlist aWishlist = wishlists.get(index);
        return aWishlist;
    }

    public List<Wishlist> getWishlists()
    {
        List<Wishlist> newWishlists = Collections.unmodifiableList(wishlists);
        return newWishlists;
    }

    public int numberOfWishlists()
    {
        int number = wishlists.size();
        return number;
    }

    public boolean hasWishlists()
    {
        boolean has = wishlists.size() > 0;
        return has;
    }

    public int indexOfWishlist(Wishlist aWishlist)
    {
        int index = wishlists.indexOf(aWishlist);
        return index;
    }
    /* Code from template association_GetMany */
    public Category getCategory(int index)
    {
        Category aCategory = categories.get(index);
        return aCategory;
    }

    public List<Category> getCategories()
    {
        List<Category> newCategories = Collections.unmodifiableList(categories);
        return newCategories;
    }

    public int numberOfCategories()
    {
        int number = categories.size();
        return number;
    }

    public boolean hasCategories()
    {
        boolean has = categories.size() > 0;
        return has;
    }

    public int indexOfCategory(Category aCategory)
    {
        int index = categories.indexOf(aCategory);
        return index;
    }
    /* Code from template association_GetMany */
    public Cart getCart(int index)
    {
        Cart aCart = carts.get(index);
        return aCart;
    }

    public List<Cart> getCarts()
    {
        List<Cart> newCarts = Collections.unmodifiableList(carts);
        return newCarts;
    }

    public int numberOfCarts()
    {
        int number = carts.size();
        return number;
    }

    public boolean hasCarts()
    {
        boolean has = carts.size() > 0;
        return has;
    }

    public int indexOfCart(Cart aCart)
    {
        int index = carts.indexOf(aCart);
        return index;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfReviews()
    {
        return 0;
    }
    /* Code from template association_AddManyToOne */
    public Review addReview(String aRating, String aComment, int aAmountOfLikes, Customer aCustomer)
    {
        return new Review(aRating, aComment, aAmountOfLikes, aCustomer, this);
    }

    public boolean addReview(Review aReview)
    {
        boolean wasAdded = false;
        if (reviews.contains(aReview)) { return false; }
        Game existingGame = aReview.getGame();
        boolean isNewGame = existingGame != null && !this.equals(existingGame);
        if (isNewGame)
        {
            aReview.setGame(this);
        }
        else
        {
            reviews.add(aReview);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeReview(Review aReview)
    {
        boolean wasRemoved = false;
        //Unable to remove aReview, as it must always have a game
        if (!this.equals(aReview.getGame()))
        {
            reviews.remove(aReview);
            wasRemoved = true;
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addReviewAt(Review aReview, int index)
    {
        boolean wasAdded = false;
        if(addReview(aReview))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
            reviews.remove(aReview);
            reviews.add(index, aReview);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveReviewAt(Review aReview, int index)
    {
        boolean wasAdded = false;
        if(reviews.contains(aReview))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
            reviews.remove(aReview);
            reviews.add(index, aReview);
            wasAdded = true;
        }
        else
        {
            wasAdded = addReviewAt(aReview, index);
        }
        return wasAdded;
    }
    /* Code from template association_SetOneToMany */
    public boolean setManager(Manager aManager)
    {
        boolean wasSet = false;
        if (aManager == null)
        {
            return wasSet;
        }

        Manager existingManager = manager;
        manager = aManager;
        if (existingManager != null && !existingManager.equals(aManager))
        {
            existingManager.removeGame(this);
        }
        manager.addGame(this);
        wasSet = true;
        return wasSet;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfWishlists()
    {
        return 0;
    }
    /* Code from template association_AddManyToManyMethod */
    public boolean addWishlist(Wishlist aWishlist)
    {
        boolean wasAdded = false;
        if (wishlists.contains(aWishlist)) { return false; }
        wishlists.add(aWishlist);
        if (aWishlist.indexOfGame(this) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = aWishlist.addGame(this);
            if (!wasAdded)
            {
                wishlists.remove(aWishlist);
            }
        }
        return wasAdded;
    }
    /* Code from template association_RemoveMany */
    public boolean removeWishlist(Wishlist aWishlist)
    {
        boolean wasRemoved = false;
        if (!wishlists.contains(aWishlist))
        {
            return wasRemoved;
        }

        int oldIndex = wishlists.indexOf(aWishlist);
        wishlists.remove(oldIndex);
        if (aWishlist.indexOfGame(this) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = aWishlist.removeGame(this);
            if (!wasRemoved)
            {
                wishlists.add(oldIndex,aWishlist);
            }
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addWishlistAt(Wishlist aWishlist, int index)
    {
        boolean wasAdded = false;
        if(addWishlist(aWishlist))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfWishlists()) { index = numberOfWishlists() - 1; }
            wishlists.remove(aWishlist);
            wishlists.add(index, aWishlist);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveWishlistAt(Wishlist aWishlist, int index)
    {
        boolean wasAdded = false;
        if(wishlists.contains(aWishlist))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfWishlists()) { index = numberOfWishlists() - 1; }
            wishlists.remove(aWishlist);
            wishlists.add(index, aWishlist);
            wasAdded = true;
        }
        else
        {
            wasAdded = addWishlistAt(aWishlist, index);
        }
        return wasAdded;
    }
    /* Code from template association_IsNumberOfValidMethod */
    public boolean isNumberOfCategoriesValid()
    {
        boolean isValid = numberOfCategories() >= minimumNumberOfCategories();
        return isValid;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfCategories()
    {
        return 1;
    }
    /* Code from template association_AddManyToManyMethod */
    public boolean addCategory(Category aCategory)
    {
        boolean wasAdded = false;
        if (categories.contains(aCategory)) { return false; }
        categories.add(aCategory);
        if (aCategory.indexOfGame(this) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = aCategory.addGame(this);
            if (!wasAdded)
            {
                categories.remove(aCategory);
            }
        }
        return wasAdded;
    }
    /* Code from template association_AddMStarToMany */
    public boolean removeCategory(Category aCategory)
    {
        boolean wasRemoved = false;
        if (!categories.contains(aCategory))
        {
            return wasRemoved;
        }

        if (numberOfCategories() <= minimumNumberOfCategories())
        {
            return wasRemoved;
        }

        int oldIndex = categories.indexOf(aCategory);
        categories.remove(oldIndex);
        if (aCategory.indexOfGame(this) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = aCategory.removeGame(this);
            if (!wasRemoved)
            {
                categories.add(oldIndex,aCategory);
            }
        }
        return wasRemoved;
    }
    /* Code from template association_SetMStarToMany */
    public boolean setCategories(Category... newCategories)
    {
        boolean wasSet = false;
        ArrayList<Category> verifiedCategories = new ArrayList<Category>();
        for (Category aCategory : newCategories)
        {
            if (verifiedCategories.contains(aCategory))
            {
                continue;
            }
            verifiedCategories.add(aCategory);
        }

        if (verifiedCategories.size() != newCategories.length || verifiedCategories.size() < minimumNumberOfCategories())
        {
            return wasSet;
        }

        ArrayList<Category> oldCategories = new ArrayList<Category>(categories);
        categories.clear();
        for (Category aNewCategory : verifiedCategories)
        {
            categories.add(aNewCategory);
            if (oldCategories.contains(aNewCategory))
            {
                oldCategories.remove(aNewCategory);
            }
            else
            {
                aNewCategory.addGame(this);
            }
        }

        for (Category anOldCategory : oldCategories)
        {
            anOldCategory.removeGame(this);
        }
        wasSet = true;
        return wasSet;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addCategoryAt(Category aCategory, int index)
    {
        boolean wasAdded = false;
        if(addCategory(aCategory))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
            categories.remove(aCategory);
            categories.add(index, aCategory);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveCategoryAt(Category aCategory, int index)
    {
        boolean wasAdded = false;
        if(categories.contains(aCategory))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
            categories.remove(aCategory);
            categories.add(index, aCategory);
            wasAdded = true;
        }
        else
        {
            wasAdded = addCategoryAt(aCategory, index);
        }
        return wasAdded;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfCarts()
    {
        return 0;
    }
    /* Code from template association_AddManyToManyMethod */
    public boolean addCart(Cart aCart)
    {
        boolean wasAdded = false;
        if (carts.contains(aCart)) { return false; }
        carts.add(aCart);
        if (aCart.indexOfGame(this) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = aCart.addGame(this);
            if (!wasAdded)
            {
                carts.remove(aCart);
            }
        }
        return wasAdded;
    }
    /* Code from template association_RemoveMany */
    public boolean removeCart(Cart aCart)
    {
        boolean wasRemoved = false;
        if (!carts.contains(aCart))
        {
            return wasRemoved;
        }

        int oldIndex = carts.indexOf(aCart);
        carts.remove(oldIndex);
        if (aCart.indexOfGame(this) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = aCart.removeGame(this);
            if (!wasRemoved)
            {
                carts.add(oldIndex,aCart);
            }
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addCartAt(Cart aCart, int index)
    {
        boolean wasAdded = false;
        if(addCart(aCart))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfCarts()) { index = numberOfCarts() - 1; }
            carts.remove(aCart);
            carts.add(index, aCart);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveCartAt(Cart aCart, int index)
    {
        boolean wasAdded = false;
        if(carts.contains(aCart))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfCarts()) { index = numberOfCarts() - 1; }
            carts.remove(aCart);
            carts.add(index, aCart);
            wasAdded = true;
        }
        else
        {
            wasAdded = addCartAt(aCart, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        for(int i=reviews.size(); i > 0; i--)
        {
            Review aReview = reviews.get(i - 1);
            aReview.delete();
        }
        Manager placeholderManager = manager;
        this.manager = null;
        if(placeholderManager != null)
        {
            placeholderManager.removeGame(this);
        }
        ArrayList<Wishlist> copyOfWishlists = new ArrayList<Wishlist>(wishlists);
        wishlists.clear();
        for(Wishlist aWishlist : copyOfWishlists)
        {
            aWishlist.removeGame(this);
        }
        ArrayList<Category> copyOfCategories = new ArrayList<Category>(categories);
        categories.clear();
        for(Category aCategory : copyOfCategories)
        {
            aCategory.removeGame(this);
        }
        ArrayList<Cart> copyOfCarts = new ArrayList<Cart>(carts);
        carts.clear();
        for(Cart aCart : copyOfCarts)
        {
            aCart.removeGame(this);
        }
    }


    public String toString()
    {
        return super.toString() + "["+
                "name" + ":" + getName()+ "," +
                "description" + ":" + getDescription()+ "," +
                "price" + ":" + getPrice()+ "," +
                "stockQuantity" + ":" + getStockQuantity()+ "," +
                "approvedByOwner" + ":" + getApprovedByOwner()+ "," +
                "category" + ":" + getCategory()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "photo" + "=" + (getPhoto() != null ? !getPhoto().equals(this)  ? getPhoto().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null");
    }
}


