# OtherWorlds

Hello there!

I'm currently looking for a coding job, so I decided to create an orignal app to showcase my Java language competency.

Here's a quick demo video if you'd like to see the app in action:

https://www.youtube.com/watch?v=2IbuZ38PcLs&ab_channel=Zepher319

Other Worlds was designed to be a platform for writing and sharing user created stories, in the vein of WattPad, if you're familiar. The app has three main fragments: home, read, and write. The home screen is where user's can log in using their google account. The write page features a simple text editor and several buttons for users to save, load, and upload their stories. The story is saved locally in an SQLite database as a String and accessed via a StoryDao class. The story may be uploaded to a user's google drive, which was meant to simulate saving to an external server (obviously I don't personally have a server capable of running this kind of app). The read fragment was meant to simulate accessing the online servers story database. It uses a webView to open the user's google drive in the app, and allows the user to view stories they and others have submitted. 



