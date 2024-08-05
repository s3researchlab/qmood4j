# Requirements

You have to install Maven on your computer.

## MacOS

The easiest way is to use HomeBrew

```sh
brew install maven
```

If everything went well, type the following command and see the version

```sh
mvn --version
```

# Definitions:

 - **Non-inherited Class:** It is a base class or a root class in the hierarchy. This class does not have `extends` statement.
 - **User-defined Classes** These are classes created by the developer in the system under test.  We define it as non-primitive types that are not included in the Java standard libraries

# QMOOD Metrics

## Design Metrics

### Design Size in Classes (DSC)

 - **Design Property:** Design Size
 - **Description:** A measure of the number of classes used in a design
 - **Interpreted as:** It is the total number of user-defined classes (not interfaces) in the design, excluding both imported libraries and standard Java classes
 - **Calculated As:** Number

### Number of Hierarchies (NOH)

 - **Design Property:** Hierarchies
 - **Description:** Hierarchies are used to represent different generalization-specialization concepts in a design
 - **Interpreted as:** It is a count of the number of non-inherited classes that have children in the design
 - **Calculated As:** Number

### Average Number of Ancestors (ANA)

 - **Design Property:** Abstraction
 - **Description:** A measure of the generalization-specialization aspect of the design.
 - **Interpreted as:** It counts the average number of classes from which a user-defined class inherits information. If the parent class is from an imported library, then the search stops counting the imported class as the last one.
 - **Calculated As:** Average Number

### Data Access Metric (DAM)

 - **Design Property:** Encapsulation
 - **Description:** Defined as the enclosing of data and behavior within a single construct. In object-oriented designs the property specifically refers to designing classes that prevent access to attribute declarations by defining them to be private, thus protecting the internal representation of the objects.
 - **Interpreted as:** It counts the average across all user-defined classes with at least one attribute, of the ratio of non-public to total attributes in a class
 - **Calculated As:** Average Number

### Direct Class Coupling (DCC)

 - **Design Property:** Coupling
 - **Description:** Defines the interdependency of an object on other objects in a design. It is a measure of the number of other objects that would have to be accessed by an object in order for that object to function correctly.
 - **Interpreted as:** It counts the average across all user-defined classes of the number of distinct user-defined classes a class is coupled to by method parameter or attribute type. We exclude standard Java library classes from the computation. Generic Types are also considered if the extending type is provided.
 - **Calculated As:** Average Number

### Cohesion Among Methods of Class (CAM)

 - **Design Property:** Cohesion
 - **Description:** Assesses the relatedness of methods and attributes in a class. Strong overlap in the method parameters and attribute types is an indication of strong cohesion
 - **Interpreted as:** It is computed using the summation of the intersection of parameters of a method with the maximum independent set of all parameter types in the class. We have excluded constructors, and implicit 'this' parameters from the computation. We exclude standard Java library classes from the computation. Generic Types are also considered if the extending type is provided.
 - **Calculated As:** Average Number

### Measure of Aggregation (MOA)

 - **Design Property:** Composition
 - **Description:** Measures the "part-of," "has," "consists-of," or "part-whole" relationships, which are aggregation relationships in an object-oriented design
 - **Interpreted as:** It is a count of the number of data declarations whose types are user-defined classes. Interpreted as the average value across all design classes.
 - **Calculated As:** Average Number

### Measure of Functional Abstraction (MFA)

 - **Design Property:** Inheritance
 - **Description:** A measure of the "is-a" relationship between classes. This relationship is related to the level of nesting of classes in an inheritance hierarchy. The ratio of the number of methods inherited by a class to the number of methods accessible by member methods of the class.
 - **Interpreted as:** Interpreted as the average across all classes in a design of the ratio of the number of methods inherited by a class to the total number of methods available to that class, i.e. inherited and defined methods. Static and Abstract methods are not counted. Super classes from libraries also not considered.
 - **Calculated As:** Average Number

### Number of Polymorphic Methods (NOP)

 - **Design Property:** Polymorphism
 - **Description:** The ability to substitute objects whose interfaces match for one another at run-time. It is a measure of services that are dynamically determined at run-time in an object. A count of the number of the methods that can exhibit polymorphic behavior.
 - **Interpreted as:** Interpreted as the average across all classes, where a method can exhibit polymorphic behavior if it is overridden by one or more descendent classes
 - **Calculated As:** Average Number


### Class Interface Size (CIS)

 - **Design Property:** Messaging
 - **Description:** A count of the number of public methods that are available as services to other classes. This is a measure of the services that a class provides.
 - **Interpreted as:** It is a count of the number of public methods in a class, including abstract and static.
 - **Calculated As:** Average across all classes in a design

### Number of Methods (NOM)

 - **Design Property:** Complexity
 - **Description:** A measure of the degree of difficulty in understanding and comprehending the internal and external structure of classes and their relationships.
 - **Interpreted as:** A count of all the methods defined in a class, including abstract and static.
 - **Calculated As:** Average across all classes in a design

## Quality Attributes

• Reusability : the degree to which a software module or
other work product can be used in more than one computer
program or software system.

• Flexibility: the ease with which a system or component
can be modified for use in applications or environments
other than those for which it was specifically designed.

• Understandability: the properties of designs that enable
it to be easily learned and comprehended. This directly
relates to the complexity of design structure.

# FAQ

 1. <details> <summary>Does this project count Java Interfaces?</summary>No, it does not. Only Java Classes are considered in all metrics.</details>
