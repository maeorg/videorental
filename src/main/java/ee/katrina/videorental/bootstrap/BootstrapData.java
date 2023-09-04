package ee.katrina.videorental.bootstrap;

import ee.katrina.videorental.entity.*;
import ee.katrina.videorental.model.Genre;
import ee.katrina.videorental.model.MovieType;
import ee.katrina.videorental.repository.*;
import ee.katrina.videorental.service.MovieService;
import ee.katrina.videorental.service.RentalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final MovieService movieService;
    private final RentalService rentalService;

    @Transactional
    @Override
    public void run(String... args) {
        loadActorData();
        loadDirectorData();
        loadWriterData();
        loadMovieData();
        loadCustomerData();
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

            actorRepository.saveAll(Arrays.asList(actor1, actor2, actor3));
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

            directorRepository.saveAll(Arrays.asList(director1, director2));
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

            writerRepository.saveAll(Arrays.asList(writer1, writer2));
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
                    .movieType(MovieType.REGULAR)
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
                    .movieType(MovieType.NEW)
                    .rentedOut(false)
                    .genres(Arrays.asList(Genre.ACTION, Genre.SCIFI))
                    .directors(Arrays.asList(director1, director2))
                    .stars(Arrays.asList(actor1, actor2, actor3))
                    .writers(Arrays.asList(writer1, writer2))
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            movieRepository.saveAll(Arrays.asList(movie1, movie2));
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

            customerRepository.saveAll(Arrays.asList(customer1, customer2));
            System.out.println("Loaded customers data");
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
