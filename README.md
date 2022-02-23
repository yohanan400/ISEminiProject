# ISEminiProject

This project is part of the "introduction to software engineering" course. In this project, we create a little graphical library with the ability to determine the material, its reflection & reflection percentage, and of course its shape.

The shapes: 
sphere,
plane,
triangle.


[Preparation for cylinder and tube - need to implement the intersections method (findGeoIntersections)]

The elements:
Camera,
Directional light,
Point light,
Spotlight,
Ambient light.

The primitives that make up the shapes:
Coordinate,
Point (3D),
Ray,
Vector.

Material factors:
kD - The diffuse attenuation factor,
kS - The specular attenuation factor,
nShininess - The shininess factor,
kT - The transparency attenuation factor,
kR - The refraction attenuation factor.

In our project, we implement Ray Tracing, and to improve the performances, we also implement Adaptive Supersampling.

For picture improvement, we chose to implement depth of field.
The program calculates the focal point and refers to the distance from it the ray goes through a different point in the aperture (instead of the main point) to get the blurring effect.


some pectures to demonstrate the diffrences:

picture with no improvment:
![finalPicture](https://user-images.githubusercontent.com/41789360/155300494-8c788a1a-7599-424a-9492-b274f9d2cbdb.png)


picture with with super sampling:
![finalPictureSuperSampling](https://user-images.githubusercontent.com/41789360/155301283-25616042-0cd9-454e-afaf-71ec88fd9709.png)


picture with with super sampling and depth of field (the person is the focal point):
![finalPictureSuperSamplingDOF](https://user-images.githubusercontent.com/41789360/155301473-679fdbde-d8f1-4581-bf53-d4b3925ff0c9.png)
