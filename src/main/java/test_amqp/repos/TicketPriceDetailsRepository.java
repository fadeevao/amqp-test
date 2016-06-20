package test_amqp.repos;


import org.springframework.data.repository.CrudRepository;
import test_amqp.entities.TicketPriceDetails;

@org.springframework.stereotype.Repository
public interface TicketPriceDetailsRepository extends CrudRepository<TicketPriceDetails, Long> {
    TicketPriceDetails findById(Long id);
}
