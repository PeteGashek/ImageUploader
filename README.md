ImageUploader
===========

Spring boot REST Service to upload and download image thumbnails(for each image 3 pic : 100x100,200x200,300x300)
- Used Maven as build automation tool
- Spring boot
- JAX-RS
- Commons-IO
- Log4j
- Embeded Tomcat 7 as a servlet container

How to use:
- in application.properties set path to  your image directory, for example :

        path.to.uploadedImages = D:\\YourFolderName\\ImagesForTestServiceForAngular
- mvn tomcat7:run from console or in IDE
- next you can use postman or index page to upload your image. There you can modify url field to create different hub and subfolder structutre
If subfolder for the sertain name will be absent then it will be created.

- This pattern can be used :
  

        GET localhost:9966/ImageUploader/download/hub/hubId?token=nameOfImage&size=size
        POST localhost:9966/ImageUploader/upload/hub/hunId

Key for form-data multipart upload is "upload". Hub - is your folder in parent directory , hubId is a subfolder in this folder, token - name of the image, size - size of image (small,mid,big). If image is not present in the filesystem service will return 404 error.


-----------------------------------------------------------------
