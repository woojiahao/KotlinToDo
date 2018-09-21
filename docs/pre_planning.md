# Pre-Planning
With any project, we have to perform some pre-planning for the design of our application.

We know that the following should be present in our application (in order of importance):

1. Display the queried data in a list of sorts
2. Ability to record todos
3. Query the data from a data store
4. Add priorities to each of the todos

With these requirements in mind, we can gauge that we will need the following:

1. **Data store** - Firebase? MySQL?
2. **List View** - RecyclerView? ListView?
3. **Constants for each priority** - Enumeration? Interface?

For this application, we will be using **Firebase** to store the todos, a **RecyclerView** to list out the data and use a set of constants stored in an **enumeration** to represent each priority.

***

Next: [Listing Information](listing_information.md)