# Conference Portal

Conference Portal is a web-based service to generate a customized android application for your conference. It consists of two components - **django-based web application** and a **generic android application**. The organizers register their conference, along with the required details on the web service, which then generates a specific APK file for that particular conference. The final application will support two modes - offline and online. 

<br>
<p align="center">
<img src="Screenshots/Combined.png">
</p>
<br>

This project was done as an Independent Project by [Saatvik Jain](https://www.github.com/saatvikj) & [Meghna Gupta](https://www.github.com/gupta-meghna64) under the guidance of [Dr. Pushpendra Singh](pushpendrasingh.org) at [IIIT Delhi](https://www.iiitd.ac.in).

# Features

The application downloaded from this web service is divided into two sections: **Online** and **Offline**. Common features in both online and offline are:
- [Day wise event schedule]()
- [Event information and socials]()
- [Food breaks during the event]()
- [Event location details]()
- [Event attendees]()

These features do not require an internet connection. The online mode takes advantage of internet connection to add several more features. These features are:
- [Attendee profiles]()
- [Social media feed]()
- [Networking opportunities]()

#### Day wise event schedule
#### Event information and socials
#### Food breaks
#### Location details
#### Attendee details

#### Social media feed
#### Attendee profiles
#### Networking opportunites

# Demo of Webapp

# Generated Application

# Development
The portal service contains of a web application and produces an android apk specific to the requirements of the conference. Necessary libraries for recreating the web and android application are listed below. 

### Web Application

#### Libraries Used
- django [Docs](https://docs.djangoproject.com/en/2.2/)
- pyrebase [Docs](https://github.com/thisbejim/Pyrebase)
- pycountry [Docs](https://pypi.org/project/pycountry/)

### Android Application



#### Libraries Used
- recycler-fast-scroll [Docs](https://github.com/FutureMind/recycler-fast-scroll)
- android-about-page [Docs](https://github.com/medyo/android-about-page)
- FastCSV [Docs](https://github.com/osiegmar/FastCSV)
- glide [Docs](https://github.com/bumptech/glide)
- SmartTabLayout [Docs](https://github.com/ogaclejapan/SmartTabLayout)

