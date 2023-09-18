# OtherWorlds

Hello there!

I'm currently looking for a coding job, so I decided to create an orignal app to showcase my Java language competency.

Here's some pictures: (from left to right, icon on homescreen, splashscreen, home sign in, home once signed in, read fragment, write fragment)

![home](https://github.com/zepher19/OtherWorlds/assets/108103331/36d51760-ea53-44c7-8062-ee7d9731a249)  ![splash](https://github.com/zepher19/OtherWorlds/assets/108103331/f154ec8f-c1cc-4924-a6f1-87c4abb259fb) ![hpme fragment](https://github.com/zepher19/OtherWorlds/assets/108103331/620a7edf-62bb-4b40-a16d-e4323d85b5e5) ![home signed in](https://github.com/zepher19/OtherWorlds/assets/108103331/5cd8da2d-3eab-48a4-b294-74098ff36631) ![write](https://github.com/zepher19/OtherWorlds/assets/108103331/9d4eeea7-f088-4247-b23a-35f15ed32a61) ![read](https://github.com/zepher19/OtherWorlds/assets/108103331/25cf844c-7c90-41c8-8901-8e0bae202160)







Here's a quick demo video if you'd like to see the app in action:

https://www.youtube.com/watch?v=2IbuZ38PcLs&ab_channel=Zepher319

Other Worlds was designed to be a platform for writing and sharing user created stories, in the vein of WattPad, if you're familiar. The app has three main fragments: home, read, and write. The home screen is where user's can log in using their google account. The write page features a simple text editor and several buttons for users to save, load, and upload their stories. The story is saved locally in an SQLite database as a String and accessed via a StoryDao class. The story may be uploaded to a user's google drive, which was meant to simulate saving to an external server (obviously I don't personally have a server capable of running this kind of app). The read fragment was meant to simulate accessing the online servers story database. It uses a webView to open the user's google drive in the app, and allows the user to view stories they and others have submitted. 



