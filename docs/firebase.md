# Storing And Retrieving Data
Whilst there are other alternatives to storing data for our application, one that is widely used for mobile applications is the use of [Firebase.](https://firebase.google.com/?)

Firebase offers more than just a simple way of storing and retrieving data, it provides easy to use libraries for the service for many platforms and it provides services aside from data storage, such as user authentication and providing developers with means to connect users with login services like Gmail.

## Firebase's Structure
Firebase provides a [NoSQL realtime database](https://firebase.google.com/docs/database/) which uses a format very similar to JSON or a dictionary, where each value is paired with a key, similar to a format like this:

```json
{
    "name": "John Doe",
    "age": 18,
    "occupation": "Waiter"
}
```

## Setting Up Firebase
1. Navigate to the [Firebase website](https://firebase.google.com/?)
2. Create a new project, named **KotlinToDo**
3. When the project has been created, you will need to add Android support to the project
4. For the project root, you will have to set it to be `chill.me.kotlintodo` and you can leave the rest as it is
5. Follow the remainder of the instructions and you should be able to test if your application is connected to Firebase

## Storing Data
We will need to store the notes we add to Firebase and later, retrieve them.

**Follow Along:**

1. Edit the `Note` data class by adding an additional property, a String for a note's ID:
   
   ```kotlin
   data class Note(
       val title: String, 
       val content: String,
       var noteId: String
   )
   ```

   We make the `noteId` property a var as this will be re-assigned later on

2. Add a class body for the `Note` data class and create an empty secondary constructor for the Note data class:
   
   ```kotlin
   //... {
       constructor() : this("", "", "") {}
   }
   ```

3. Create a new file called `FirebaseOperations.kt`
4. Add the following into the file:
   
   ```kotlin
   fun addNoteToDatabase(note: Note) {
       val noteNode = FirebaseDatabase.getInstance()
                                        .reference
                                        .child("notes")
                                        .push()
       note.noteId = noteNode.key
       noteNode.setValue(note)
   }
   ```

5. Within the `AddNote.kt`'s `connectListeners()` method, add the following to connect an onclick action to the FAB
   
   ```kotlin
   confirmNoteFAB.setOnClickListener {
       val title = addNoteTitle.text.toString()
       val content = addNoteContent.text.toString()
       addNoteToDatabase(Note(title, content, ""))
       finish()
   }
   ```

6. When you re-run the application, you will be able to view in Firebase's realtime database that a new node is added to the `notes` node every time you add a new note.

**Break Down:**

We had to add a new property to the `Note` data class as each note when stored into Firebase needs to have a special key aka id to reference that note.

We then include an empty secondary constructor as that is something Firebase requires for the objects that we will store into the its realtime database. Notice that we have to make a call to the primary constructor in the secondary constructor, this is a behavior seen for every secondary constructor as long as the primary constructor is not an empty one.

After configuring the `Note` data class to work with Firebase, we then turn our attention to creating a function to add a note to the realtime database. This function will take in the note to be added, create a key for this note, and then add the note to that key.

Luckily, we do not have to generate a unqiue key for each note ourselves, Firebase can do so by using the `push()` method, which creates a key-value pair in the realtime database, where the value is empty. The keys for these pairs will have a unique key attached to it, which we can extract using the `key` property. Since we need each note to have a reference to this unique key, we set the `noteId` property of the note to add to be this key. Once everything is done, we set the value of the key-value pair to be the note we received. Thanks to the prior configurations made to the `Note` data class, Firebase is able to properly convert the note into a value for the realtime database.

For the `AddNote` activity to successfully add a new note, we simply add a click listener to the FAB and within it, we extract the title and content of the note from the EditTexts in the activity and use the helper function made for adding notes to Firebase.

Once the note is added, we want to close out the AddNote activity as a simple quality of life improvement.

## Retrieving Data
Now that we have devised a strategy to store note data into Firebase, we have to be able to retrieve it and load up this notes into the RecyclerView we created previously. 

1. Inside `FirebaseOperations.kt` add the following function:
   
   ```kotlin
   fun getNotes(onComplete: (List<Note>) -> Unit) { 
        FirebaseDatabase.getInstance().reference.child("notes")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(e: DatabaseError) = Unit

                override fun onDataChange(ds: DataSnapshot) {
                    val notes = mutableListOf<Note>()
                    for (child in ds.children) {
                        val note = child.getValue(Note::class.java)
                        note?.let { notes.add(it)  }
                    }

                    onComplete(notes)
                }
            })
   }
    ```

2. Inside `Home.kt` replace the code in `init()` with this:
   
   ```kotlin
   notesList.addItemDecoration(SpacingDecoration(0, 32, 1))
   notesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
   getNotes {
       notesList.adapter = NoteAdapter(this, it)
   }
   ```

3. Run the application and it should be retrieving the stored notes in Firebase and displaying them in the RecyclerView.

**Break Down:**

We have to create a function to retrieve the data from Firebase. We use the `addListenerForSingleValueEvent` since this is a one-time use method and fits our needs perfectly.

When we receive all the key-value pairs in the `notes` node in the realtime database, we need to create a list of the notes that is stored in that node by iterating through the children of that node.

A data snapshot is an instance of what the values of a node looks like in the realtime database at the time of the retrieval.

We then retrieve each child as a `Note` and add it to the list. Once the notes are all retrieved, they are used for the `onComplete` function. 

We make use of a lambda in this instance as we do not know what we might be doing with the loaded set of notes but we want to retain the fact that we want to be able to perform some action when we get the notes.

This behavior is utilised in the **Home** activity when we change the way the RecyclerView is loaded. Instead of loading in a set of pre-made data, we load in the data from Firebase by using the `getNotes` function we have created previously and as the `onComplete` behaviour, we set it to utilise the list of notes that was loaded to fill the RecyclerView's note adapter and display them.