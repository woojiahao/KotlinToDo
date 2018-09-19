# Setting Up
To follow this talk, you will need only a single tool, **Android Studio.**

The installation process of Android Studio is relatively simple and painless but you will have to ensure that you are able to run an Android Emulator and successfully run an Android sample example on the emulator.

**Note:** If you are using an Android device that has Nougat and above, you can opt out of the following steps and skip to [configuring my device.](setup.md?id=configuring-a-device)

## Configuring an Emulator
These set of steps are ideal if you do not own an Android device that has Nougat and above running on it.

### Creating the Emulator {docsify-ignore}
1. Navigate to **Tools > AVD Manager > Create Virtual Device**.
2. Select **Nexus 6** for the device definition and click **Next**.
3. If you do not have a system image for **API level 25** yet, download the image and follow the set up steps. (This step will take about 5 minutes on a good network connection)
4. Once you have **API level 25** installed, select it and click **Next**.
5. Leave all the defaults as is and select **Finish**.
 
### Testing the Emulator {docsify-ignore}
1. Open Android Studio
2. Select **Start a new Android Studio project**
3. In the following screen, enter the following select **Next**:
   * **Application name:** HelloWorld
   * **Company domain:** me.&lt;*your name*&gt;
   * **Project location:** *Leave the default*
   * **Include Kotlin support:** ✔️
4. Select **API 19: Android 4.4 (KitKat)** as the minimum SDK and select **Next**. This specifies the lowest API level that your application will support.
5. Select **Empty Activity > Next** and leave the next screen as what they display and select **Finish**.
6. Android Studio will load the project, if this is the first time you are building a project from Gradle, it will take a while so sit back and do something else.
7. After Gradle has finished building the project, you should see 2 files open: **MainActivity.kt** and **activity_main.xml**
8. Run the project, by doing **Ctrl + Shift + A** and typing **run** and selecting the first option. 
9. Select the previously created Android emulator and proceed with running the application.
10. Since this should be the first time you run the emulator, it will take a while to set up.
11. Once it has completed loading, your application will open shortly and you will see the text **"Hello World"** displayed in your application. 
12. You have just run your first Android program! Congratulations. 🎉

## Configuring a Device
These set of steps are ideal if you own an Android device that has Nougat and above running on it.

### Set Up {docsify-ignore}
1. Go to the **settings** in your phone.
2. Navigate to **About phone > Software information**.
3. You should be able to find an option for **Build number**. 
4. When you find it, tap on it 10 times to activate **Developer options**.
5. Return to the home of your settings and a new option should show up aptly labelled **Developer options**.
6. Enable these developer options and scroll down to find the option **USB debugging** and enable that.

### Testing the Device {docsify-ignore}
1. Follow the instructions from **step 1 to 8** from this [guide.](setup.md?id=testing-the-emulator)
2. Plug your phone into your computer via a USB cable.
3. A popup might appear prompting you to grant your computer access to your phone, select **Allow** if it does.
4. The AVD manager will then refresh after your device is detected, select your device and run the application on your device. 
5. You should see the Hello World application running on your phone.

## Troubleshooting
Whilst configuring your emulator, you might encounter some difficulties, a list of issues and fixes can be found on the [official Android developer site.](https://developer.android.com/studio/run/emulator-troubleshooting)

Another common issue encountered might be virtualization, and once again, the [official Android developer site](https://developer.android.com/studio/run/emulator-acceleration) covers a fix for these.

## Project Files
### Getting the files {docsify-ignore}
1. Navigate to the [**GitHub repository**.](https://github.com/woojiahao/KotlinToDoBare)
2. Select the option to **Clone or download** the repository.
3. Then, choose to **Download ZIP** to the location on your computer where you wish to keep the project files.
4. Unzip the downloaded file.
5. Launch Android Studio.
6. Select the option to **Import a project from Gradle** and navigate to the downloaded folder, select the root folder of the project and click **OK**.
7. Run the application on your emulator/device and you should be greeted with a bare bones Hello World application with some files included beforehand.

### What is included? {docsify-ignore}
The project folder includes the following:

* Layout files
* **SpacingDecoration.kt** 
* **SpacingDecorationError.kt**