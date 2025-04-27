package Java.etc;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPI_practice {
    public static void main(String[] args) {
        // 연습 1
        // list names에서 A로 시작하는 문자열만 뽑아서 대문자로 출력
        List<String> names = List.of("Alice", "Bob", "Andrew", "David", "Angela");

        List<String> result = names.stream()
        .filter(name -> name.startsWith("A"))
        .map(String::toUpperCase)
        .collect(Collectors.toList());
        
        System.out.println("첫 번째 : " +result); // 결과: [ALICE, ANDREW, ANGELA]

        // 연습 2
        int[] numbers = {1, 2, 3, 4, 5, 6};

        OptionalDouble a = Arrays.stream(numbers)
        .filter(nums -> nums%2==0)
        .map(nums -> nums * nums)
        .average();

        System.out.println("두 번쨰 : " + a);

         // 연습 3
        List<String> words = List.of("apple", "banana", "apple", "cherry", "banana", "date");

        List<String> result2 = words.stream()
        .distinct()
        .sorted()
        .collect(Collectors.toList());

        System.out.println("세 번째 : " +result2); // 결과: [apple, banana, cherry, date]
        
        // 연습 4
        List<Person> people = List.of(
            new Person("Alice", 23),
            new Person("Bob", 30),
            new Person("Charlie", 18),
            new Person("David", 35)
        );

        String result3 = people.stream()
            .filter(p -> p.getAge() >= 20)   // 20살 이상만
            .map(Person::getName)            // 이름만 추출
            .collect(Collectors.joining(", ")); // 콤마로 연결

        System.out.println(result3); 
    }


    static class Person {
        private String name;
        private int age;
    
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
    }
    

}