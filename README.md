Android-app-for-ordering-drinks-in-restaurants
==============================================

Android app for ordering drinks in restaurants. App uses web REST(ful) web servis which is connected to mySQL database. App is part of system which containts Android app, RESTU(ful) servis and Java web app. Technologies: Java, Android.

REMARK: 
  - web app and REST(ful) web service is uploaded on free hosting so when you click on first screen, first you have to wait      for <30 seconds for activating the server. If is not working then free hosting is expired.
  - folder mNarudzbe from putInPhone put in memory of your phone. Otherwise app won't work.

First screen when you run app (1th image)

![Alt text](https://raw.githubusercontent.com/krunogr/Android-app-for-ordering-drinks-in-restaurants/master/mNarudzbe/res/screenshots/startScreen.JPG "start screen")


On the second screen you can see all places which are in system (2th image). So you can choose one of them and then you will see all articles which are available for ordering in certain restaurants/caffe bar (3th image). Places and articles app is getting through REST(ful) web service which is a part of web app.

![Alt text](https://raw.githubusercontent.com/krunogr/Android-app-for-ordering-drinks-in-restaurants/master/mNarudzbe/res/screenshots/placesList.JPG "Places")

On the next image you can see expandable list with a couple of categories of drinks. When you click on some item you can choose number of articles. When you are done with choosing article you should push Naruči and then you will see everything what is in your list (4th image).

![Alt text](https://raw.githubusercontent.com/krunogr/Android-app-for-ordering-drinks-in-restaurants/master/mNarudzbe/res/screenshots/ordering.JPG "Articles")

After pressing Potvrdi narudžbu user has to put a number of spot in caffe bar. All informations about order is sending through web service to mySQL database.

![Alt text](https://raw.githubusercontent.com/krunogr/Android-app-for-ordering-drinks-in-restaurants/master/mNarudzbe/res/screenshots/ordering2.JPG "Ordering")


In any moment user can see history of orders (5th image) and order again the same drinks.

![Alt text](https://raw.githubusercontent.com/krunogr/Android-app-for-ordering-drinks-in-restaurants/master/mNarudzbe/res/screenshots/history.JPG "History")
