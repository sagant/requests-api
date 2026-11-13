package uy.requests;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uy.requests.persistence.RequestRepository;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class RequestApiIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired RequestRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void anonymousUserCanCreateAndListRequests() throws Exception {
        mvc.perform(post("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title":"Teclado","description":"Necesito un teclado nuevo","type":"EQUIPMENT"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern("/api/requests/[0-9]+")))
                .andExpect(jsonPath("$.title").value("Teclado"));

        mvc.perform(get("/api/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type").value("EQUIPMENT"));
    }

    @Test
    void summaryIsGlobalAndGroupsRequestsByType() throws Exception {
        mvc.perform(post("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title":"Teclado","description":"Necesito un teclado nuevo","type":"EQUIPMENT"}
                        """))
                .andExpect(status().isCreated());
        mvc.perform(post("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title":"Repositorio","description":"Necesito acceso al repositorio","type":"ACCESS"}
                        """))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/requests/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.byType.EQUIPMENT").value(1))
                .andExpect(jsonPath("$.byType.ACCESS").value(1))
                .andExpect(jsonPath("$.byType.OTHER").value(0));
    }

    @Test
    void anonymousCanReadGlobalSummary() throws Exception {
        mvc.perform(get("/api/requests/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    void detailReturns404WhenRequestDoesNotExist() throws Exception {
        mvc.perform(get("/api/requests/98765"))
                .andExpect(status().isNotFound());
    }

    @Test
    void inputValidationRejectsEmptyTitle() throws Exception {
        mvc.perform(post("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title":"","description":"Necesito un teclado nuevo","type":"EQUIPMENT"}
                        """))
                .andExpect(status().isBadRequest());
    }
}
