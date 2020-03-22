*Disclaimer: Our github repo does not provide api keys for security reasons

Introduction

Virupp is an application, which will help to fight the epidemic of COVID-19 on many levels. What is important to stay healthy, how to prevent people from infections, what to do if we have already got infected, what information is needed to know about that dangerous virus named SaRS-CoV-2? The answer to all these questions will be concluded in this app. All of the users will stay informed about the latest news from the globe. So many fake ones appear lately, to prevent people from misinformation, our app provides a mythbuster where people could dispel their doubts. What is more, Virupp will cooperate with store owners, so they will be able to update the current status of products on store shelves, assortment deliveries, and report the length of queues at the cash desks in individual buildings. Another option made available to all users are reminders to wash their hands when they return to their homes. The application will automatically show a thirty-second slideshow on how to wash your hands properly. Virupp is accessible to all age groups, from children to elderly people.

Backend

Cloud Firestore handles most of our data. To retrieve information about amount of cases we are currently using this api:
https://github.com/NovelCOVID/API under GNU GPL license
But we are planning to develop our own api in future.
At this moment accounts are stored with anonymous device ID, we are planning to include google and facebook login to be able to transfer user data between different devices. Articles and Shops are also stored in Cloud Firestore. In the future we are planning to perform web scrapping from governments sites to get latest news instead of updating it manually.
New shops are added by users. App scan area in 15 km radius for shops and check if they exists in database. If not, they are created and filled with all supplies levels at 50 as a starting point. At this moment  we are only getting grocery stores and supermarkets, but we are planning to include chemists and pharmacies with fitting supplies types. When user updates shop data, his account is also receiving an update, with shop update time and that shop name and supply is added to an array, after an hour array is cleared and user can update blocked supply again.

Future Ideas

We plan to expand the range of diseases that affect humanity. Each of them will be thoroughly described and developed, they will not lack statistical data on illnesses, deaths and other parameters. As with COVID-19, the application will dispel any doubts related to symptoms and the course of diseases. Some of the latest reports will be constantly updated, so users will be kept informed of new discoveries via notifications. We intend to cooperate with certain authors and YouTube creators to enhance the transfer of information about diseases. It is well known that more and more often man prefers to acquire knowledge by watching, not reading. To reach as many customers as possible, we need to transfer knowledge in the most appropriate way. People like competition, therefore in the near future we are going to introduce a hand washing ranking, which will encourage people to take better care of cleanliness. In the future, we'll also add pop-up notifications when you approach the store and when you return home. The former will inform you about the status of the store, while the latter will remind you about washing your hands. In addition, notifications will appear one hour after the last hand wash.


Technologies

Whole app has been developed using Android Studio 3.6.1 in Kotlin programming language
We used several Maven repositories:
	https://mvnrepository.com/artifact/com.squareup.retrofit2
	https://github.com/oguzbilgener/CircularFloatingActionMenu
	https://github.com/googlesamples/easypermissions
Google Services:
	Google Firebase Analytics
	Google Firebase Firestore
	Google Firebase Authentication
	Google Location Services
And CoronaVirus data API:
	https://github.com/NovelCOVID/API 

Authors
Michał Posłuszny (UI Designer, Front&Backend Developer)
Karol Karpiński (Researcher, Writer)
Patrycja Żółkiewska (UI Designer, Graphics Designer)
Szymon Stasik (Location Services)
