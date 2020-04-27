# TDT4240 - Gålf

Gålf is surprisingly a Golf-like 2D game for Android, with support for  both multiplayer and single-player.

The goal of the game is to get from the starting point to the flag in the least amount of shots possible. In order to reach the goal, the player will have to maneuver rough terrain. Colliding with terrain or objects will cause the ball trajectory to change. If the goal is not reached within X shots, the level is considered failed. The game itself will consist of level packs, where a level pack can contain any number of levels.

Both game modes will also support different ball shapes. Planned shapes are squares and circles. These alternative shapes can impact the size and speed of the ball, whilst also affecting how collisions will impact ball trajectory.

The ball object will be controlled with touch \& drag, where a player can touch and drag in the opposite direction of where they intend to go. The power of the shot is dependent on how far the player decides to drag. A tiny indicator will give the player a general idea of their shot trajectory and strength. A visual of the game concept is shown in figure.

## Installing
The application can be ran on both PC and Android. Instructions on how to perform both will be outlined in this subsection. The game- and matchmaking-servers are currently being hosted by the group, but options for hosting them locally will also be provided.

Do note that these installation notes are specified towards Windows-based machines. In order for the gradle commands to run on Linux (tested on Mac OS) you will have to use `./gradlew` instead. Furthermore, you might have to chmod the *gradlew* file. When testing, *755* was used for setting permissions.

### Desktop

In order for the desktop application to run you might have to edit the desktop run configurations, due to the assets being stored in the android assets folder. This can be done by opening the project in Android Studio and selecting "Edit Configurations..." from the Run menu. Add a new Application runtime configuration. Within this configuration, set the working directory the the assets folder, which can be found in the Android folder. Furthermore, the "use classpath of module" option should be set to desktop. The "Main class" option should be set to "DesktopLauncher", which should appear in the provided list when clicking the button next to the option. 

From this point on you can run the application with the newly created run configuration by selecting it within Android studio and then clicking the "Run" button.

You can also run the application directly through the command line by using gradle. 
This can be done by navigating to the root of the source folder and running the command
    `gradlew desktop:run`
    
#### Hosting servers locally
You can also choose to host both game- and matchmaking-servers locally. In order for this to work you will still have to have the run configuration mentioned above. Each command should be run in a separate command line window. The processes tends to stop at 87\% for some reason, but they still work.

Within a command line, navigate to the root of the source folder. The following command will start the matchmaking server. Sometimes there will be an exception displayed, but this can be ignored:
    `gradlew server-matchmaking:run --args local`

The following command will start a game server:
    `gradlew server-game:run --args 7001`

Instances of the game also needs to be instantiated to work towards the locally hosted servers. This is done with the following command:
    `gradlew desktop:run --args local`
    

### Android
The group has ran the application through the use of Android studio throughout the project period, and therefore recommends that the reader does the same. In order to run the application on Android you will need an Android emulator running on API version 22 (Lollipop) or higher. The group recommends using API version 29, as this is what has been primarily tested on throughout the duration of the project. The run configuration for Android should be straightforward to set up. Create a new run configuration the same way as for desktop, but choose "Android App" instead of "Application". The only option that could need to be changed is the "Module" option, which should be set to "android".

An APK has also been provided. It is located in the root folder of the source as \textit{gaalf.apk}. It has been tested on both emulators running both API 22 and 29. Emulators running on lower API versions than 22 is not guaranteed to work. The APK can be installed by simply firing up an Android emulator and dragging the file on top of the emulated screen. It can then be ran. 
![Android CI](https://github.com/Jonas-C/TDT4240-Gaalf/workflows/Android%20CI/badge.svg)
