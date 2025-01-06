# My Personal Project

## Who, What, and Why?

My application will be a book tracker for the user to **add, rate, link, and sort**
books they have read. This project is designed for *anyone who is an active reader*.
It may also be useful to students who would like to use it for keeping track
of the online journal articles they have read, or textbooks to keep track of. I am interested in this
project because I would like a simple application to record my reading history.
Not only would it be helpful to look back on, but it would be fun to see my past ratings
for the books.

## User Stories
- I want to be able to add a book to my book-list and record the start and end date
- I want to be able to view my reading history
- I want to be able to rate a book
- I want to mark a book as read or unread
- I want to be able to link a PDF or website to the corresponding book or article
- I want to be able to remove a book from my book-list
- I want to be able to save my book-list to file (if I choose)
- I want to be able to load my book-list from file (if I choose)
- I want to be able to add multiple books to a book list
- I want to be prompted with the option to load data from file when the application starts and prompted with the option to save data to file when the application ends

# Instructions
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by pressing "Add Book" after entering a book title and other book attributes
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by selecting a book in the list and pressing "Remove Book" to delete a book
- You can locate my visual component by adding a book, which displays an image
- You can save the state of my application by pressing "Save Booklist"
- You can reload the state of my application by pressing "Load Booklist"

# For Future Reference
If I were to improve this project, I would add a few exception classes to handle the different exceptions that
could arise when running the program. For example, entering random characters as a book rating would not make sense,
so creating an exception, such as a "RatingException" could help. In addition, using an iterator for the BookList class
may be a good refactor change. This could simplify the code, since iterating over the book-list is repeated several times.
Lastly, I would also break down the GUI class into several different classes, perhaps for the buttons and text boxes. Currently,
the single class is slightly long, so spreading it out in a few classes would enhance readability.
