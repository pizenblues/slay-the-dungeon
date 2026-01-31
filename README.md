# Slay the Dungeon

Slay the Dungeon is a roguelike dungeon crawler game. This is a remade of the classic Pixel Dungeon featuring:
- 🗺️ New sprites and tilesets.
- ⚔️ New character perks added to the existing characters and a new class.
- 🌞 New GUI.
- 👺 New monsters and lore.
- 🎹 Brand new soundtrack with a different theme for each level.

What's coming up for v2?
-  Spanish translation.
-  New recipes
-  Monster meat buffs

Sprites and music by me:
www.instagram.com/drtamagotchi/

Based on Pixel Dungeon Graddle by Evan Debenham:
https://github.com/00-Evan/pixel-dungeon-gradle

Original Pixel Dungeon Source Code:
https://GitHub.com/watabou/pixel-dungeon

# Compiling STD

To compile STD you will need:
- (required) A computer which meets the [system requirements for Android Studio](https://developer.android.com/studio#get-android-studio)
- (recommended) A GitHub account to fork this repository, if you wish to use version control
- (recommended) An Android phone to test your build

### 1. Installing programs

Download and install the latest version of [Android Studio](https://developer.android.com/studio). This is the development environment which android apps use, it includes all the tools needed to get started with building android apps.

### 2. Setting up your copy of the code

If you are using version control, fork this repository using the 'fork' button at the top-right of this web page, so that you have your own copy of the code on GitHub.

### 3. Opening the code in Android Studio

Open Android Studio, you will be greeted with a splash page with a few options.

If you are using version control, you must first tell Android Studio where your installation of Git is located:
- Select 'Configure' then 'Settings'
- From the settings window, select 'Version Control' then 'Git'
- If it wasn't auto-detected, Point 'Path to Git executable:' to 'bin/git.exe', which will be located where you installed git.
- Hit the 'test' button to make sure git works, then press 'Okay' to return to the splash page.

After that, you will want to select 'check out project from version control' then 'git'. Log in to GitHub through the button (use username instead of tokens), and select your forked repository from the list of URLs. Import to whatever directory on your computer you like. Accept the default options android studio suggests when opening the project. If you would like more information about working with Git and committing changes you make back to version control, [consult this guide](https://code.tutsplus.com/tutorials/working-with-git-in-android-studio--cms-30514) (skip to chapter 4).

If you are not using version control, select 'Import project (Gradle, Eclipse ADT, etc.)' and select the folder you unzipped the code into. Accept the default options android studio suggests when opening the project.

### 4. Running the code

Once the code is open in Android Studio, running it will require either a physical android device or an android emulator. Using a physical device is recommended as the Android Emulator is less convenient to work with and has additional system requirements. Note that when you first open and run the code Android Studio may take some time, as it needs to set up the project and download various android build tools.

The Android Studio website has [a guide which covers the specifics of running a project you have already set up.](https://developer.android.com/studio/run)

This guide includes a [section on physical android devices...](https://developer.android.com/studio/run/device.html)

... and [a section on emulated android devices.](https://developer.android.com/studio/run/emulator)

### 5. Generating an installable APK

APK (Android PacKage) and AAB (Android App Bundle) files are used to distribute Android applications. The Android studio website has [a guide which covers building your app.](https://developer.android.com/studio/run/build-for-release) Note that the option you will likely want to use is 'Generate Signed Bundle / APK'. APK files are best used when you are not trying to upload your app to Google Play, otherwise you must provide Google with an AAB file and they will used that to generate APK files for you.

Note that APK and AAB files must be signed with a signing key. If you are making a small personal modification to STD then your signing key is not important, but **if you intend to distribute your modification to other people and want them to be able to receive updates, then your signing key is critical.** Google will also sign your app for you on Google Play, but you can provide them with the same key that you use to sign your own APKs. The Android studio website has [a guide on signing keys](https://developer.android.com/studio/publish/app-signing.html) that covers both personal and Google Play distribution.