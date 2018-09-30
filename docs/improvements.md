# Improving KotlinToDo
We have built the basics of the application. We can now move our attention to improving the existing code using some of Kotlin's more advanced and interesting features.

We will be using this new knowledge to build out a filtering system for the notes as well.

## Collections
In Kotlin, there are a myriad of methods built for collections of information that makes it a lot easier to manipulate and deal with structures like lists or maps.

This is akin to the [stream API](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html) that was introduced in Java 8.

The core 3 methods used will be **.forEach()**, **.map()** and **.filter()**

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

In both cases, it will print **"The fruit is: &lt;fruit name&gt;"** for every fruit in the list. However, using the `forEach()` method makes the intent a lot clearer and the code is a lot more concise.

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

In both cases, `newNumbers` is a new list of numbers with each number being twice of that in the original `numbers` list ([2, 4, 6, 8, 10, 12, 14, 16, 18, 20]). However, when you use the `map()` method, your intent is a lot clearer and you do not need to set up a for loop just to go through every item in the list and adding them manually to the new list.

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

In both cases, the even numbers is extracted from the original list of numbers and added to the new list, `evenNumbers`. However, by using the `filter()` method, you are able to define the same behavior in not only less lines, but also have a much clearer method of delivering the purpose of the code.

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

val notBFruits = fruits
                .filter { it.startsWith("B") }
                .map { "Not ${it}" }
                .forEach { println("This is ${it}") }
```