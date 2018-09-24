# Kotlin's History
## What is Kotlin?
Kotlin was a language developed by the [JetBrains team](https://github.com/JetBrains/kotlin) in 2011.

In 2017, Google had made it an official language for Android development, joining the ranks of Java and Dart.

Kotlin is a statically typed programming language that is built upon the Java Virtual Machine (JVM). 

## Why use Kotlin?
Whilst Java is not going anywhere, there are several reasons to use Kotlin over Java and some are specific to Android development whilst others aren't.

1. **Null-safety** 
   
   In Java, it is not uncommon to encounter NullPointerException caused by an object you forgot to initialize. 

   Kotlin, however, does not allow for a `null` piece of data unless explicitly specified with the `?` operator and handling potential null data also requires additional care. (More on this [here](kotlin_basics.md?id=null-safety))

   With such measures in place, the likelihood of an unsupervised NullPointerException decreases, reducing your headaches. 

2. **Compatability with existing libraries**
   
   As Kotlin is a language built on top of the JVM, you can use existing libraries written in Java as you normally would.

   If you create a library in Kotlin, it can be used with Java. This cross support makes developing in Kotlin a breeze as you have a wide selection of libraries available.

3. **Type inferrence**
   
   Whilst Kotlin is a statically typed language, it relies on the use of type inference to determine the data type of variables, without having to explicitly specify it. Java even implemented a similar concept in [Java 10](https://blog.codefx.org/java/java-10-var-type-inference/) but with many limitations.

4. **Open source**
   
   Kotlin is an open source language, allowing you to pick it apart and learn how it all works, as well as it encourages the open source movement.

5. **Libraries tailored to work with Kotlin**
   
   In Android, several tasks are extremely tedious to perform with Java, for instance, creating a Parcelable object with Java can dirty your code. 

   Kotlin, on the other hand, has several libraries that introduce nifty features to reduce this burden, such as introducing the `@Parcelize`.