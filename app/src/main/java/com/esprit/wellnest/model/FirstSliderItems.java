package com.esprit.wellnest.model;

public class FirstSliderItems {
    private String url;         // For images loaded from URLs
    private int drawableResId;  // For images loaded from local resources

    // Constructors
    public FirstSliderItems(String url) {
        this.url = url;
        this.drawableResId = 0; // Indicate that this is a URL image
    }

    public FirstSliderItems(int drawableResId) {
        this.drawableResId = drawableResId;
        this.url = null; // Indicate that this is a drawable resource image
    }

    // Getters
    public String getUrl() {
        return url;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    // Helper method to check if it's a URL image or a drawable resource
    public boolean isUrl() {
        return url != null;
    }
}
