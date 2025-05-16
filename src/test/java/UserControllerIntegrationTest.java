
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.domain.dto.UserDTO;
import com.project.app.domain.ports.UserUsecase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  // For JSON serialization/deserialization

    @Autowired
    private UserUsecase userUsecase;  // To setup/verify test data if needed

    @Test
    public void testCreateUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstName("Arun");
        user.setLastName("Reign17");
        user.setEmail("arun.doe@gmail.com");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstname", is("Arun")))
            .andExpect(jsonPath("$.lastname", is("Reign17")))
            .andExpect(jsonPath("$.email", is("arun.doe@gmail.com")));
    }

    @Test
    public void testGetUserById() throws Exception {
        // First create a user to ensure there is one to fetch
        UserDTO user = new UserDTO();
        user.setFirstName("Muniyasamy");
        user.setLastName("Baskaran");
        user.setEmail("muniyasamy.baskaran@gmail.com");

        UserDTO created = userUsecase.create(user);

        mockMvc.perform(get("/api/users/{id}", created.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstname", is("Muniyasamy")))
            .andExpect(jsonPath("$.lastname", is("Baskaran")))
            .andExpect(jsonPath("$.email", is("muniyasamy.baskaran@gmail.com")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Create a user first
        UserDTO user = new UserDTO();
        user.setFirstName("Arun");
        user.setLastName("Reign17");
        user.setEmail("arun.doe@gmail.com");

        UserDTO created = userUsecase.create(user);

        mockMvc.perform(delete("/api/users/{id}", created.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("User deleted successfully")));

    }

}
