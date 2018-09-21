# Listing Information
We first need to be able to display the todos into a **RecyclerView**. We will leave the job of storing and retrieving data for later and focus on understanding what a RecyclerView is for now.

## What is a RecyclerView?
According to the [Android Developers page on RecyclerViews:](https://developer.android.com/guide/topics/ui/layout/recyclerview)

> If your app needs to display a scrolling list of elements based on large data sets (or data that frequently changes), you should use RecyclerView as described on this page.

There are other means to display sets of data in a list, such as ListView or GridView. However, we are not going to use these other options for the simple reason that RecyclerView was created as an improvement to these methods. (Reading found [here](https://stackoverflow.com/questions/26728651/recyclerview-vs-listview))

A RecyclerView is comprised of a key component, the **Adapter**, which is used to hold the data to be displayed as well as bind the data to what is known as a **ViewHolder**.

## Using a RecyclerView in the application
Now that some background has been established for what a RecyclerView is, we will proceed to implementing is in Kotlin for our application.

If you have donwloaded the [KotlinToDoBare](https://github.com/woojiahao/KotlinToDoBare), you will have already noticed how the layout files are already pre-packaged for you. Inside of it, you will notice a file `activity_home.xml` with the basic layout of the application completed, together with the [Floating Action Button](https://developer.android.com/guide/topics/ui/floating-action-button) and RecyclerView already added to the layout.

You will also find the layout file `note_card.xml`, which represents what each note will look like in the RecyclerView after it is filled with data.

### Creating a data class
A data class is often defined as:

> A class that contains only fields and crude methods for accessing them, ie. getters and setters

We will use a data class to represent a single Todo or Note.

Traditionally, in Java, a data class can look something like this:

```java
public class Note {
    private String title;
    private String content;

    public Coordinate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // getters
    public String getTitle() { return title; }
    public String getContent() { return content; }
    
    // setters
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}
```

However, in Kotlin, there is a keyword focussed on creating these data classes, aptly named `data`.

**Follow Along:**

1. Create a file called `Note.kt`
2. Inside of it, include the following code:

    ```kotlin
    data class Note(
        val title: String = "", 
        val content: String = ""
    )
    ```

**Break Down:**

With this code snippet, we have essentially created an entire data class. We do not need to fiddle with getters, setters and constructors. These are all done by Kotlin and we can simply declare a data class as such.

The data class for the Note will hold on to several pieces of information that we can build upon later, but the key pieces of information we need now is the note's **title** and **content**.

### Creating an adapter
We will now proceed to creating an adapter for the Notes that will be given to the RecyclerView.

**Follow Along:**

1. Create a file named `NoteAdapter.kt`
2. Inside of it, add the following:
   
   ```kotlin
   class NoteAdapter(
       private val context: Context, 
       private val notes: List<Note>
    ) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

        class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
            fun setNoteTitle(title: String) {
                v.noteTitle.text = title
            }

            fun setNoteContent(content: String) {
                v.noteContent.text = content
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) {
            val viewToPass = LayoutInflater
                                .from(context)  
                                .inflate(R.layout.note_card, parent, false)
            return ViewHolder(viewToPass)
        }

        override fun getItemCount() = notes.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val note = notes[position]
            holder.setNoteTitle(note.title)
            holder.setNoteContent(note.content)
        }
    }
        
   ```

**Break Down:**

As mentioned previously, the NoteAdapter will include a `ViewHolder` class which represents each list item that will hold the data in the RecyclerView.

When creating the adapter, we need to pass in 2 pieces of information, the context of the calling class as well as the list of notes that we want to be displayed.

The NoteAdpater class will extend the `RecyclerView.Adapter<E>` class as that is why to get your custom RecyclerView working with the existing methods and classes for `RecyclerView.Adapter`s.

When you extend the `RecyclerView.Adapter<E>` class, you will be required to override the following methods:

* `onCreateViewHolder` - Creates a ViewHolder to hold the data
  
  We will inflate the view of the `note_card.xml` so as to indicate to the ViewHolder that this is the view that I want all my note data to be stored in.

* `onBindViewHolder` - Binds a single item in the data list to a ViewHolder
  
  We will first retrieve the note at the position given to us, then we will use the helper methods in the `ViewHolder` class to set the note's title and content.

* `getItemCount` - Gets the size of the data list passed to the NoteAdapter
  
  We are just going to return the length of the `data` list we passed to the adapter during the creation of the adapter.

### Bringing it all together
We have created all the building blocks to use the RecyclerView.

**Follow Along:**

1. Inside `Home.kt`'s `init()` method, add the following:
   
   ```kotlin
   val notes = listOf(
       Note("Note 1", "Blah blah"),
       Note("Note 2", "Blah blah"),
       Note("Note 3", "Blah blah"),
       Note("Note 4", "Blah blah")
   )

   notesList.adapter = NoteAdapter(notes)
   notesList.layoutManager = LinearLayoutManager(
                                this, 
                                LinearLayoutManager.VERTICAL, 
                                false
                            )
   notesList.addItemDecoration(SpacingDecoration(0, 32, 1))
   ```

   You can feel free to tweak the contents of the notes, add notes and remove them to see the effect you will have on the displayed notes.

2. Run the application, you should see now that the RecyclerView in the home page is filled with notes that you created.

**Break Down:**

We first created a list of notes that would be given to the NoteAdapter.

We then set the adapter of the RecyclerView to a new instance of the NoteAdapter class and pass in the notes we created previously.

For every RecyclerView, we have to specify a layout manager, which simply dictates how the items in the RecyclerView will be layed out. We pick a **LinearLayoutManager** as we want all the information in a single row/column and when creating the LayoutManager, we specify that we want items in the manager to be ordered vertically.

Lastly, we use the pre-made `SpacingDecoration` class to properly space the items in the RecyclerView, this was a class I made to properly calculate padding between items in a RecyclerView, be it in a GridLayout or ListLayout.

**Kotlin Extensions:**

When making both the NoteAdapter and creating the RecyclerView, you might have noticed that we do not need to use `findViewById()` at all. That is because, Kotlin provides a set of extensions for Android that reduces these additional work for you.

> You can reference a component in the layout just by referencing the id of that component.

This holds several benefits, one being it enforcing the idea that all ids should be unique.

***

Next: [Storing and retrieving data](firebase.md)