package co.com.crediyarequest.r2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    ApplicationReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    ApplicationReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {


    }

    @Test
    void mustFindAllValues() {

    }

    @Test
    void mustFindByExample() {

    }

    @Test
    void mustSaveValue() {

    }
}
