package ee.katrina.videorental.bootstrap;

import ee.katrina.videorental.entity.*;
import ee.katrina.videorental.model.Genre;
import ee.katrina.videorental.model.MovieType;
import ee.katrina.videorental.model.Role;
import ee.katrina.videorental.repository.*;
import ee.katrina.videorental.security.TokenGenerator;
import ee.katrina.videorental.service.CustomerService;
import ee.katrina.videorental.service.EmployeeService;
import ee.katrina.videorental.service.MovieService;
import ee.katrina.videorental.service.RentalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final ActorRepository actorRepository;
    private final ContactDataRepository contactDataRepository;
    private final CustomerRepository customerRepository;
    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;
    private final WriterRepository writerRepository;
    private final RentalRepository rentalRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final RentalService rentalService;

    @Autowired
    TokenGenerator tokenGenerator;

    @Transactional
    @Override
    public void run(String... args) {
        loadActorData();
        loadDirectorData();
        loadWriterData();
        loadMovieData();
        loadCustomerData();
        loadEmployeeData();
        loadRentalTransactionData();
    }

    private void loadActorData() {
        if (actorRepository.count() == 0) {
            Actor actor1 = Actor.builder()
                    .firstName("Keanu")
                    .lastName("Reeves")
                    .birthYear(1964)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Actor actor2 = Actor.builder()
                    .firstName("Laurence")
                    .lastName("Fishburne")
                    .birthYear(1961)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Actor actor3 = Actor.builder()
                    .firstName("Carrie-Anne")
                    .lastName("Moss")
                    .birthYear(1967)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Actor actor4 = Actor.builder()
                    .firstName("Margot")
                    .lastName("Robbie")
                    .birthYear(1990)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Actor actor5 = Actor.builder()
                    .firstName("Ryan")
                    .lastName("Gosling")
                    .birthYear(1980)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            actorRepository.saveAll(Arrays.asList(actor1, actor2, actor3, actor4, actor5));
            System.out.println("Loaded actors data");
        }
    }

    private void loadDirectorData() {
        if (directorRepository.count() == 0) {
            Director director1 = Director.builder()
                    .firstName("Lana")
                    .lastName("Wachowski")
                    .birthYear(1965)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Director director2 = Director.builder()
                    .firstName("Lilly")
                    .lastName("Wachowski")
                    .birthYear(1967)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Director director3 = Director.builder()
                    .firstName("Greta")
                    .lastName("Gerwig")
                    .birthYear(1983)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            directorRepository.saveAll(Arrays.asList(director1, director2, director3));
            System.out.println("Loaded directors data");
        }
    }

    private void loadWriterData() {
        if (writerRepository.count() == 0) {
            Writer writer1 = Writer.builder()
                    .firstName("Lilly")
                    .lastName("Wachowski")
                    .birthYear(1967)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Writer writer2 = Writer.builder()
                    .firstName("Lana")
                    .lastName("Wachowski")
                    .birthYear(1965)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Writer writer3 = Writer.builder()
                    .firstName("Greta")
                    .lastName("Gerwig")
                    .birthYear(1983)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Writer writer4 = Writer.builder()
                    .firstName("Noah")
                    .lastName("Baumbach")
                    .birthYear(1969)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            writerRepository.saveAll(Arrays.asList(writer1, writer2, writer3, writer4));
            System.out.println("Loaded writers data");
        }
    }

    private void loadMovieData() {
        if (movieRepository.count() == 0) {
            Director director1 = directorRepository.findDirectorByFirstNameAndLastNameAndBirthYear(
                    "Lana", "Wachowski", 1965);
            Director director2 = directorRepository.findDirectorByFirstNameAndLastNameAndBirthYear(
                    "Lilly", "Wachowski", 1967);

            Writer writer1 = writerRepository.findWriterByFirstNameAndLastNameAndBirthYear(
                    "Lana", "Wachowski", 1965);
            Writer writer2 = writerRepository.findWriterByFirstNameAndLastNameAndBirthYear(
                    "Lilly", "Wachowski", 1967);

            Actor actor1 = actorRepository.findActorByFirstNameAndLastNameAndBirthYear(
                    "Keanu", "Reeves", 1964);
            Actor actor2 = actorRepository.findActorByFirstNameAndLastNameAndBirthYear(
                    "Laurence", "Fishburne", 1961);
            Actor actor3 = actorRepository.findActorByFirstNameAndLastNameAndBirthYear(
                    "Carrie-Anne", "Moss", 1967);

            Movie movie1 = Movie.builder()
                    .title("The Matrix")
                    .releaseYear(1999)
                    .lengthInMinutes(136)
                    .movieType(MovieType.OLD)
                    .rentedOut(false)
                    .genres(Arrays.asList(Genre.ACTION, Genre.SCIFI))
                    .directors(Arrays.asList(director1, director2))
                    .stars(Arrays.asList(actor1, actor2, actor3))
                    .writers(Arrays.asList(writer1, writer2))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Movie movie2 = Movie.builder()
                    .title("The Matrix Reloaded")
                    .releaseYear(2003)
                    .lengthInMinutes(138)
                    .movieType(MovieType.REGULAR)
                    .rentedOut(false)
                    .genres(Arrays.asList(Genre.ACTION, Genre.SCIFI))
                    .directors(Arrays.asList(director1, director2))
                    .stars(Arrays.asList(actor1, actor2, actor3))
                    .writers(Arrays.asList(writer1, writer2))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Director director3 = directorRepository.findDirectorByFirstNameAndLastNameAndBirthYear(
                    "Greta", "Gerwig", 1983);

            Writer writer3 = writerRepository.findWriterByFirstNameAndLastNameAndBirthYear(
                    "Greta", "Gerwig", 1983);
            Writer writer4 = writerRepository.findWriterByFirstNameAndLastNameAndBirthYear(
                    "Noah", "Baumbach", 1969);

            Actor actor4 = actorRepository.findActorByFirstNameAndLastNameAndBirthYear(
                    "Margot", "Robbie", 1990);
            Actor actor5 = actorRepository.findActorByFirstNameAndLastNameAndBirthYear(
                    "Ryan", "Gosling", 1980);

            Movie movie3 = Movie.builder()
                    .title("Barbie")
                    .releaseYear(2023)
                    .lengthInMinutes(114)
                    .movieType(MovieType.NEW)
                    .rentedOut(false)
                    .genres(Arrays.asList(Genre.ADVENTURE, Genre.COMEDY, Genre.FANTASY))
                    .directors(Arrays.asList(director3))
                    .stars(Arrays.asList(actor4, actor5))
                    .writers(Arrays.asList(writer3, writer4))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3));
            System.out.println("Loaded movies data");
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            ContactData contactData1 = ContactData.builder()
                    .email("one@one.ee")
                    .phoneNumber("952039723")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            ContactData contactData2 = ContactData.builder()
                    .email("two@two.ee")
                    .phoneNumber("249753593")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            ContactData savedContactData1 = contactDataRepository.save(contactData1);
            ContactData savedContactData2 = contactDataRepository.save(contactData2);

            Customer customer1 = Customer.builder()
                    .firstName("Customer")
                    .lastName("One")
                    .username("1")
                    .password("1")
                    .contactData(savedContactData1)
                    .bonusPoints(55L)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .firstName("Customer")
                    .lastName("Two")
                    .username("2")
                    .password("2")
                    .contactData(savedContactData2)
                    .bonusPoints(0L)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerService.addCustomer(customer1);
            customerService.addCustomer(customer2);
            System.out.println("Loaded customers data");
        }
    }


    private void loadEmployeeData() {
        if (employeeRepository.count() == 0) {
            ContactData contactData1 = ContactData.builder()
                    .email("admin@admin.ee")
                    .phoneNumber("63473473")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            ContactData contactData2 = ContactData.builder()
                    .email("employee1@employee1.ee")
                    .phoneNumber("37537357")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            ContactData savedContactData1 = contactDataRepository.save(contactData1);
            ContactData savedContactData2 = contactDataRepository.save(contactData2);

            Employee admin = Employee.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .username("admin")
                    .password("admin")
                    .contactData(savedContactData1)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .role(Role.ADMIN)
                    .build();

            Employee employee1 = Employee.builder()
                    .firstName("Employee")
                    .lastName("One")
                    .username("e1")
                    .password("e1")
                    .contactData(savedContactData2)
                    .role(Role.EMPLOYEE)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            employeeService.addEmployee(admin);
            employeeService.addEmployee(employee1);
            System.out.println("Loaded employee data");
        }
    }

    private void loadRentalTransactionData() {
        if (rentalRepository.count() == 0) {
            Customer customer = customerRepository.findAll().get(0);
            Movie movie1 = movieRepository.findByTitle("The Matrix");
            Movie movie2 = movieRepository.findByTitle("The Matrix Reloaded");
            RentalTransactionLine rentalTransactionLine1 = RentalTransactionLine.builder()
                    .movie(movie1)
                    .daysRented(5)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            RentalTransactionLine rentalTransactionLine2 = RentalTransactionLine.builder()
                    .movie(movie2)
                    .daysRented(1)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            RentalTransaction rentalTransaction = RentalTransaction.builder()
                    .customer(customer)
                    .rentalTransactionLines(Arrays.asList(rentalTransactionLine1, rentalTransactionLine2))
                    .build();
            rentalService.addRental(rentalTransaction);
            System.out.println("Loaded rental transactions data");
        }
    }
}
