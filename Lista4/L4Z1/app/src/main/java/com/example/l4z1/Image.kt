package com.example.l4z1

class Image {
    var imagePath: String? = null
    var imageName: String? = null
    var description: String? = null
    var rating: Int? = null

    constructor(imagePath: String?, imageName: String?, description: String?, rating: Int?) {
        this.imagePath = imagePath
        this.imageName = imageName
        this.description = description
        this.rating = rating
    }
    constructor(){}

}