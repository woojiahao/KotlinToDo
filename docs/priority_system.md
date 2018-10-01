# Adding Priorities
Now that the basic application has been built, we can now set our sights onto making a priorities feature to store and display the priority of each note.

## Enumerations
An enumeration is a collection of constants. These constants within the enumeration is an object of the enumeration class, and thus, they can store methods.

In Kotlin, this is implemented via the `enum class` modifier.

```kotlin
enum class Color {
    RED, BLUE, GREEN;
}
```

### Properties

In a similar fashion to regular classes, enumerations can have access to a primary constructor which dictates the arguments that each constant has to pass to it.

```kotlin
enum class Animal(val color: String, val latinName: String) {
    Dog("brown", "Canis lupus familiaris"),
    Cat("black", "Felis catus"),
    Hamster("orange", "Cricetinae");
}
```

The following table depicts the association between the constant in an enumeration and the properties

|Constant|color|latinName|
|---|---|---|
|Dog|brown|Canis lupus familiaris|
|Cat|black|Felis catus|
|Hamster|orange|Cricetinae|

You can reference each constant in an enumeration via `Animal.Dog` and access the properties of the enumeration using `Animal.Dog.latinName`.

### Methods
You can also add methods to the enumeration that can be accessed by all of the constants in the enumeration.

```kotlin
enum class Color(val code: String) {
    Red("FF0000"),
    Green("00FF00"),
    Blue("0000FF");

    fun parseColor() = Color.parseColor(this.code)
}
```

### .valueOf() and .values()
The `valueOf()` method is available to all enumerations and it receives a string and attempts to match a corresponding constant to the name. 

```kotlin
val red = Color.valueOf("Red")
println(red.code) // This prints "FF0000"
```

The `values()` method is also available to all enumerations and it returns all the constants.

```kotlin
val colors = Color.values()
colors.forEach {
    print(it.code + " ") // This prints "FF0000 00FF00 0000FF"
}
```

### .name
Each constant has a property called `name` which represents the name of the constant.

```kotlin
val redName = Color.Red.name
println(redName) // This prints "Red"
```

## Priority Enumeration 
In our application, we will use an enumeration to store the list of pre-defined priorities available to users.

**Follow Along:**

1. Create a new file: `Priority.kt`
2. Add the following code:
   
   ```kotlin
   enum class Priority(val color: String) {
       High("FF1744"), 
       Medium("FFA726"), 
       Low("69F0AE");

       fun parseColor() = Color.parseColor("#${this.code}")
   }
   ```

**Break Down:**

We created an enumeration for the different levels of priorities, namely for the **High**, **Medium** and **Low** priority.

Each of the constant holds onto their RGB value for their respective color codes.

We then add a method that is available to all constants, which is the `parseColor` method, which uses Android's color library to parse a color by some String input following the hexadecimal format of representing RGB.

If we wanted to retrieve the parsed color code of the **High** priority, you can do so like this:

```kotlin
println("Color code of HIGH is: ${Priority.High.parseColor()}")
```

## Menu Items
Next, we want to add some menu options in the title bar of the application so as to provide users with the ability to customize and set the priority of each note.

**Follow Along:**

1. Create a new resource folder under the `res` folder and name it `menu`
2. Create a new file in the `res/menu` folder called `add_note_menu.xml`
3. Add the following code:
   
   ```xml
   <menu
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto">
	<item
		android:id="@+id/addNoteSetPriority"
		android:icon="@drawable/set_priority_icon"
		android:title="@string/set_priority"
		app:showAsAction="ifRoom" />
</menu>
```

4. Inside of the `AddNote.kt` file, add the following code below the `onSupportNavigateUp()` method:
   
   ```kotlin
   // ...
   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflator.inflate(R.menu.add_note_menu, menu)
       return true
   }
   ```

5. Add an instance variable to `AddNote.kt` called `priority`
   
   ```kotlin
   class AddNote : AppCompactActivity() {
       private var priority = Priority.Low
       
       // ...
   }
   ```

