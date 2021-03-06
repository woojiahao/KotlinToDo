# Kotlin Basics
Quick overview of the basic syntax of Kotlin. 

Kotlin is a monolith language with many nuances and it can take an entire day just to discuss them 
all, to learn more about the Kotlin's syntax, you can reference the 
[Kotlin documentation.](https://kotlinlang.org/docs/reference)

## Variables
Variables in Kotlin are declared relatively simply and in different ways with minute differences:
   
```kotlin
val x = 10
var str = "Hello"
val d: Double = 10.0
```

### Modifiers
#### val
The `val` keyword makes the variable `x` immutable, meaning that its value can no longer be changed 
from the time it was initialized. This is akin to the `final` keyword in Java. 

#### var
The `var` keyword makes the variable `str` mutable, meaning that its value can be re-assigned even 
after being initialized, this is akin to any normal variable you declare in Java.

### Data Types
You can explicitly specify the data type of a variable in Kotlin, using the `: <data type>` syntax, 
however, most of the time, this is not necessary as Kotlin provides 
[type inference](https://www.packtpub.com/mapt/book/application_development/9781787126367/2/ch02lvl1sec18/type-inference) 
meaning that it can determine the type of the variable just by the data you initialize it with.
  
This, however, is not a magic bullet as there might be instances where the compiler is unable to 
determine the type of a variable as it might not be initialized or it might be making use 
[generics.](https://kotlinlang.org/docs/reference/generics.html)

For example, if you are to define a mutable list without providing any predefined values to the 
list, the compiler is unable to determine what the type of the list will be, and thus, this has to 
be explicity specified:

```kotlin
val items = mutableListOf()                         // This is invalid
val items: MutableList<String> = mutableListOf()    // This is valid
```

**Note:** All data types in Kotlin are objects, so they all start with an uppercase.

#### Data type list
* Int
* Short 
* Long
* Double
* Float
* String
* Char
* Unit

#### Unit
`Unit` is a special kind of data type Kotlin has. It is used to represent a `void` return type or 
nothing.

It was included as it is key when concepts like [lambdas](kotlin_basics.md?id=lambdas) come into 
play.

### String interpolation
You can use variables and perform operations within a String using 
[String interpolation](https://kotlinlang.org/docs/reference/idioms.html)

```kotlin
val name = "John"
val age = 18
println("I am $name")
println("I am $age this year")
println("I will be ${age + 10} in 10 years time")
```

More on [Kotlin Variables.](https://kotlinlang.org/docs/reference/basic-syntax.html)

## Control Flow
Kotlin has very similar flow control as Java and any other common languages.

### if
`if` statements are used to check against a Boolean expression

```kotlin
val age = 18
if (age < 35) {
    println("Young one") // This prints "Young one"
} else {
    println("Old one")
}
```

### when
`when` statements are similar to `switch` statements in more traditional languages.

```kotlin
val selection = 1
when (selection) {
    0 -> println("You selected 0")
    1 -> println("You selected 1")
    2 -> println("You selected 2")
} // This prints "You selected 1"
```

### while
`while` loops are used to continually perform a set of actions while a Boolean condition is true.

```kotlin
val counter = 1
while (counter < 10) {
    print(counter + " ") // This prints "1 2 3 4 5 6 7 8 9"
    counter++
}
```

### for
`for` loops are used primarily to loop through a list of items in Kotlin.

```kotlin
val names = arrayOf(
    "John", "Mary", "Zoe", "Peter"
)
for (name in names) {
    println(name + " ") // This prints "John Mary Zoe Peter"
}
```

More on [Kotlin's Flow Control.](https://kotlinlang.org/docs/reference/control-flow.html) 

## Functions
Kotlin allows for functions to be declared in the global scope (not within any class), this makes 
creating utility functions a lot easier for Kotlin as they are not needed to be tied down into a 
single class.

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

Or if the method body is simply returning a value, you can omit the return type and the `return` 
keyword:

```kotlin
fun getName() = "John"
```

### Optional Parameters
Kotlin supports the use of optional parameters, which can be declared in this manner:

```kotlin
fun foo(val x: String = "", val y: Int = 0)
```

More on [Kotlin Functions.](https://kotlinlang.org/docs/reference/functions.html)

## Lambdas
A concept very prevalent in Kotlin is the idea of **lambdas**. In Kotlin, functions are considered 
[first-class citizens](https://stackoverflow.com/questions/5178068/what-is-a-first-class-citizen-function) 
which means that you are able to pass a function as an argument to another function, essentially, 
packaging a set of behavior for another function to use later on.

### Passing functions as arguments
This idea of passing functions from one method to another is not a new one, and is implemented in 
many languages, with varying degrees of complexity involved.

For instance, in Python and Javascript, you can just pass a function name to another function and 
execute it, no set up involved. 

```python
def baz(x):
    return x ** 2

def foo(bar):
    bar(10)

print(foo(baz)) #  This prints "100"
```

In the code above, the function `foo` receives a function as an argument and it will execute the 
function it receives, passing that function a value of 10. As you can see, the behavior of `baz` 
is given to foo so that is can be used later on.

### Implementing lambdas in Kotlin 
Lambdas require a specific format to be implemented in Kotlin. This format can be thought of as a 
contract, where the lambda initially creates a format that all functions to be passed have to follow 
([Function Type](https://kotlinlang.org/docs/reference/lambdas.html#function-types)), and then the 
functions to be passed to these parameters, will follow the format.

#### Function types

> A function type is a special notation that corresponds to the signatures of the function, i.e. 
their parameters and return values

A function type will be used to declare the **inputs** of the function and what this function will 
**output**.

```kotlin
fun foo(func: () -> Unit) {  }
```

In the example above, `func` is a function type. The function takes in no parameters, as seen by 
the `()` and it returns nothing, via `Unit`. Other types of function types can look like:

```kotlin
fun foo(func: (Int) -> Int) {  }
```
 
A function that takes an integer and returns an integer.

```kotlin
fun foo(func: (String, Int) -> String) {  }
```

A function that takes a string and an integer and returns a string.

#### Creating lambdas
After you have created a function type, you can now pass a lambda to the function.

It is important to note that all lambdas are enclosed within `{}`.

```kotlin
fun foo(func: (Int) -> Int) = func(10)
println(foo({ input -> input * 2 })) // This prints "20"
```

In this case, the lambda defines the behavior where the input received will be doubled. And `foo` 
passes an input of 10, thus, the program prints 20.

### it
In cases where the function type only takes in 1 input, the initial declaration of the input name 
can be omitted as such and referred to using the `it` keyword:

```kotlin
fun foo(func: (Int) -> Int) = func(10)
println(foo({ it * 2 })) // This prints "20"
```

### Lambdas as the last argument
If a function type is the last parameter of a function, or the only parameter of the function, they 
can be declared outside of the parantheses, `()` and in the case where a function type is the only 
parameter, the parantheses can be completely omitted.

```kotlin
fun foo(name: String, age: String, func: (Int) -> Int) =
    "My name is $name, I am $age years old," + 
    " if you take my age and do some magic, you get ${func(age)}"

// This prints:
// "My name is John, I am 18 years old, if you take my age and do some magic, you get 39" 
println(
    foo("John", 18) {
        (it * 2) + 3
    }
) 
```

In that example, since `func` was the last parameter, it was moved outside of the parantheses.

```kotlin
fun foo(func: (String, Int) -> String) = func("John", 18)

// This prints:
// "I am John, I am 18 years old"
println(
    foo {
        name, age ->
            "I am $name, I am $age years old"
    }
)
```

In that example, we completely omitted the use of parantheses because the function type was the only 
parameter for `foo`.

More on [Kotlin Lambdas.](https://kotlinlang.org/docs/reference/lambdas.html#higher-order-functions-and-lambdas)

## Classes
Classes do not require a class body if there are no additional methods or instance variables in the 
class.

### Constructors
Classes have 2 types of constructors, a **primary** constructor, declared in the class header, as 
well as a **secondary** constructor, declared in the class body.

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

When making a secondary constructor, if you have also declared a primary constructor, you **must** 
call the primary constructor in the secondary constructor, using the `this` keyword.

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

## Null Safety
Like mentioned previously, Kotlin enforces the concept of null safety, where you are not able to 
create or assign null to objects unless you use a specific operator.

### ?
The `?` operator is used to denote that a data type is nullable. For instance:

```kotlin
val name = null             // This fails as String is not nullable
val name: String? = null    // This works as String is now nullable
```

### !!
The `!!` operator will attempt to convet any value to a non-null type and if the value is actually 
null, it will throw an error.

```kotlin
val name: String? = null
println(name!!.length)      // This throws an error as name is null
```

### ?.
If you do not want the value to be converted to a non-null type or do not want the program to throw 
an error if the value is null, you can use `?.` or **safe calls** instead. 

Using the operator in that order will make it so any value that is not null will perform the 
following action, otherwise, the expression will return null, which can be handled later on.

```kotlin
var name: String? = null
println(name?.length)       // This prints null
name = "Bob"
println(name?.length)       // This prints "3"
```

#### let
The method `let` pairs well with the concept of safe calls as it will only execute the body if the 
value is not null.

```kotlin
val names: Array<String?> = arrayOf(
    "John", null, "Bob"
) 
names.forEach {
    it?.let { val -> print(val) } // This prints "JohnBob", ignoring null
}
```

### ?:
The `?:` or **elvis operator** is used when you want to retrieve the value of a data and perform a 
specific action if the value is null.

This can be done to return from a function if the value is null.

```kotlin
fun foo(i: String?) {
    i ?: return

    println(i)
}

foo(null) // This prints nothing
foo("Hi") // This prints "Hi"
```

More on [Kotlin Null Safety.](https://kotlinlang.org/docs/reference/null-safety.html)