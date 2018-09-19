# Listing Information
We first need to be able to display the todos into a **RecyclerView**. We leave the job of storing and retrieving data for later and focus on understanding what a RecyclerView is.

## What is a RecyclerView?
According to the [Android Developers page on RecyclerViews:](https://developer.android.com/guide/topics/ui/layout/recyclerview)

> If your app needs to display a scrolling list of elements based on large data sets (or data that frequently changes), you should use RecyclerView as described on this page.

There are other means to display sets of data in a list, much like a RecyclerView, such as ListView or GridView. However, we are not going to use these other options for the simple reason that RecyclerView was created as an improvement to ListView. (Reading found [here](https://stackoverflow.com/questions/26728651/recyclerview-vs-listview))

A RecyclerView is comprised of a key component, the **Adapter**, which is used to hold the data to be displayed as well as bind the data to what is known as a **ViewHolder**.

## Using a RecyclerView in the application
Now that some background has been established for what a RecyclerView is, we will proceed to implementing is in Kotlin for our application.

### Creating a data class
A data class is often defined as:

> A class that contains only fields and crude methods for accessing them, ie. getters and setters

We will use a data class to represent a single Todo or Note.

Traditionally, in Java, a data class can look something like this:

```java
public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // getters
    public double getX() { return x; }
    public double getY() { return y; }
    
    // setters
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
```

However, in Kotlin, there is a keyword focussed on creating these data classes, aptly named `data`.

1. Create a file called `Note.kt`
2. Inside of it, include the following code:

    ```kotlin
    data class Note(
        val title: String = "", 
        val content: String = ""
    )
    ```

Inside this code snippet, we have essentially created an entire data class. We do not need to fiddle with getters, setters and constructors. These are all done by Kotlin and we can simply declare a data class as such.