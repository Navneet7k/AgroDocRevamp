# AgroDocRevamp

![Alt text](https://github.com/Navneet7k/AgroDocRevamp/blob/master/androidDevChallenge.png?raw=true "Title")

AgroDoc is an Android Application that would allow farmers to treat their plant diseases in a series of steps which are predefined in our system.
We are using Tensorflow library(Machine Learning library) to detect plant disease. The farmers can use their smartphone to scan a particular plant 
leaf which they suspect for having a plant disease. We have generated custom re trained tensorflow models using the possible plant diseases. Here we 
are retraining a mobilenet_0.50 model. The model is pre trained with common classifications, but on top of it we are training it with various leaf diseases. 
We then analyse the plant diseases, symptoms etc and then generate proper measures to improve plant health.

Use Case : 
The leaf of a particular plant/tree may differ in certain characteristics based on geographical conditions. Even within the same geographical conditions 
the leaves of a particular plant/tree may differ in characteristics like patterns and all. So if we try to match a leaf image sample with two or three 
samples which we have pre saved, we may not yield accurate results. This is because even same plant's leaves on different geographical conditions can show 
different patterns. So we need to have a model that has similar geographical conditions as this farmer. Training a model with a large number of image samples 
within this geographical condition  will definitely increase the accuracy of plant disease detection.
