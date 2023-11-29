# Regional Business Management Application

## A management system for simple operation across regions

- The application will provide a way to manage various **outlets/offices/stores for businesses across regions**. 
  The application will make use of two main classes, Facility (the non-trivial X class), which will have the fields and
  methods for operations within the facility, and Region (the non-trivial Y class), which will have the methods and 
  fields for various regions. The Region class will collect an arbitrary number of objects of the Facility class. Some 
  examples of functionality are **managing operation costs and revenue, transferring resources as and when needed 
  or adding new outlets**.
- **Anyone can use the software**, ranging from small business owners operating from their homes to big regional 
  corporations.
- I've always been interested in how businesses run and manage their resources judiciously and how they maximize 
  their profits.
  I want to develop this application to understand the inner workings better.
  Moreover, such a project is a practical implementation of software design in the corporate world.

## User Stories
- As a user, I'd want to be able to add and remove existing outlets (the non-trivial X class) to various regions
  (the non-trivial Y class).
- As a user, I'd want to be able to calculate revenue and expenses.
- As a user, I'd want to be able to view all the stores/outlets/offices and manage them, for example, increasing the 
  number of employees to a particular store in a particular region . 
- As a user, I'd want to be able to transfer resources or stock of various products from one store/outlet to another.
- As a user, I'd like to be able to save the information of the facilities.
- As a user, I'd like to be able to load the information I stored earlier.
- credit for the icon : 
<a href="https://www.flaticon.com/free-icons/budget" title="budget icons">Budget icons created by Freepik - Flaticon</a>


# Instructions for Grader

- You can add facilities to the region by bringing the mouse pointer to the "Add Facility" button on the left hand side
of the frame and clicking it. You'll be asked to enter a few particulars about the facility you want to add. 
- You can remove facilities from the region by bringing the mouse pointer to the "Remove Facility" button on the right 
hand side of the frame and clicking it. You'll be asked to enter the name of the facility you want to remove. 
- You can filter the facilities such that only profitable facilities are shown on the panel on the left. This can be 
done by clicking the "Show Profitable Facilities" button. You can click it again to show all the facilities.
- You can transfer resources from one facility to another by pressing the "Transfer Resources" button in the left panel.
- You can save the state of my application by clicking the "Save" button in the right panel.
- You can reload the last state of my application by clicking the "Load" button in the right panel.

# Phase 4: Task 3
- Looking at the final project and the UML class diagram, there are a few shortcomings with the projects that I'll like 
to address in the refactoring. The first would be using more classes to make the GUI, my GUI is all in one class, and I 
believe it does too much. It would be better if I could split it into different classes and split the work using the 
power of object-oriented design. I would make the 'Facility' an interface and implement different types of facilities. 
- Another thing I'd like to improve on is making the system more robust. I haven't made use of exceptions anywhere in my 
model classes, and have relied on REQUIRES clauses and the end-user to behave and input the permissible values. I'd 
like to take that burden away from the user and add exceptions.