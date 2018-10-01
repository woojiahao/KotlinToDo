# Improving KotlinToDo
We have built the basics of the application. We can now move our attention to improving the existing code using some of Kotlin's more advanced and interesting features.

We will be using this new knowledge to build out a filtering system for the notes as well.

## Collections
In Kotlin, there are a myriad of methods built for collections of information that makes it a lot easier to manipulate and deal with structures like lists or maps.

This is akin to the [stream API](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html) that was introduced in Java 8.

The core 3 methods used will be **.forEach()**, **.map()** and **.filter()**

These are advantageous to use over traditional means as it offers a far more straight-forward approach to list manipulation and reading chains of these methods are a lot easier to digest than when everything is embedded within a for loop. [This video](https://youtu.be/1OpAgZvYXLQ) goes into the advantages of using such constructs.

### .forEach()
`forEach()` will loop through an entire list whilst you supply some set of behavior to execute for each element in the list.

This is a way to reduce a for loop into a much clearer and more concise construct.

**Traditional approach:**

```kotlin
val fruits = listOf(
    "Apple", "Orange", "Mango", "Banana", "Grapes",
    "Strawberry", "Watermelon", "Blueberry", "Pears"
)

for (fruit in fruits) {
    println("The fruit is: ${fruit}")
}
```

**Using .forEach()**

```kotlin
fruits.forEach { println("The fruit is: ${it}")}
```

In both cases, it will print **"The fruit is: &lt;fruit name&gt;"** for every fruit in the list. 

### .map()
`map()` will loop through a list of data and return a new list of data based on a set of actions that to be performed on the original list of data.

This is useful when you wish to perform the same action on every item in a list.

**Traditional approach:**

```kotlin
val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

val newNumbers = mutableListOf<Int>()

for (number in numbers) {
    newNumbers.add(number * 2)
}
```

**Using .map()**

```kotlin
val newNumbers = numbers.map { it * 2 }
```

In both cases, `newNumbers` is a new list of numbers with each number being twice of that in the original `numbers` list ([2, 4, 6, 8, 10, 12, 14, 16, 18, 20]). 

### .filter()
`filter()` will loop through a list of data and return a new list that consists of the elements of the original list that passes a Boolean condition.

**Traditional approach:**

```kotlin
val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

val evenNumbers = mutableListOf<Int>()

for (number in numbers) {
    if (number % 2 == 0) {
        evenNumbers.add(number)
    }
}
```

**Using .filter()**

```kotlin
val evenNumbers = numbers.filter { it % 2 == 0 }
```

In both cases, the even numbers is extracted from the original list of numbers and added to the new list, `evenNumbers`. 

### Chaining methods
These collection methods are not just one-use only. They can be chained one after the other to form method chains that perform a set of sequential operations on a list of data.

```kotlin
val fruits = listOf(
    "Apple", "Orange", "Mango", "Banana", "Grapes",
    "Strawberry", "Watermelon", "Blueberry", "Pears"
)

// I want to get all fruits that starts with "B" 
//  and add the word "Not" before each of them 
//  and print this new list

fruits
    .filter { it.startsWith("B") }
    .map { "Not ${it}" }
    .forEach { println("This is ${it}") }
```

## Using Collections in KotlinToDo
There are several places in the application that we can replace with Collection methods. 

### Firebase Operations
Notice that in the `getNotes() > onDataChange()` method of our Firebase access class, we use looping to load the notes. However, this can be simplified using the previously discussed methods.

**Original:**

```kotlin
override fun onDataChange(ds: DataSnapshot) {
    val notes = mutableListOf<Note>()
    for (child in ds.children) {
        val note = child.getValue(Note::class.java)
        note?.let { notes.add(it) }
    }

    onComplete(notes)
}
```

**New:**

Firstly, the `for` loop is sticking out like a sore thumb, thus we will replace it with a `forEach()` call.

```kotlin
override fun onDataChange(ds: DataSnapshot) {
    val notes = mutableListOf<Note>()
    ds.children.forEach {
        val note = it.getValue(Note::class.java)
        note?.let { n -> notes.add(n) }
    }

    onComplete(notes)
}
```

Next, since `notes` is simply a mapped collection of the `ds.children` list, we can use `map()`

```kotlin
override fun onDataChange(ds: DataSnapshot) {
    val notes = ds
            .children
            .map { it.getValue(Note::class.java) }
            .filter { it != null }
    
    onComplete(notes)
}
```

Then, there's a special form of `map()` called `mapNotNull()` which creates a map of data where each item in the map is not null, as we are performing what is essentially a **non-null** check on the note before adding it to the map, we can make use of this form of map.

```kotlin
override fun onDataChange(ds: DataSnapshot) {
    val notes = ds.children.mapNotNull { it.getValue(Note::class.java) }
    onComplete(notes)
}
```

Lastly, we are not using `notes` in any other place other than as an argument for `onComplete` thus, we can remove the variable and simply pass the `onComplete` method the map.

```kotlin
override fun onDataChange(ds: DataSnapshot) =
    onComplete(ds.children.mapNotNull { it.getValue(Note::class.java) })
```

And with that, we've successfully converted an otherwise long and tedious process of retrieving the stored notes into a concise and easy to read one-line function.

### Ordering/Filtering Notes
We want to be able to filter our notes displayed based on certain criteria, namely, ordering them by title and filtering them by priority.

**Follow Along:**

Like when we were making the set priority feature, we want to add a menu item to the Home activity action bar to allow users to filter/order the data.

1. Add a new menu resource file, `home_menu.xml` to represent the filter item and add the following:
   
   ```xml
   <?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto">
	<item
		android:id="@+id/homeFilterNotes"
		android:icon="@drawable/sort_icon"
		android:title="@string/filter_notes"
		app:showAsAction="ifRoom" />
</menu>
```

2. After that, we will make another menu resource file, `filter_menu.xml` to represent the set of actions users can do:
   
   ```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
	<item
		android:id="@+id/filterNameAscending"
		android:title="@string/ascending_name" />

	<item
		android:id="@+id/filterNameDescending"
		android:title="@string/descending_name" />

	<group android:id="@+id/filterPriorityGroup">
		<item
			android:id="@+id/filterHighPriority"
			android:title="@string/high_priority" />
		<item
			android:id="@+id/filterMediumPriority"
			android:title="@string/medium_priority" />
		<item
			android:id="@+id/filterLowPriority"
			android:title="@string/low_priority" />
	</group>
</menu>
```

3. Then we attach the menu to the action bar in Home as well as create a popup menu for whenever the user presses on the menu item.
   
   ```kotlin
   // ...
   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.home_menu, menu)
       return true
   } 

   override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       when (item?.itemId) {
           R.id.homeFilterNotes -> {
               val filterIconView = findViewById<View>(R.id.homeFilterNotes)
               val popupMenu = PopupMenu(this, filterIconView)
               popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)
               popupMenu.show()
           }
       }
       return true
   }
   ```

4. We can then declare an instance variable to hold onto the notes when they are loaded from Firebase and pass this instance variable to the NoteAdapter
   
   ```kotlin
   class Home : AppCompactActivity() {
       private val notes = mutableListOf<Note>()

       //...
       private fun init() {
            notesList.addItemDecoration(SpacingDecoration(0, 32, 1))
            notesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            getNotes {
                notes.addAll(it)
                notesList.adapter = NoteAdapter(this, notes)
            }
       }
   }
   ```

5. Next, we will begin to implement the logic for the filtering of notes:
   
   ```kotlin
   // inside the 'when' in onOptionsItemSelected
   // ...
   popupMenu.setOnMenuItemClickListener {
       when (it.itemId) {
           R.id.filterNameAscending -> notes.sortBy { it.title }
           R.id.filterNameDescending -> notes.sortByDescending { it.title }
           R.id.filterHighPriority -> filterPriority(Priority.High)
           R.id.filterMediumPriority -> filterPriority(Priority.Medium)
           R.id.filterLowPriority -> filterPriority(Priority.Low)
       }

       notes.notifyDataSetChanged()
       true
   }

   // ... outside of onOptionsItemSelected
   private fun filterPriority(priority: Priority) {
       val filtered = notes.filter { it.priority = priority }
       notes.clear()
       notes.addAll(filtered)
   }
   ```

6. Run the application and try filtering the notes

**Break down:**

Like the last time, we are going to add a menu resource file for the menu item in the Home action bar. We also add a new menu resource file for the list of options that will be displayed to users when they tap on the menu item.

The options for filtering is displayed using a popup menu. When making the popup menu, we had to find a view to anchor it to, thus we used `findViewById` to retrieve the menu item.

Then, we moved the `notes` list outside of the `init()` method as we want to be able to use it outside of that method.

For sorting the notes by name, we use the `.sortBy()` and `.sortByDescending()` methods, sorting them by the title.

For the filtering, we are simply going to filter the notes list with the condition that only notes with the priority assigned to the selected filter will be kept. For the new notes to be the only thing displayed, we have to clear out the original set of notes and re-populate the notes list with the filtered set of notes.

After we manipulate the contents of `notes`, we are going to call the method `notifyDataSetChanged()` which will inform the RecyclerView to refresh the layout with the new set of data, effectively, sorting/filtering the data.