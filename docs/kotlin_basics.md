# Kotlin Basics
Quick overview of the basic syntax of Kotlin, more about the nuances in Kotlin's syntax can be found in the [Kotlin documentation.](https://kotlinlang.org/docs/reference)

## Variables
Variables in Kotlin are declared relatively simply and in different ways with minute differences:
   
```kotlin
val x = 10
var str = "Hello"
val d: Double = 10.0
```

### Modifiers
#### val
The `val` keyword makes the variable `x` immutable, meaning that its value can no longer be changed from the time it was initialized. This is akin to the `final` keyword in Java. 

#### var
The `var` keyword makes the variable `str` mutable, meaning that its value can be re-assigned even after being initialized, this is akin to any normal variable you declare in Java.

### Data Types
You can explicitly specify the data type of a variable in Kotlin, using the `: <data type>` syntax, however, most of the time, this is not necessary as Kotlin provides [type inference](https://www.packtpub.com/mapt/book/application_development/9781787126367/2/ch02lvl1sec18/type-inference) meaning that it can determine the type of the variable just by the data you initialize it with.
  
This, however, is not a magic bullet as there might be instances where the compiler is unable to determine the type of a variable as it might not be initialized or it might be making use [generics.](https://kotlinlang.org/docs/reference/generics.html)

### String interpolation
You can use variables and perform operations within a String using [String interpolation](https://kotlinlang.org/docs/reference/idioms.html)

```kotlin
val name = "John"
val age = 18
println("I am $name")
println("I am $age this year")
println("I will be ${age + 10} in 10 years time")
```

More on [Kotlin Variables.](https://kotlinlang.org/docs/reference/basic-syntax.html)

## Functions
Kotlin allows for functions to be declared in the global scope (not within any class), this makes creating utility functions a lot easier for Kotlin as they are not needed to be tied down into a single class.

### General Functions
```kotlin
fun printContents(data: Array<String>) {
    for (content in data) {
        println(content)
    }
}
```

### Functions With Return Values
```kotlin
fun getName(): String {
    val infoMap = mapOf("name" to "John", "age" to "19")
    return infoMap["name"]
}
```

Or if the method body is simply returning a value, you can omit the return type and the `return` keyword:

```kotlin
fun getName() = "John"
```

### Optional Parameters
Kotlin supports the use of optional parameters, which can be declared in this manner:

```kotlin
fun foo(val x: String = "", val y: Int = 0)
```

More on [Kotlin Functions.](https://kotlinlang.org/docs/reference/functions.html)

## Classes
Classes do not require a class body if there are no additional methods or instance variables in the class.

### Constructors
Classes have 2 types of constructors, a **primary** constructor, declared in the class header, as well as a **secondary** constructor, declared in the class body.

```kotlin
// Primary constructor
class Cat(val name: String, val breed: String, val age: Int) {
    // Secondary constructor
    constructor(val name: String, val age: Int)
        : this(name, "Shorthair", age) { }

    fun greet() = 
        "Hello there, I am $name, I am a $age year old $breed"
}
```

When making a secondary constructor, if you have also declared a primary constructor, you **must** call the primary constructor in the secondary constructor, using the `this` keyword.

### Creating an object
There is no `new` keyword in Kotlin, so we simply create an object using:

```kotlin
val tim = Cat("Tim", 10)
```

### Accessing data
Accessing class data is similar to that in Java:

```kotlin
println(tim.name)
println(tim.greet())
```

More on [Kotlin Classes.](https://kotlinlang.org/docs/reference/classes.html)