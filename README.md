Google drive link to the submission >> https://docs.google.com/document/d/1jlkzbvv53CHBnFo6L_2lq2lukFLkLd_CGSssBvn9InY/ 

# AgroDoc App

![Alt text](https://github.com/Navneet7k/AgroDocRevamp/blob/master/androidDevChallenge.png?raw=true "Title")

AgroDoc is an Android Application that would allow farmers to treat their plant diseases in a series of steps which are predefined in our system.
We are using Tensorflow library(Machine Learning library) to detect plant disease. The farmers can use their smartphone to scan a particular plant 
leaf which they suspect for having a plant disease. We have generated custom re trained tensorflow models using the possible plant diseases. Here we 
are retraining a mobilenet_0.50 model. The model is pre trained with common classifications, but on top of it we are training it with various leaf diseases. 
We then analyse the plant diseases, symptoms etc and then generate proper measures to improve plant health.

# Use Case : 

The leaf of a particular plant/tree may differ in certain characteristics based on geographical conditions. Even within the same geographical conditions 
the leaves of a particular plant/tree may differ in characteristics like patterns and all. So if we try to match a leaf image sample with two or three 
samples which we have pre saved, we may not yield accurate results. This is because even same plant's leaves on different geographical conditions can show 
different patterns. So we need to have a model that has similar geographical conditions as this farmer. Training a model with a large number of image samples 
within this geographical condition  will definitely increase the accuracy of plant disease detection.

# Google's Help :

For this project, we will need huge datasets to build a model that is trustworthy. So the only way to do that will be to ask for contributions in the form of datasets/code. It is almost impractical to build such a model without a community help. I would like to get google's help in getting this project noticeable

# Project Timeline :

December 25, 2019 - UX/UI, set of all functionalities to be incorporated are finalised<br/>
January 25,2020   - Collecting sample datasets and training a model to be used for offline operations are done<br/> 
February 1, 2020  - testing and deploying the tensorflow lite model in the app (offline model).<br/>
February 8, 2020  - incorporating firebase mlkit for working in online conditions<br/>
February 20, 2020 - finalised the setup for training the dataset in cloud and further setup to be done for the process are carried out<br/>
March 20, 2020    - formalised a way to collect the user scanned data and train them using the cloud training process

# App Screens/Flow :

![Alt text](https://github.com/Navneet7k/AgroDocRevamp/blob/master/mockups/Screenshot%202019-10-27%20at%206.42.40%20PM.png?raw=true "Title")

![Alt text](https://github.com/Navneet7k/AgroDocRevamp/blob/master/mockups/Screenshot%202019-10-27%20at%206.43.53%20PM.png?raw=true "Title")

![Alt text](https://github.com/Navneet7k/AgroDocRevamp/blob/master/mockups/Screenshot%202019-10-27%20at%206.44.19%20PM.png?raw=true "Title")

![Alt text](https://github.com/Navneet7k/AgroDocRevamp/blob/master/mockups/Screenshot%202019-10-27%20at%206.44.39%20PM.png?raw=true "Title")

# about me

Stackoverflow :https://stackoverflow.com/users/8009433/navneet-krishna?tab=profile
Blog : https://www.freshbytelabs.com/

Hi, I'm Navneet from Kochi,India. I love solving common problems that are out there. I also love hackathons and other coding events. The AgroDoc app is one of my projects in which i had been spending most of my time recently. I have also won 1st runners up position for AgroDoc App in RapidValue Hackathon, please see here >> https://www.rapidvaluesolutions.com/event/coders-innovate-for-the-digital-future-hackathon19/
