ImageViewPopUpHelper
====================
ImageViewPopUpHelper enables you to create an image pop up on click. 
ImageViewPopUpHelper will cache your images on it's static method call. If the image source gets replaced between the method call and the user click, it's cache is updated.

Example of Use
-----
![bigger image](https://raw.github.com/juliomarcos/ImageViewPopUpHelper/master/images/popup-ex1.jpg)

Download
-----
Download the [0.1 JAR] (https://raw.github.com/juliomarcos/ImageViewPopUpHelper/master/bin/imageviewpopuphelper-0.1.jar)

Usage
-----
It's very simple to use it:
```
ImageViewPopUpHelper.enablePopUpOnClick(getActivity(), targetImageView);
```