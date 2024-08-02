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
 - **User-defined Classes** These are classes created by the developer in the system under test.

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
 - **Interpreted as:** It counts the average across all user-defined classes of the number of distinct user-defined classes a class is coupled to by method parameter or attribute type. We exclude standard Java library classes from the computation 
 - **Calculated As:** Average Number

| Design Property| Acronym | Name | Description | Interpreted As|
|--|:--:|--|--|--|
|  |  |  |  |  |
|  |  |  |  | |
| Cohesion | CAM | Cohesion among methods of class | Assesses the relatedness of methods and attributes in a class. Strong overlap in the method parameters and attribute types is an indication of strong cohesion | It is computed using the summation of the intersection of parameters of a method with the maximum independent set of all parameter types in the class. We have excluded constructors, and implicit ‘this’ parameters from the computation |
| Composition | MOA | Measure of aggregation | Measures the "part-of," "has," "consists-of," or "part-whole" relationships, which are aggregation relationships in an object-oriented design | It is a count of the number of data declarations whose types are user-defined classes. Interpreted as the average value across all design classes. We define ‘user defined classes’ as non-primitive types that are not included in the Java standard libraries |
| Inheritance | MFA | Measure of Functional Abstraction | A measure of the "is-a" relationship between classes. This relationship is related to the level of nesting of classes in an inheritance hierarchy. The ratio of the number of methods inherited by a class to the number of methods accessible by member methods of the class. | Interpreted as the average across all classes in a design of the ratio of the number of methods inherited by a class to the total number of methods available to that class, i.e. inherited and defined methods. Static and Abstract methods are not counted. Super classes from libraries also not considered |
| Polymorphism | NOP | Number of Polymorphic Methods | The ability to substitute objects whose interfaces match for one another at run-time. It is a measure of services that are dynamically determined at run-time in an object. A count of the number of the methods that can exhibit polymorphic behavior. | Interpreted as the average across all classes, where a method can exhibit polymorphic behavior if it is overridden by one or more descendent classes |
| Messaging | CIS | Class Interface Size | A count of the number of public methods that are available as services to other classes. This is a measure of the services that a class provides. | It is a count of the number of public methods in a class. Interpreted as the average across all classes in a design |
| Complexity | NOM | Number of Methods | A measure of the degree of difficulty in understanding and comprehending the internal and external structure of classes and their relationships. | A count of all the methods defined in a class. Interpreted as the average across all classes in a design |

## Quality Attributes

# FAQ

 1. <details> <summary>Does this project count Java Interfaces?</summary>No, it does not. Only Java Classes are considered in all metrics.</details>
