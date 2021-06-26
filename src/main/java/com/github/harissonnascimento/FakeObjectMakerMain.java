package com.github.harissonnascimento;

import com.github.harissonnascimento.classes.Employee;
import com.github.harissonnascimento.classes.Person;
import com.github.harissonnascimento.engine.Instantiator;

public class FakeObjectMakerMain {

  public static void main(String[] args) {

    var employee = Instantiator.generateClass(Employee.class);
    var person2 = Instantiator.generateClass(Person.class);

    var person = Instantiator.generateClass(Person.class);
    person.setStringTest("Modified value");

    System.out.println(employee);
    System.out.println(person);
    System.out.println(person2);

  }

}