6. Lastly, below the `onCreateOptionsMenu()` method, add the following method:
   
   ```kotlin
   // ...
   override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       when (item?.itemId) {
           R.id.addNoteSetPriority -> {
                val choices = arrayOf(
                    "High", "Medium", "Low"
                    ) 

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Select Task Priority")
                dialogBuilder.setSingleChoiceItem(
                    choices,
                    Priority.valueOf(priority.name).ordinal
                ) { _, selected -> Priority.valueOf(selected) }
                dialogBuilder.setPositiveButton("Confirm") { _, _ -> }
                dialogBuilder.setCancelable(false)
                dialogBuilder.show()
           }
           android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
       }

       return true
   }
   ```

7. Run the application and you should be able to open up a dialog box allowing to select a priority level for a new note.

**Break Down:**

Firstly, we had to add a menu resource called `add_note_menu.xml`. This file holds on to the menu items that we want to add to the menu bar. Inside of it, we declared that the menu has a single item with the id of `addNoteSetPriority`, gave it an icon and a title. We also set the `showAsAction` attribute for the item to allow the menu item to be actively displayed on the menu bar.

Next, we need to link the newly created menu file with the AddNote menu bar. To do so, we override the `onCreateOptionsMenu()` method which dictates what items will be going into the menu bar. We utilise the **menuInflator** to inflate our previously created menu into the menu bar for the activity. This adds our menu items to menu bar.

Then, we create an instance variable to hold onto the priority of the current note, defaulting it to the **Low** priority. This is done as we have to pass to Firebase some kind of information about the priority and this is our way of doing so.

Lastly, we add some actions for the menu items to dictate what happens when a user presses on one of the menu items. We use a **when** statement to go through the selected menu item's id and if it matches up with either `R.id.addNoteSetPriority` or `android.R.id.home`, it will perform the respective set of actions.

When the user presses onto the set priority menu option, a dialog box will show up, prompting them to select a priority. For this, we use an AlertDialog, building it such that it only allows the users to select a single choice. The choices that are available will be the `choices` array that is given and the selected item will be based on the current value of the `priority` instance variable that we made previously. We then add a confirm button and show the AlertDialog.

When the user selects an option in the dialog box, the priority instance variable will be set to the selection.

## Connecting to Firebase
Now that we have created the menu options and priority system, we now want to connect it with Firebase so that every time we add a new note, it will store the priority as well.

**Follow Along:**

1. Edit the **Note** class such that it now includes a priority property, so the new class should look like:
   
   ```kotlin
   data class Note(
       val title: String,
       val content: String,
       val priority: Priority,
       var noteId: String
   ) {
       constructor() : this("", "", Priority.LOW, "") { }
   }
   ```

2. Then, we can edit the `addNote` functionality such that it adds a note with the priority attached to it. Go to `AddNote.kt` and add the following to the `onClickListener()` for the FAB.
   
   ```kotlin
   confirmNoteFAB.setOnClickListener {
       val title = addNoteTitle.text.toString()
       val content = addNoteContent.text.toString()
       addNoteToDatabase(Note(title, content, priority, ""))
       finish()
    } 
    ```

3. Clear all the entries in Firebase.
4. Run the application, and this time, when you set the priorities, it will be saved to Firebase as well.

**Break Down:**

We edited the **Note** data class to take in the priority of the note and edit the secondary constructor to reflect those changes.

Then when we are creating the note to be added to Firebase, we pass in the priority instance variable previously made.

## Updating RecyclerView
After all of the changes, we need to update the RecyclerView to display the priority of the note.

**Follow Along:**
1. Inside of the `NoteAdapter` class, add the following method to the `ViewHolder`
   
   ```kotlin
   fun setNotePriority(priority: Priority) {
       v.notePriorityColorBox.setBackgroundColor(priority.parseColor())
       v.notePriorityText.setTextColor(priority.parseColor())
       v.notePriorityText.text = priority.name
   }
   ```

2. Edit the `onBindViewHolder()` method in the adapter as well:
   
   ```kotlin
   // ...
   holder.setNotePriority(note.priority)
   ```

3. Run your application and this time, when you retrieve the notes, each of them should have the right priority color and name specified.

**Break Down:**

We created a method for the holder to set the priority of the note as well as make the changes to the color of the box and the text.

Then when we are binding the list item to the note, we set the priority.