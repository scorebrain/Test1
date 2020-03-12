package com.electromech.cronetstuff;

public class ImageRepository {

    private static String[] imageUrls= {
            "https://storage.googleapis.com/cronet/sun.jpg",
            "https://storage.googleapis.com/cronet/flower.jpg",
            "https://storage.googleapis.com/cronet/chair.jpg",
            "https://storage.googleapis.com/cronet/white.jpg",
            "https://storage.googleapis.com/cronet/moka.jpg",
            "https://storage.googleapis.com/cronet/walnut.jpg"
    };

    public static int numberOfImages() {
        return imageUrls.length;
    }

    public static String getImage(int position) {
        return imageUrls[position];
    }
}
