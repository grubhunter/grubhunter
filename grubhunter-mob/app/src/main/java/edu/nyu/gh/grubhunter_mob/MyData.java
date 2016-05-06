package edu.nyu.gh.grubhunter_mob;

/**
 * Created by Student on 5/5/2016.
 */
public class MyData {
    public String myTitle;
    public String myNum;
    public String myRating;
    public String landmark;
    public String priceRange;
    public String noReviews;
    public String dishes;

    public MyData(){
        super();
    }

    public MyData(String myTitle, String myNum, String myRating, String landmark, String priceRange, String noReviews, String dishes) {
        super();
        this.myTitle = myTitle;
        this.myNum = myNum;
        this.myRating = myRating;
        this.landmark = landmark;
        this.priceRange = priceRange;
        this.noReviews = noReviews;
        this.dishes = dishes;
    }
}